-- ===================================================
-- BẢNG IMAGES - LƯU ẢNH DẠNG BASE64
-- ===================================================
USE canteen_db;

-- Tạo bảng images
CREATE TABLE IF NOT EXISTS images (
    id INT AUTO_INCREMENT PRIMARY KEY,
    
    -- Thông tin file gốc
    filename VARCHAR(255) NOT NULL,
    mime_type VARCHAR(50) NOT NULL,
    file_size INT NOT NULL,
    
    -- Dữ liệu ảnh Base64
    image_data LONGTEXT NOT NULL,
    thumbnail_data TEXT,
    
    -- Kích thước
    width INT,
    height INT,
    
    -- Metadata
    uploaded_by INT,
    entity_type ENUM('food', 'user', 'stall', 'category') DEFAULT 'food',
    
    -- Timestamp
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_entity_type (entity_type),
    INDEX idx_uploaded_by (uploaded_by)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Cập nhật bảng foods để sử dụng FK image_id
ALTER TABLE foods ADD COLUMN image_id INT AFTER category_id;
ALTER TABLE foods ADD FOREIGN KEY (image_id) REFERENCES images(id) ON DELETE SET NULL;

-- Nếu cột image cũ tồn tại, có thể xóa sau khi migrate
-- ALTER TABLE foods DROP COLUMN image;

-- Index cho hiệu suất
CREATE INDEX idx_foods_image ON foods (image_id);

