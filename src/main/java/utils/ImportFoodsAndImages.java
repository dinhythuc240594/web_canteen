package utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.*;
import java.text.Normalizer;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Script standalone: import ảnh + tạo dữ liệu foods dựa trên thư mục picture/
 * - com/               → category_id=1 (Cơm),       stall_id=1
 * - pho-bun-mi/        → category_id=2 (Phở/Bún/Mì), stall_id=2
 * - an-vat-trang-mieng/→ category_id=3 (Ăn vặt),     stall_id=1
 * - giai-khat/         → category_id=4 (Giải khát),  stall_id=2
 * - tra-caphe/         → category_id=5 (Cà phê/Trà), stall_id=2
 *
 * Cập nhật lại DB_URL, user, password cho phù hợp trước khi chạy.
 */
public class ImportFoodsAndImages {

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/canteen_db?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456"; // ⚠️ Đổi theo mật khẩu của bạn
    private static final String PICTURE_ROOT = "picture/";

    private static final Map<String, FolderConfig> FOLDER_CONFIG = Map.of(
            "com", new FolderConfig(1, 1, "Cơm", 35000, 55000),
            "pho-bun-mi", new FolderConfig(2, 2, "Phở/Bún/Mì", 40000, 60000),
            "an-vat-trang-mieng", new FolderConfig(3, 1, "Đồ ăn vặt/Tráng miệng", 15000, 35000),
            "giai-khat", new FolderConfig(4, 2, "Đồ uống giải khát", 10000, 25000),
            "tra-caphe", new FolderConfig(5, 2, "Cà phê/Trà", 15000, 35000)
    );

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("🔌 Đã kết nối MySQL thành công.");
            conn.setAutoCommit(false);

            int totalFoods = 0;

            for (Map.Entry<String, FolderConfig> entry : FOLDER_CONFIG.entrySet()) {
                String folderName = entry.getKey();
                FolderConfig config = entry.getValue();

                File dir = new File(PICTURE_ROOT + folderName);
                if (!dir.exists() || !dir.isDirectory()) {
                    System.err.println("⚠️ Bỏ qua thư mục (không tồn tại): " + folderName);
                    continue;
                }

                File[] files = dir.listFiles((d, name) -> {
                    String lower = name.toLowerCase();
                    return lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".png");
                });

                if (files == null || files.length == 0) {
                    System.out.println("⚠️ Thư mục " + folderName + " không có ảnh.");
                    continue;
                }

                System.out.printf("\n📁 %s → Category %d (%s), Stall %d\n",
                        folderName, config.categoryId, config.categoryLabel, config.stallId);

