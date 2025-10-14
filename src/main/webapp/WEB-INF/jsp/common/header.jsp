<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<%
		String type_user = (String) request.getAttribute("type_user");
	%>
<header>
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
		  <div class="container-fluid">
		    <div class="collapse navbar-collapse" id="navbarSupportedContent">
		      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
		        <li class="nav-item">
		          <a class="nav-link active" aria-current="page" href="#">Home</a>
		        </li>
		        <li class="nav-item">
		          <a class="nav-link" href="#">Category</a>
		        </li>
		        
		        <li class="nav-item">
		          	<% if(type_user == "guest"){ %>
		      			<a class="nav-link" href="login">Login</a>
					<% } else { %>
						<a class="nav-link" href="logout">Logout</a>
					<% } %>
		        </li>
		      </ul>
		    </div>
		  </div>
		</nav>
</header>