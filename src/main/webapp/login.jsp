<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
	<title>Đăng nhập - Canteen ĐH</title>
	<jsp:include page="/WEB-INF/jsp/common/head.jsp" />
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />

<div class="min-h-screen bg-gradient-to-br from-gray-50 to-blue-50 flex items-center justify-center py-8 px-4 sm:px-6 lg:px-8">
	<div class="max-w-md w-full bg-white p-6 rounded-lg shadow-sm border border-gray-200">
		<div class="text-center mb-6">
			<h2 class="text-2xl font-bold text-gray-800">Đăng nhập</h2>
			<p class="mt-1 text-gray-600 text-sm">Vui lòng nhập thông tin tài khoản của bạn</p>
		</div>

		<form action="login" method="post" class="space-y-4" onsubmit="handleLogin(event)">
			<div>
				<label for="username" class="block text-sm font-medium text-gray-700">Tên đăng nhập</label>
				<input
						type="text"
						id="username"
						name="username"
						required
						class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 text-sm"
				/>
			</div>

			<div>
				<label for="password" class="block text-sm font-medium text-gray-700">Mật khẩu</label>
				<input
						type="password"
						id="password"
						name="password"
						required
						class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 text-sm"
				/>
			</div>

			<div>
				<button
						type="submit"
						class="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
				>
					Đăng nhập
				</button>
			</div>
		</form>

		<div class="mt-4 text-center">
			<p class="text-xs text-gray-600">
				Chưa có tài khoản?
				<a href="register" class="font-medium text-blue-600 hover:text-blue-500">
					Đăng ký ngay
				</a>
			</p>
		</div>
	</div>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
<script>
	document.addEventListener('DOMContentLoaded', function() {
		lucide.createIcons();
	});

	function handleLogin(event) {
		// For UI only, prevent form submission
		event.preventDefault();
		alert('Tính năng đăng nhập sẽ được triển khai khi kết nối backend!');
	}
</script>
</body>
</html>