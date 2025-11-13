package utils;

import jakarta.servlet.http.Part;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class để convert ảnh upload sang Base64
 * Hỗ trợ resize, tạo thumbnail tự động
 */
public class ImageBase64Util {

    private static final int MAX_WIDTH = 1920;
    private static final int MAX_HEIGHT = 1080;
    private static final int THUMB_SIZE = 300;
    private static final long MAX_SIZE = 5 * 1024 * 1024; // 5MB

    /**
     * Convert file upload (Part) sang Base64 và metadata
     * 
     * @param part File part từ multipart form
     * @return Map chứa filename, mimeType, fileSize, imageData, thumbnailData, width, height
     * @throws IOException nếu file không hợp lệ hoặc lỗi xử lý
     */
    public static Map<String, Object> convertToBase64(Part part) throws IOException {
        // Validate
        if (part == null || part.getSize() == 0) {
            throw new IOException("File rỗng");
        }
        if (part.getSize() > MAX_SIZE) {
            throw new IOException("File vượt quá 5MB");
        }

        String contentType = part.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IOException("Không phải file ảnh");
        }

        // Đọc ảnh gốc
        BufferedImage original;
        try (InputStream is = part.getInputStream()) {
            original = ImageIO.read(is);
            if (original == null) {
                throw new IOException("Không đọc được ảnh");
            }
        }

        int width = original.getWidth();
        int height = original.getHeight();

        // Resize nếu quá lớn (giữ tỷ lệ)
        BufferedImage resized = original;
        if (width > MAX_WIDTH || height > MAX_HEIGHT) {
            resized = resizeImage(original, MAX_WIDTH, MAX_HEIGHT);
        }

        // Tạo thumbnail
        BufferedImage thumbnail = resizeImage(original, THUMB_SIZE, THUMB_SIZE);

        // Convert sang Base64
        String format = getFormatFromMime(contentType);
        String base64Image = bufferedImageToBase64(resized, format);
        String base64Thumb = bufferedImageToBase64(thumbnail, format);

        // Lấy filename
        String filename = getFileName(part);

        // Kết quả
        Map<String, Object> result = new HashMap<>();
        result.put("filename", filename);
        result.put("mimeType", contentType);
        result.put("fileSize", (int) part.getSize());
        result.put("imageData", base64Image);
        result.put("thumbnailData", base64Thumb);
        result.put("width", resized.getWidth());
        result.put("height", resized.getHeight());

        return result;
    }

    /**
     * Resize ảnh giữ tỷ lệ khung hình
     */
    private static BufferedImage resizeImage(BufferedImage original, int maxWidth, int maxHeight) {
        int width = original.getWidth();
        int height = original.getHeight();
        double ratio = (double) width / height;

        int newWidth = width;
        int newHeight = height;

        if (width > maxWidth) {
            newWidth = maxWidth;
            newHeight = (int) (maxWidth / ratio);
        }
        if (newHeight > maxHeight) {
            newHeight = maxHeight;
            newWidth = (int) (maxHeight * ratio);
        }

        BufferedImage resized = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resized.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(original, 0, 0, newWidth, newHeight, null);
        g.dispose();
        return resized;
    }

    /**
     * BufferedImage → Base64 string
     */
    private static String bufferedImageToBase64(BufferedImage image, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, format, baos);
        byte[] bytes = baos.toByteArray();
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Base64 string → BufferedImage
     */
    public static BufferedImage base64ToBufferedImage(String base64) throws IOException {
        byte[] bytes = Base64.getDecoder().decode(base64);
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        return ImageIO.read(bais);
    }

    /**
     * Lấy format từ MIME type
     */
    private static String getFormatFromMime(String mimeType) {
        if (mimeType.contains("png")) return "png";
        if (mimeType.contains("webp")) return "webp";
        if (mimeType.contains("gif")) return "gif";
        return "jpg"; // default
    }

    /**
     * Lấy filename từ Part, sanitize
     */
    private static String getFileName(Part part) {
        String name = part.getSubmittedFileName();
        if (name == null || name.isEmpty()) {
            return "image_" + System.currentTimeMillis() + ".jpg";
        }
        // Sanitize: chỉ giữ ký tự an toàn
        name = name.replaceAll("[^a-zA-Z0-9._-]", "_");
        return name;
    }

    /**
     * Tạo Data URL để dùng trực tiếp trong HTML
     * VD: data:image/jpeg;base64,/9j/4AAQ...
     */
    public static String toDataURL(String base64, String mimeType) {
        return "data:" + mimeType + ";base64," + base64;
    }
}

