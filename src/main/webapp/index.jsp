<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>
</head>
<body>
<% String type_user = (String) request.getAttribute("type_user"); %>

<% if(type_user == "guest"){ %>
	<a href="login">Login</a><br />	
<% } %>

<% if(type_user != "guest"){ %>
	<a href="logout">Logout</a><br />	
<% } %>

<a href="foods?action=list">List</a>
</body>
</html>