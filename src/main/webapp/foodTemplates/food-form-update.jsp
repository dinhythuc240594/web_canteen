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
      <form method="POST" action="foods" method="post">
      	<input type="hidden" id="id" class="form-control" name="id" value="<%= food.getId() %>" />
      	<input type="hidden" id="action" class="form-control" name="action" value="update" />
        <label class="form-label">Tên món ăn</label>
        <input type="text" id="nameFood" class="form-control" name="nameFood" value="<%= food.getNameFood() %>" />
        <label class="form-label">Giá</label>
        <input type="text" id="priceFood" class="form-control" name="priceFood" value="<%= food.getPriceFood() %>" /><br />
        <label class="form-label">Tồn kho</label>
        <input type="text" id="inventoryFood" class="form-control" name="inventoryFood" value="<%= food.getInventoryFood() %>" />
        <button type="submit" class="btn btn-primary">Update</button>
      </form>
    </div>
	<% } %>
	<a href="foods?action=list">Go back to Home</a>
  </div>

</body>
</html>
