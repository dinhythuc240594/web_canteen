package repositoryimpl;

import model.ImageDAO;
import repository.ImageRepository;

import javax.sql.DataSource;
import java.sql.*;

/**
 * Implementation của ImageRepository
 */
public class ImageRepositoryImpl implements ImageRepository {
    
    private final DataSource ds;
    
    public ImageRepositoryImpl(DataSource ds) {
        this.ds = ds;
    }
    
    @Override
    public int insert(ImageDAO image) {
        String sql = "INSERT INTO images " +
                     "(filename, mime_type, file_size, image_data, thumbnail_data, " +
                     "width, height, uploaded_by, entity_type) " +
                     "VALUES (?,?,?,?,?,?,?,?,?)";
        
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, image.getFilename());
            ps.setString(2, image.getMimeType());
            ps.setInt(3, image.getFileSize());
            ps.setString(4, image.getImageData());
            ps.setString(5, image.getThumbnailData());
            ps.setInt(6, image.getWidth());
            ps.setInt(7, image.getHeight());
            
            if (image.getUploadedBy() != null) {
                ps.setInt(8, image.getUploadedBy());
            } else {
                ps.setNull(8, Types.INTEGER);
            }
            
            ps.setString(9, image.getEntityType());
            
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows > 0) {
                // Lấy ID vừa insert
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);
                        System.out.println("✓ Image inserted with ID: " + generatedId);
                        return generatedId;
                    }
                }
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Lỗi insert image: " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }
    
    @Override
    public ImageDAO findById(int id) {
        String sql = "SELECT * FROM images WHERE id = ?";
        
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ImageDAO img = new ImageDAO();
                    img.setId(rs.getInt("id"));
                    img.setFilename(rs.getString("filename"));
                    img.setMimeType(rs.getString("mime_type"));
                    img.setFileSize(rs.getInt("file_size"));
                    img.setImageData(rs.getString("image_data"));
                    img.setThumbnailData(rs.getString("thumbnail_data"));
                    img.setWidth(rs.getInt("width"));
                    img.setHeight(rs.getInt("height"));
                    
                    int uploadedBy = rs.getInt("uploaded_by");
                    if (!rs.wasNull()) {
                        img.setUploadedBy(uploadedBy);
                    }
                    
                    img.setEntityType(rs.getString("entity_type"));
                    img.setCreatedAt(rs.getTimestamp("created_at"));
                    img.setUpdatedAt(rs.getTimestamp("updated_at"));
                    
                    return img;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Lỗi findById image: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM images WHERE id = ?";
        
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows > 0) {
                System.out.println("✓ Image deleted: ID " + id);
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Lỗi delete image: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public boolean update(ImageDAO image) {
        String sql = "UPDATE images SET " +
                     "filename = ?, mime_type = ?, file_size = ?, " +
                     "image_data = ?, thumbnail_data = ?, " +
                     "width = ?, height = ?, uploaded_by = ?, entity_type = ? " +
                     "WHERE id = ?";
        
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, image.getFilename());
            ps.setString(2, image.getMimeType());
            ps.setInt(3, image.getFileSize());
            ps.setString(4, image.getImageData());
            ps.setString(5, image.getThumbnailData());
            ps.setInt(6, image.getWidth());
            ps.setInt(7, image.getHeight());
            
            if (image.getUploadedBy() != null) {
                ps.setInt(8, image.getUploadedBy());
            } else {
                ps.setNull(8, Types.INTEGER);
            }
            
            ps.setString(9, image.getEntityType());
            ps.setInt(10, image.getId());
            
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows > 0) {
                System.out.println("✓ Image updated: ID " + image.getId());
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Lỗi update image: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}

