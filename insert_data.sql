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