                for (File imgFile : files) {
                    try {
                        String foodName = normalizeFileName(imgFile.getName());
                        int imageId = upsertImage(conn, imgFile); // insert ảnh nếu chưa có
                        upsertFood(conn, foodName, config, imageId);
                        totalFoods++;
                        System.out.printf("   ✓ %s → image_id=%d\n", foodName, imageId);
                    } catch (Exception e) {
                        System.err.printf("   ✗ Lỗi với %s: %s\n", imgFile.getName(), e.getMessage());
                    }
                }
            }

            conn.commit();
            System.out.println("\n🎉 Hoàn tất! Đã tạo " + totalFoods + " món ăn.");
        } catch (Exception e) {
            System.err.println("❌ Lỗi nghiêm trọng: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static int upsertImage(Connection conn, File file) throws Exception {
        String filename = file.getName();

        // 1. Nếu ảnh đã tồn tại, trả về ID
        try (PreparedStatement check = conn.prepareStatement(
                "SELECT id FROM images WHERE filename = ? LIMIT 1")) {
            check.setString(1, filename);
            ResultSet rs = check.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }

        BufferedImage original = ImageIO.read(file);
        if (original == null) {
            throw new IllegalArgumentException("Ảnh không hợp lệ: " + filename);
        }

        BufferedImage resized = resizeKeepRatio(original, 1920, 1080);
        BufferedImage thumb = resizeKeepRatio(original, 300, 300);

        String mimeType = filename.toLowerCase().endsWith(".png") ? "image/png" : "image/jpeg";

        try (PreparedStatement insert = conn.prepareStatement(
                "INSERT INTO images (filename, mime_type, file_size, image_data, thumbnail_data, width, height, entity_type) " +
                        "VALUES (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            insert.setString(1, filename);
            insert.setString(2, mimeType);
            insert.setLong(3, file.length());
            insert.setString(4, toBase64(resized, mimeType));
            insert.setString(5, toBase64(thumb, mimeType));
            insert.setInt(6, resized.getWidth());
            insert.setInt(7, resized.getHeight());
            insert.setString(8, "food");
            insert.executeUpdate();

            ResultSet keys = insert.getGeneratedKeys();
            if (keys.next()) return keys.getInt(1);
            throw new SQLException("Không lấy được ID ảnh mới.");
        }
    }

    private static void upsertFood(Connection conn, String foodName, FolderConfig config, int imageId) throws Exception {
        // Kiểm tra món đã tồn tại chưa
        try (PreparedStatement check = conn.prepareStatement(
                "SELECT id FROM foods WHERE name = ? LIMIT 1")) {
            check.setString(1, foodName);
            ResultSet rs = check.executeQuery();
            if (rs.next()) {
                // Cập nhật image_id nếu trước đó chưa có
                try (PreparedStatement update = conn.prepareStatement(
                        "UPDATE foods SET image_id = ?, category_id = ?, stall_id = ?, is_available = TRUE WHERE id = ?")) {
                    update.setInt(1, imageId);
                    update.setInt(2, config.categoryId);
                    update.setInt(3, config.stallId);
                    update.setInt(4, rs.getInt("id"));
                    update.executeUpdate();
                }
                return;
            }
        }

        int price = randomBetween(config.priceMin, config.priceMax);
        int inventory = randomBetween(50, 200);
        int promotion = (Math.random() < 0.3) ? randomBetween(5, 20) : 0; // 30% có khuyến mãi

        try (PreparedStatement insert = conn.prepareStatement(
                "INSERT INTO foods (name, price, inventory, category_id, stall_id, image_id, description, promotion, is_available) " +
                        "VALUES (?,?,?,?,?,?,?,?,TRUE)")) {
            insert.setString(1, foodName);
            insert.setInt(2, price);
            insert.setInt(3, inventory);
            insert.setInt(4, config.categoryId);
            insert.setInt(5, config.stallId);
            insert.setInt(6, imageId);
            insert.setString(7, "Món ngon từ quầy " + config.categoryLabel.toLowerCase());
            insert.setInt(8, promotion);
            insert.executeUpdate();
        }
    }

    private static BufferedImage resizeKeepRatio(BufferedImage img, int maxW, int maxH) {
        int w = img.getWidth();
        int h = img.getHeight();
        if (w <= maxW && h <= maxH) return img;

        double ratio = (double) w / h;
        int newW = w;
        int newH = h;
        if (newW > maxW) {
            newW = maxW;
            newH = (int) (maxW / ratio);
        }
        if (newH > maxH) {
            newH = maxH;
            newW = (int) (maxH * ratio);
        }

        BufferedImage resized = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resized.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img, 0, 0, newW, newH, null);
        g.dispose();
        return resized;
    }

    private static String toBase64(BufferedImage img, String mimeType) throws Exception {
        String format = mimeType.contains("png") ? "png" : "jpg";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, format, baos);
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    private static String normalizeFileName(String filename) {
        String base = filename.replaceAll("\\.[^.]+$", "");
        base = base.replace('_', ' ').replace('-', ' ');
        base = Normalizer.normalize(base, Normalizer.Form.NFD);
        base = base.replaceAll("\\p{M}", ""); // bỏ dấu
        return capitalizeWords(base.trim());
    }

    private static String capitalizeWords(String input) {
        String[] words = input.split("\\s+");
        List<String> capitalized = new ArrayList<>();
        for (String word : words) {
            if (word.isEmpty()) continue;
            capitalized.add(word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase());
        }
        return String.join(" ", capitalized);
    }

    private static int randomBetween(int min, int max) {
        return ((int) (Math.random() * (max - min + 1)) + min) / 1000 * 1000;
    }

    private static class FolderConfig {
        final int categoryId;
        final int stallId;
        final String categoryLabel;
        final int priceMin;
        final int priceMax;

        FolderConfig(int categoryId, int stallId, String label, int priceMin, int priceMax) {
            this.categoryId = categoryId;
            this.stallId = stallId;
            this.categoryLabel = label;
            this.priceMin = priceMin;
            this.priceMax = priceMax;
        }
    }
}