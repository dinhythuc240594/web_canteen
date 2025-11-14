package dto;

/**
 * Kết quả của hàm getImageByID
 */
public class ImageDTO {
    private String mimeType;
    private byte[] imageBytes;

    public ImageDTO() {
        this.imageBytes = new byte[0];
    }

    public ImageDTO(String mimeType, byte[] imageBytes) {
        this.mimeType = mimeType;
        this.imageBytes = imageBytes;
    }

    //Getter và setter
    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }
}
