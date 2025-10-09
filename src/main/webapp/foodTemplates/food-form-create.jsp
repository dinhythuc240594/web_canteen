<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New Food</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Bootstrap JS (optional, cho các component cần JS) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        .grid { display: grid; grid-template-columns: repeat(3, 1fr); gap: 20px; }
        .book { border: 1px solid #ccc; padding: 10px; text-align: center; }
    </style>
</head>
<body>
	<h1>Create Food</h1>
  <div id="container">

    <div id="" class="form-text">
      <form method="POST" action="foods" method="post">
      	<input type="hidden" id="action" class="form-control" name="action" value="create" />
        <label class="form-label">Nhập tên món</label>
        <input type="text" id="nameFood" class="form-control" name="nameFood">
        <label class="form-label">Nhập giá</label>
        <input type="text" id="priceFood" class="form-control" name="priceFood">
        <label class="form-label">Nhập hàng tồn</label>
        <input type="text" id="inventoryFood" class="form-control" name="inventoryFood"><br />
        <button type="submit" class="btn btn-primary">Create</button>
      </form>
	<a href="foods?action=list">Go back to Home</a>
    </div>
  </div>

</body>
</html>
