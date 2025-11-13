-- Sử dụng database
USE canteen_db;

-- Chú ý: Trong thực tế, 'password_hash' phải là kết quả băm mật khẩu
INSERT INTO users (username, password, full_name, email, phone_number, role, status) VALUES
-- 1. Tài khoản Admin
('admin', MD5('123456'), 'Admin', 'admin@hcm.edu.vn', '0901112222', 'admin', TRUE),

-- 2. Tài khoản Quầy (Stall Staff) - Quản lý Quầy Cơm
('thietru', MD5('123456'), 'Thiết Trụ', 'thiettru@hcm.edu.vn', '0903334444', 'stall', TRUE),

-- 3. Tài khoản Quầy (Stall Staff) - Quản lý Quầy Phở/Bún/Mì
('lymouyen', MD5('123456'), 'Lý Mộ Uyển', 'lymouyen@hcm.edu.vn', '0905556666', 'stall', TRUE),
-- 3. Tài khoản Quầy (Stall Staff) - Quản lý Quầy Ăn Vặt
('lyhai', MD5('123456'), 'Lý Hải', 'lyhai@canteen.edu.vn', '0903334448', 'stall', TRUE),
-- 3. Tài khoản Quầy (Stall Staff) - Quản lý Quầy Nước
('tranmong', MD5('123456'), 'Trần Mộng', 'tranmong@canteen.edu.vn', '0905556667', 'stall', TRUE);


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

-- Quầy ---
INSERT INTO stalls (id, name, description, manager_user_id, is_open)
VALUES
(1, 'Quầy Cơm', 'Chuyên các món cơm', (SELECT id FROM users WHERE username='thietru'), TRUE),
(2,'Quầy nước ', 'Chuyên nước, trà, cà phê', (SELECT id FROM users WHERE username='tranmong'), TRUE),
(3, 'Quầy Ăn vặt ', 'Chuyên các loại ăn vặt', (SELECT id FROM users WHERE username='lyhai'), TRUE),
(4, 'Quầy Phở/bún/mì ', 'Chuyên phở/bún/mì', (SELECT id FROM users WHERE username='lymouyen'), TRUE)
ON DUPLICATE KEY UPDATE
    name = VALUES(name),
    description = VALUES(description),
    manager_user_id = VALUES(manager_user_id),
    is_open = VALUES(is_open);




-- Đặt mức ưu đãi cho 11 món tiêu biểu
USE canteen_db;

UPDATE foods SET promotion = 20 WHERE id = 106;
UPDATE foods SET promotion = 15 WHERE id = 122;
UPDATE foods SET promotion = 8 WHERE id = 113;
UPDATE foods SET promotion = 10 WHERE id = 123;
UPDATE foods SET promotion = 5 WHERE id = 133;
UPDATE foods SET promotion = 5 WHERE id = 134;
UPDATE foods SET promotion = 5 WHERE id = 125;
UPDATE foods SET promotion = 7 WHERE id = 147;
UPDATE foods SET promotion = 9 WHERE id = 157;
UPDATE foods SET promotion = 11 WHERE id = 149;
UPDATE foods SET promotion = 5 WHERE id = 150;
UPDATE foods SET promotion = 5 WHERE id = 143;
