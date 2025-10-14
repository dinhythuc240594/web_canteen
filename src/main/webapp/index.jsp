<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

	<%-- Start the scriptlet and define the content area --%>
	<%
		String type_user = (String) request.getAttribute("type_user");
	%>

    <div id="header">
        <jsp:include page="/WEB-INF/jsp/common/header.jsp" />
    </div>

	<div id="content">
	    <% if(type_user == "guest"){ %>
			<a href="login">Login</a><br />	
		<% } else { %>
			<a href="logout">Logout</a><br />	
		<% } %>
	</div>

    <div id="footer">
        <jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
    </div>
</body>

</html>
