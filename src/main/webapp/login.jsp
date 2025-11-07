<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <jsp:include page="/WEB-INF/jsp/common/head.jsp" />
</head>
<body>

    <div id="header">
        <jsp:include page="/WEB-INF/jsp/common/header.jsp" />
    </div>

	<div class="tab-content">
		<% String error = (String) request.getAttribute("error"); %>
		<% if (error != null) { %>
		<div class="alert alert-danger" role="alert">
		  <%= error %>
		</div>
		<% } %>
		<div class="container">
		    <div class="row justify-content-center mt-5">
		        <div class="col-md-6">
		            <div class="card">
		                <div class="card-header bg-primary text-white">
		                    <h4 class="mb-0">Login</h4>
		                </div>
		                <div class="card-body">
		                    <form action="login" method="post">
		                        <div class="mb-3">
		                            <label for="email" class="form-label">User name</label>
		                            <input type="text" class="form-control" id="username" name="username" placeholder="Enter username" required>
		                        </div>
		                        <div class="mb-3">
		                            <label for="password" class="form-label">Password</label>
		                            <input type="password" class="form-control" id="password" name="password" placeholder="Enter your password" required>
		                        </div>
		                        <div class="mb-3 form-check">
		                            <input type="checkbox" class="form-check-input" id="remember" name="remember">
		                            <label class="form-check-label" for="rememberMe">Remember me</label>
		                        </div>
		                        <button type="submit" class="btn btn-primary">Login</button>
		                    </form>
		                </div>
		            </div>
		        </div>
		    </div>
		</div>
	</div>

    <div id="footer">
        <jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
    </div>

</body>

</html>

