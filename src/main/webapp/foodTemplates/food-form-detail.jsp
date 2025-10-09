<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Update Food</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Bootstrap JS (optional, cho các component cần JS) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        .grid { display: grid; grid-template-columns: repeat(3, 1fr); gap: 20px; }
        .book { border: 1px solid #ccc; padding: 10px; text-align: center; }
    </style>
</head>
<body>
	<h1>Edit</h1>
  <div id="container">
	<% model.FoodDAO food = (model.FoodDAO) request.getAttribute("food"); %>
	<% if (food != null) { %>
    <div id="" class="form-text">

        <label class="form-label">Tên món ăn</label>
        <span id="nameFood" class="form-control"><%= food.getNameFood() %></span><br />
        <label class="form-label">Giá</label>
        <span id="priceFood" class="form-control"><%= food.getPriceFood() %></span><br />
        <label class="form-label">Tồn kho</label>
        <span id="inventoryFood" class="form-control" ><%= food.getInventoryFood() %></span>
        
    </div>
	<% } %>
	<a href="foods?action=list">Go back to Home</a>
  </div>

</body>
</html>
