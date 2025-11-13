package repository;

import model.ImageDAO;

/**
 * Repository interface cho bảng images
 */
public interface ImageRepository {
    
    /**
     * Lưu ảnh vào database
     * @param image ImageDAO object
     * @return ID của ảnh vừa insert, hoặc -1 nếu lỗi
     */
    int insert(ImageDAO image);
    
    /**
     * Tìm ảnh theo ID
     * @param id ID của ảnh
     * @return ImageDAO object hoặc null nếu không tìm thấy
     */
    ImageDAO findById(int id);
    
    /**
     * Xóa ảnh
     * @param id ID của ảnh
     * @return true nếu xóa thành công
     */
    boolean delete(int id);
    
    /**
     * Cập nhật ảnh
     * @param image ImageDAO object
     * @return true nếu cập nhật thành công
     */
    boolean update(ImageDAO image);
}

