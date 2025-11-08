<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Đăng nhập - FoodApp</title>
	<jsp:include page="/WEB-INF/jsp/common/head.jsp" />
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />

<div class="container">
	<div class="form-container">
		<h2 class="form-title">Đăng nhập</h2>

		<% String error = (String) request.getAttribute("error"); %>
		<% if (error != null) { %>
		<div class="alert alert-danger" role="alert">
			<%= error %>
		</div>
		<% } %>

		<form action="login" method="post">
			<div class="form-group mb-3">
				<label for="login-username">Tên đăng nhập</label>
				<input type="text" class="form-control" id="login-username"
					   name="username" placeholder="Nhập tên đăng nhập" required>
			</div>
			<div class="form-group mb-3">
				<label for="login-password">Mật khẩu</label>
				<input type="password" class="form-control" id="login-password"
					   name="password" placeholder="Nhập mật khẩu" required>
			</div>
			<div class="mb-3 form-check">
				<input type="checkbox" class="form-check-input" id="rememberMe" name="rememberMe">
				<label class="form-check-label" for="rememberMe">Ghi nhớ đăng nhập</label>
			</div>
			<button type="submit" class="btn btn-primary w-100">Đăng nhập</button>
			<div class="form-link">
				<a href="register">Chưa có tài khoản? Đăng ký ngay</a>
			</div>
		</form>
	</div>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
</body>
</html>