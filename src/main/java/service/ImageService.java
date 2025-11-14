package service;

import dto.ImageDTO;

public interface ImageService {

    /**
     * Lấy hình ảnh theo id
     * @param imageID
     * @param type
     * @return byte[]
     */
    ImageDTO GetImageByID (int imageID, String type);


}
