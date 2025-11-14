package serviceimpl;

import dto.ImageDTO;
import model.ImageDAO;
import repository.ImageRepository;
import repositoryimpl.ImageRepositoryImpl;
import service.ImageService;

import javax.sql.DataSource;
import java.util.Base64;

public class ImageServiceIplm implements ImageService {
    private ImageRepository imageRepository;

    public ImageServiceIplm (DataSource ds) {
        this.imageRepository = new ImageRepositoryImpl(ds);
    }


    @Override
    public ImageDTO GetImageByID(int imageID, String type) {
        // Lấy ảnh từ database
        ImageDAO image = imageRepository.findById(imageID);
        if (image == null) {
            System.out.println("Image not found");
            return new ImageDTO();
        }

        // Chọn Base64 data (thumbnail hoặc full)
        String base64Data;
        if ("thumbnail".equals(type) && image.getThumbnailData() != null) {
            base64Data = image.getThumbnailData();
        } else {
            base64Data = image.getImageData();
        }

        if (base64Data == null || base64Data.isEmpty()) {
            System.out.println("Image data is empty");
            return new ImageDTO();
        }
        // Decode Base64 → byte[] (phải trả về mảng byte để trình duyệt hiểu được)
        byte[] imageBytes = Base64.getDecoder().decode(base64Data);
        String mimeType = image.getMimeType();

        return new ImageDTO(mimeType, imageBytes);
    }
}
