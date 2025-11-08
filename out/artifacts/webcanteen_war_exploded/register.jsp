<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Đăng ký - FoodApp</title>
    <jsp:include page="/WEB-INF/jsp/common/head.jsp" />
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />

<div class="container">
    <div class="form-container">
        <h2 class="form-title">Đăng ký</h2>

        <% String error = (String) request.getAttribute("error"); %>
        <% if (error != null) { %>
        <div class="alert alert-danger" role="alert">
            <%= error %>
        </div>
        <% } %>

        <form action="register" method="post">
            <div class="form-group mb-3">
                <label for="register-username">Tên đăng nhập</label>
                <input type="text" class="form-control" id="register-username"
                       name="username" placeholder="Nhập tên đăng nhập" required>
            </div>
            <div class="form-group mb-3">
                <label for="register-password">Mật khẩu</label>
                <input type="password" class="form-control" id="register-password"
                       name="password" placeholder="Nhập mật khẩu" required>
            </div>
            <div class="form-group mb-3">
                <label for="register-email">Email</label>
                <input type="email" class="form-control" id="register-email"
                       name="email" placeholder="Nhập email" required>
            </div>
            <div class="form-group mb-3">
                <label for="register-phone">Số điện thoại</label>
                <input type="text" class="form-control" id="register-phone"
                       name="phone" placeholder="Nhập số điện thoại" required>
            </div>
            <button type="submit" class="btn btn-primary w-100">Đăng ký</button>
            <div class="form-link">
                <a href="login">Đã có tài khoản? Đăng nhập ngay</a>
            </div>
        </form>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
</body>
</html>