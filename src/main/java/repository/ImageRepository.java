package repository;

import model.ImageDAO;
import java.io.File;

/**
 * Repository interface cho bảng images
 */
public interface ImageRepository {

    /**
    Lưu ảnh vào database
     @param image ImageDAO object
     @return ID của ảnh vào insert, hoặc -1 nếu lối
    */
    int insert(ImageDAO image);

    /**
     Tìm ảnh theo ID
     @param id ID của ảnh
     @return ImageDAO object hoặc null nếu không tìm thấy
     */
    ImageDAO findById(int id);

    /**
     * Tìm ảnh theo filename
     * @param filename tên file
     * @return ImageDAO object hoặc null nếu không tìm thấy
     */
    ImageDAO findByFilename(String filename);

    /**
     * Kiểm tra ảnh đã tồn tại chưa
     * @param filename tên file
     * @return true nếu đã tồn tại
     */
    boolean existsByFilename(String filename);

    /**
     * Xóa ảnh
     * @param id ID của ảnh
     * @return true nếu xóa thành công
     */
    boolean delete(int id);


    /**
     Cập nhật ảnh
     @param image ImageDAO object
     @return true nếu cập nhật thành công
     */
    boolean update(ImageDAO image);


    /**
     * Insert hoặc update nếu đã tồn tại (upsert)
     * @param image mageDAO object
     * @return ID của ảnh
     */
    int upsert(ImageDAO image);
}

