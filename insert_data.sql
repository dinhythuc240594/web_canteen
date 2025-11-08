-- Sử dụng database
USE canteen_db;

-- Chú ý: Trong thực tế, 'password_hash' phải là kết quả băm mật khẩu
INSERT INTO users (username, password, full_name, email, phone_number, role, status) VALUES
-- 1. Tài khoản Admin
('admin', MD5('123456'), 'Admin', 'admin@hcm.edu.vn', '0901112222', 'admin', TRUE),

-- 2. Tài khoản Quầy (Stall Staff) - Quản lý Quầy Cơm
('thietru', MD5('123456'), 'Thiết Trụ', 'thiettru@hcm.edu.vn', '0903334444', 'stall', TRUE),

-- 3. Tài khoản Quầy (Stall Staff) - Quản lý Quầy Phở/Bún
('lymouyen', MD5('123456'), 'Lý Mộ Uyển', 'lymouyen@hcm.edu.vn', '0905556666', 'stall', TRUE),

-- 4. Tài khoản Khách hàng (SV/CBNV) - Người dùng 1
('vuonglam', MD5('123456'), 'Vương Lâm', 'vuonglam@hcm.edu.vn', '0907778888', 'customer', TRUE),

-- 5. Tài khoản Khách hàng (SV/CBNV) - Người dùng 2
('hoangthilang', MD5('123456'), 'Hoàng Thị Loan', 'hoangthiloan@hcm.edu.vn', '0909990000', 'customer', TRUE),

-- 6. Tài khoản Khách hàng bị vô hiệu hóa
('nguyenvannam', MD5('123456'), 'Nguyễn Văn Nam', 'nguyenvannam@hcm.edu.vn', '0911112222', 'customer', FALSE);

-- categories --
INSERT INTO food_categories (name, description) VALUES
-- 1. Các món ăn chính (Cơm, Bún, Phở)
('Cơm', 'Các món cơm phục vụ bữa trưa và bữa tối.'),
('Phở/Bún/Mì', 'Các món nước truyền thống của Việt Nam.'),
('Đồ Ăn Vặt/Tráng Miệng', 'Các món ăn nhẹ, kem, chè.'),

-- 2. Đồ uống
('Đồ Uống Giải Khát', 'Nước ngọt có gas, nước đóng chai.'),
('Cà Phê/Trà', 'Các loại cà phê, trà nóng và lạnh.');

INSERT INTO canteen_db.foods (category_id, image, name, price, inventory, stall_id, description, is_available, updated_at, promotion) VALUES
-- Món category_id 1: Cơm (Phân bổ cho stall_id 1)
(1, NULL, 'Cơm Gà Xối Mỡ Đặc Biệt', 45000.00, 50, 6, 'Gà xối mỡ giòn rụm, ăn kèm cơm trắng và dưa góp.', TRUE, NOW(), 10),
(1, NULL, 'Cơm Sườn Bì Chả', 40000.00, 60, 6, 'Sườn nướng mật ong, bì, chả trứng và nước sốt đặc trưng.', TRUE, NOW(), 10),
(1, NULL, 'Cơm Thịt Kho Trứng', 35000.00, 45, 6, 'Thịt ba chỉ kho tàu mềm rục với trứng cút.', TRUE, NOW(), 15),
(1, NULL, 'Cơm Canh Chua Cá Lóc', 55000.00, 30, 6, 'Canh chua cá lóc miền Tây, vị chua ngọt đậm đà.', TRUE, NOW(), 15),
(1, NULL, 'Cơm Tấm Bì Chả', 38000.00, 55, 6, 'Cơm tấm Sài Gòn truyền thống, không có sườn.', TRUE, NOW(), 20),
(1, NULL, 'Cơm Trắng Đơn Giản', 10000.00, 999, 6, 'Chỉ có cơm trắng.', TRUE, NOW(), 20),
(1, NULL, 'Cơm Xào Bò Lúc Lắc', 60000.00, 25, 6, 'Thịt bò xào với khoai tây và hành tây, sốt tiêu đen.', TRUE, NOW(), 25),
(1, NULL, 'Cơm Kho Quẹt Tôm Thịt', 42000.00, 40, 6, 'Thịt kho quẹt ăn kèm rau luộc theo ngày.', TRUE, NOW(), 25),
(1, NULL, 'Cơm Chiên Hải Sản', 48000.00, 35, 6, 'Cơm chiên tôm, mực, rau củ.', TRUE, NOW(), 30),
(1, NULL, 'Cơm Thập Cẩm', 45000.00, 50, 6, 'Phần cơm tự chọn 3 món mặn/xào.', TRUE, NOW(), 30),

-- Món category_id 2: Phở/Bún/Mì (Phân bổ cho stall_id 2)
(2, NULL, 'Phở Bò Tái Nạm', 50000.00, 70, 5, 'Phở bò Hà Nội truyền thống, nước dùng thanh ngọt.', TRUE, NOW(), 5),
(2, NULL, 'Bún Chả Hà Nội', 45000.00, 65, 5, 'Thịt nướng than hoa, chả viên, bún rối và rau thơm.', TRUE, NOW(), 5),
(2, NULL, 'Mì Quảng Gà', 55000.00, 40, 5, 'Mì Quảng với thịt gà, trứng cút và đậu phộng.', TRUE, NOW(), 10),
(2, NULL, 'Bún Bò Huế Đặc Biệt', 50000.00, 50, 5, 'Bún bò cay, chả cua, móng giò và tiết.', TRUE, NOW(), 10),
(2, NULL, 'Phở Gà Lá Chanh', 45000.00, 60, 5, 'Phở với thịt gà xé và lá chanh thơm lừng.', TRUE, NOW(), 15),
(2, NULL, 'Hủ Tiếu Nam Vang Khô', 52000.00, 35, 5, 'Hủ tiếu khô trộn với tôm, thịt xá xíu, gan.', TRUE, NOW(), 15),
(2, NULL, 'Bún Riêu Cua', 40000.00, 55, 5, 'Bún riêu cua đồng, có thêm đậu hũ và huyết.', TRUE, NOW(), 12),
(2, NULL, 'Mì Trứng Xào Giòn', 45000.00, 30, 5, 'Mì được chiên giòn, ăn kèm rau và thịt xào.', TRUE, NOW(), 12),
(2, NULL, 'Bánh Canh Cua', 58000.00, 25, 5, 'Bánh canh bột lọc/bột gạo với thịt cua.', TRUE, NOW(), 10),
(2, NULL, 'Phở Chay Rau Củ', 35000.00, 20, 5, 'Phở chay thanh đạm, nước dùng từ rau củ.', TRUE, NOW(), 10)
