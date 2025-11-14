//package utils;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.sql.*;
//import java.util.Base64;
//
//public class ImportImagesSimple {
//
//    private static final String DB_URL =
//            "jdbc:mysql://localhost:3306/canteen_db?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC";
//    private static final String DB_USER = "root";
//    private static final String DB_PASSWORD = "123456";
//
//    private static final String PICTURE_FOLDER = "picture/";
//
//    public static void main(String[] args) {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
//                conn.setAutoCommit(false);
//
//                String[][] folders = {
//                        {"com", "Cơm"},
//                        {"pho-bun-mi", "Phở/Bún/Mì"},
//                        {"an-vat-trang-mieng", "Ăn vặt/Tráng miệng"},
//                        {"giai-khat", "Đồ uống giải khát"},
//                        {"tra-caphe", "Cà phê/Trà"}
//                };
//
//                for (String[] folderInfo : folders) {
//                    String folderName = folderInfo[0];
//                    File dir = new File(PICTURE_FOLDER + folderName);
//                    if (!dir.exists() || !dir.isDirectory()) {
//                        System.out.println("Bỏ qua " + folderName + " (không tìm thấy)");
//                        continue;
//                    }
//
//                    File[] images = dir.listFiles((d, name) -> {
//                        String lower = name.toLowerCase();
//                        return lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".png");
//                    });
//
//                    if (images == null) continue;
//
//                    for (File img : images) {
//                        if (imageExists(conn, img.getName())) {
//                            System.out.println("Đã có: " + img.getName());
//                            continue;
//                        }
//
//                        BufferedImage original = ImageIO.read(img);
//                        if (original == null) {
//                            System.out.println("Không đọc được: " + img.getName());
//                            continue;
//                        }
//
//                        BufferedImage resized = resize(original, 1920, 1080);
//                        BufferedImage thumb = resize(original, 300, 300);
//
//                        insertImage(conn, img, resized, thumb);
//                        System.out.println("✓ Import: " + img.getName());
//                    }
//                }
//
//                conn.commit();
//                System.out.println("Hoàn tất import ảnh!");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static boolean imageExists(Connection conn, String filename) throws SQLException {
//        String sql = "SELECT 1 FROM images WHERE filename = ? LIMIT 1";
//        try (PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setString(1, filename);
//            ResultSet rs = ps.executeQuery();
//            return rs.next();
//        }
//    }
//
//    private static BufferedImage resize(BufferedImage img, int maxW, int maxH) {
//        int w = img.getWidth();
//        int h = img.getHeight();
//        if (w <= maxW && h <= maxH) return img;
//
//        double ratio = (double) w / h;
//        int newW = w;
//        int newH = h;
//
//        if (newW > maxW) {
//            newW = maxW;
//            newH = (int) (maxW / ratio);
//        }
//        if (newH > maxH) {
//            newH = maxH;
//            newW = (int) (maxH * ratio);
//        }
//
//        BufferedImage resized = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);
//        Graphics2D g = resized.createGraphics();
//        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//        g.drawImage(img, 0, 0, newW, newH, null);
//        g.dispose();
//        return resized;
//    }
//
//    private static void insertImage(Connection conn, File file,
//                                    BufferedImage image, BufferedImage thumb) throws Exception {
//        String mimeType = file.getName().toLowerCase().endsWith(".png") ? "image/png" : "image/jpeg";
//
//        String sql = "INSERT INTO images (filename, mime_type, file_size, image_data, thumbnail_data, width, height, entity_type) " +
//                "VALUES (?,?,?,?,?,?,?,?)";
//
//        try (PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setString(1, file.getName());
//            ps.setString(2, mimeType);
//            ps.setLong(3, file.length());
//            ps.setString(4, toBase64(image, mimeType));
//            ps.setString(5, toBase64(thumb, mimeType));
//            ps.setInt(6, image.getWidth());
//            ps.setInt(7, image.getHeight());
//            ps.setString(8, "food");
//            ps.executeUpdate();
//        }
//    }
//
//    private static String toBase64(BufferedImage img, String mimeType) throws Exception {
//        String format = mimeType.contains("png") ? "png" : "jpg";
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ImageIO.write(img, format, baos);
//        return Base64.getEncoder().encodeToString(baos.toByteArray());
//    }
//}
