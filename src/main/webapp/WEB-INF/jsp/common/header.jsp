<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<%
		String username = (String) request.getAttribute("username");
		String type_user = (String) request.getAttribute("type_user");
		boolean is_login = (boolean) request.getAttribute("is_login");
	%>
<header>
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
		  <div class="container-fluid">
		    <div class="collapse navbar-collapse" id="navbarSupportedContent">
		      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
		        <li class="nav-item">
		          <a class="nav-link active" aria-current="page" href="home">Home</a>
		        </li>
		        <li class="nav-item">
		          <a class="nav-link" href="category">Category</a>
		        </li>
		        
		        <li class="nav-item">
		          	<% if(is_login == false){ %>
		      			<a class="nav-link" href="login">Login</a>
					<% } else { %>
						<p>User: <%= username %></p>
						<p>Role: <%= type_user %></p>
						<a class="nav-link" href="logout">Logout</a>
					<% } %>
		        </li>
		      </ul>
		    </div>
		  </div>
		</nav>
</header>