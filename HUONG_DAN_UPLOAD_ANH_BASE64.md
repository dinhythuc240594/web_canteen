# 📸 HƯỚNG DẪN SỬ DỤNG UPLOAD ẢNH BASE64

## 🎯 Tổng quan

Hệ thống upload ảnh Base64 đã được tích hợp hoàn chỉnh vào dự án `web_canteen`. Ảnh sẽ được:
1. Upload từ form (PNG/JPG/JPEG)
2. Tự động resize về kích thước phù hợp (max 1920x1080)
3. Tạo thumbnail nhỏ (300x300) 
4. Convert sang Base64
5. Lưu vào bảng `images` trong MySQL

## 📝 Các file đã tạo

### 1. SQL Schema
- `sql/images_table.sql` - Script tạo bảng images và cập nhật foods

### 2. Java Backend
- `src/main/java/utils/ImageBase64Util.java` - Utility convert ảnh
- `src/main/java/model/ImageDAO.java` - Model cho bảng images
- `src/main/java/repository/ImageRepository.java` - Interface
- `src/main/java/repositoryimpl/ImageRepositoryImpl.java` - Implementation
- `src/main/java/controller/ImageServlet.java` - Serve ảnh từ DB
- `src/main/java/controller/FoodServerlet.java` - Đã cập nhật với @MultipartConfig

### 3. Frontend
- `src/main/webapp/foodTemplates/food-form-create.jsp` - Form upload đã cập nhật

## 🚀 Cách triển khai

### Bước 1: Chạy SQL Script
```bash
mysql -u root -p canteen_db < sql/images_table.sql
```

Hoặc trong MySQL Workbench:
```sql
-- Copy nội dung file sql/images_table.sql và chạy
```

Script sẽ:
- Tạo bảng `images`
- Thêm cột `image_id` vào bảng `foods`
- Tạo foreign key và index

### Bước 2: Build và Deploy
```bash
# Build project
mvn clean package

# Deploy webcanteen.war lên Tomcat
# Hoặc chạy trong IDE (Eclipse/IntelliJ)
```

### Bước 3: Test Upload

1. **Truy cập form tạo món:**
   ```
   http://localhost:8080/webcanteen/foods?action=create
   ```

2. **Điền thông tin:**
   - Tên món ăn
   - Giá
   - Tồn kho
   - Chọn file ảnh (PNG/JPG, max 5MB)

3. **Submit form** → Ảnh sẽ tự động:
   - Resize nếu quá lớn
   - Tạo thumbnail
   - Lưu Base64 vào DB
   - Trả về image_id

## 📊 Cấu trúc bảng images

```sql
CREATE TABLE images (
    id INT AUTO_INCREMENT PRIMARY KEY,
    filename VARCHAR(255),           -- "pho-bo.jpg"
    mime_type VARCHAR(50),            -- "image/jpeg"
    file_size INT,                    -- 123456 bytes
    image_data LONGTEXT,              -- Base64 full size
    thumbnail_data TEXT,              -- Base64 thumbnail
    width INT,                        -- 1920
    height INT,                       -- 1080
    uploaded_by INT,
    entity_type ENUM('food','user','stall','category'),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
```

## 🖼️ Cách hiển thị ảnh

### Cách 1: Data URL trực tiếp (đơn giản)
```jsp
<%
    ImageDAO image = imageRepo.findById(food.getImageId());
%>
<img src="<%= image.getDataURL() %>" alt="Food image">
```

### Cách 2: Qua ImageServlet (khuyên dùng - có cache)
```jsp
<img src="${pageContext.request.contextPath}/image/<%= food.getImageId() %>" 
     alt="Food image">

<!-- Thumbnail -->
<img src="${pageContext.request.contextPath}/image/<%= food.getImageId() %>?type=thumb" 
     alt="Food thumbnail">
```

## 🔧 Tùy chỉnh

### Thay đổi kích thước ảnh
Trong `ImageBase64Util.java`:
```java
private static final int MAX_WIDTH = 1920;   // Đổi thành 2560 nếu muốn HD hơn
private static final int MAX_HEIGHT = 1080;
private static final int THUMB_SIZE = 300;    // Đổi thành 150 cho thumbnail nhỏ hơn
```

### Thay đổi giới hạn file size
Trong `FoodServerlet.java`:
```java
@MultipartConfig(
    maxFileSize = 10 * 1024 * 1024,  // Tăng lên 10MB
    maxRequestSize = 20 * 1024 * 1024
)
```

## ✅ Ưu điểm

✔️ **Portable**: Backup DB = backup cả ảnh  
✔️ **Không mất file**: Không phụ thuộc filesystem  
✔️ **Dễ scale**: Clone DB sang server khác tự động có ảnh  
✔️ **Thumbnail tự động**: Tiết kiệm băng thông  
✔️ **Metadata đầy đủ**: Lưu kích thước, mime type, uploader  

## ⚠️ Lưu ý

1. **Database size**: Base64 tăng ~33% so với file gốc
   - File 100KB → Base64 ~133KB
   - Cân nhắc dọn dẹp ảnh cũ định kỳ

2. **Performance**: 
   - Dùng thumbnail cho danh sách
   - Full size cho trang chi tiết
   - Cache ở browser (Cache-Control header)

3. **Migration từ hệ thống cũ**:
   - Nếu đã có ảnh ở filesystem
   - Viết script convert sang Base64
   - Import vào bảng images

## 🐛 Troubleshooting

### Lỗi: "File quá lớn"
→ Tăng `maxFileSize` trong `@MultipartConfig`

### Lỗi: "Cannot insert image"
→ Kiểm tra:
- Bảng `images` đã tạo chưa?
- DataSource kết nối đúng chưa?
- Log SQL error: `System.err` sẽ in chi tiết

### Ảnh không hiển thị
→ Kiểm tra:
- ImageServlet đã deploy chưa? `/image/*`
- image_id có đúng không?
- Browser console có lỗi 404/500?

## 📞 Hỗ trợ

Nếu gặp vấn đề, kiểm tra log tại:
- Console servlet container (Tomcat/GlassFish)
- Browser Developer Tools > Console
- MySQL error log

---

**Chúc bạn thành công! 🎉**

