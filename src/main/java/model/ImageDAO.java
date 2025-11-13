package model;

import java.sql.Timestamp;

/**
 * Model cho bảng images
 * Lưu trữ ảnh dạng Base64
 */
public class ImageDAO {
    private int id;
    private String filename;
    private String mimeType;
    private int fileSize;
    private String imageData;       // Base64 ảnh full size
    private String thumbnailData;   // Base64 thumbnail
    private int width;
    private int height;
    private Integer uploadedBy;
    private String entityType;      // 'food', 'user', 'stall', 'category'
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Constructors
    public ImageDAO() {}

    public ImageDAO(String filename, String mimeType, int fileSize, 
                    String imageData, String thumbnailData, 
                    int width, int height, String entityType) {
        this.filename = filename;
        this.mimeType = mimeType;
        this.fileSize = fileSize;
        this.imageData = imageData;
        this.thumbnailData = thumbnailData;
        this.width = width;
        this.height = height;
        this.entityType = entityType;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public String getThumbnailData() {
        return thumbnailData;
    }

    public void setThumbnailData(String thumbnailData) {
        this.thumbnailData = thumbnailData;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Integer getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(Integer uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Tạo Data URL để dùng trực tiếp trong HTML <img src="">
     * @return data:image/jpeg;base64,/9j/4AAQ...
     */
    public String getDataURL() {
        if (imageData == null || imageData.isEmpty()) {
            return "";
        }
        return "data:" + mimeType + ";base64," + imageData;
    }

    /**
     * Lấy Data URL của thumbnail
     * Nếu không có thumbnail thì trả về ảnh full
     */
    public String getThumbnailDataURL() {
        if (thumbnailData != null && !thumbnailData.isEmpty()) {
            return "data:" + mimeType + ";base64," + thumbnailData;
        }
        return getDataURL();
    }

    /**
     * Lấy kích thước file dạng human-readable
     */
    public String getReadableFileSize() {
        if (fileSize < 1024) {
            return fileSize + " B";
        } else if (fileSize < 1024 * 1024) {
            return String.format("%.2f KB", fileSize / 1024.0);
        } else {
            return String.format("%.2f MB", fileSize / (1024.0 * 1024.0));
        }
    }

    @Override
    public String toString() {
        return "ImageDAO{" +
                "id=" + id +
                ", filename='" + filename + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", fileSize=" + getReadableFileSize() +
                ", width=" + width +
                ", height=" + height +
                ", entityType='" + entityType + '\'' +
                '}';
    }
}

