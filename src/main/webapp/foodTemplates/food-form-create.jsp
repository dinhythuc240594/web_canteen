<%@page import="model.Food_CategoryDAO"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New Food</title>
    <jsp:include page="/WEB-INF/jsp/common/head.jsp" />
</head>
<body>

  <% 
		java.util.List<Food_CategoryDAO> categories = (java.util.List<Food_CategoryDAO>) request.getAttribute("categories");
  %>

  <div id="container">

	<div class="container mt-5">
    	<h2 class="mb-4">Tạo Món Ăn Mới</h2>
    	
    	<%-- Hiển thị thông báo lỗi nếu có --%>
    	<% if (request.getAttribute("error") != null) { %>
    		<div class="alert alert-danger"><%= request.getAttribute("error") %></div>
    	<% } %>
    	
    	<form action="${pageContext.request.contextPath}/foods?action=create" 
    	      method="post" 
    	      enctype="multipart/form-data">
        	<div class="row">
            	<div class="col-md-6">
                	<div class="mb-3">
                    	<label for="nameFood" class="form-label">Tên Món Ăn <span class="text-danger">*</span></label>
                    	<input type="text" class="form-control" id="nameFood" name="nameFood" required>
                	</div>
                <div class="mb-3">
                    <label for="category_id" class="form-label">Danh Mục</label>
                    <select class="form-select" id="category_id" name="category_id">
                        <% for(Food_CategoryDAO category: categories) { %>
					  		<option value="<%= category.getId() %>"><%= category.getName() %></option>
					  	<% } %>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="priceFood" class="form-label">Giá (VND) <span class="text-danger">*</span></label>
                    <input type="number" class="form-control" id="priceFood" name="priceFood" step="1000" required>
                </div>
                <div class="mb-3">
                    <label for="inventoryFood" class="form-label">Tồn Kho <span class="text-danger">*</span></label>
                    <input type="number" class="form-control" id="inventoryFood" name="inventoryFood" required>
                </div>
                <div class="mb-3">
                    <label for="stall_id" class="form-label">Quầy</label>
                    <select class="form-select" id="stall_id" name="stall_id">
                    	<option value="1">Quầy Cơm & Món Khô</option>
                    	<option value="2">Quầy Nước & Đồ Uống</option>
                    </select>
                </div>
            	</div>

            <div class="col-md-6">
                <div class="mb-3">
                    <label for="imageFile" class="form-label">Hình Ảnh Món Ăn <span class="text-danger">*</span></label>
                    <input type="file" 
                           class="form-control" 
                           id="imageFile" 
                           name="imageFile" 
                           accept="image/png,image/jpeg,image/jpg" 
                           required>
                    <small class="text-muted">Chấp nhận: PNG, JPG, JPEG. Tối đa 5MB.</small>
                	<div id="imagePreview" class="mt-2" style="max-width: 300px; max-height: 300px; overflow: hidden; border: 1px solid #ddd; padding: 5px; display: none;">
                    	<img src="" alt="Xem trước ảnh" class="img-fluid">
                	</div>
                </div>
                <div class="mb-3 form-check">
                    <input type="checkbox" class="form-check-input" id="is_available" name="is_available" value="1" checked>
                    <label class="form-check-label" for="is_available">Còn Hàng</label>
                </div>
                <div class="mb-3">
                    <label for="promotion" class="form-label">Khuyến Mãi (%)</label>
                    <input type="number" class="form-control" id="promotion" name="promotion" min="0" max="100" value="0">
                </div>
                <div class="mb-3">
                    <label for="description" class="form-label">Mô Tả Món Ăn</label>
                    <textarea class="form-control" id="description" name="description" rows="5"></textarea>
                </div>
            </div>
        	</div>

        <button type="submit" class="btn btn-primary">Tạo Món Ăn</button>
        <a href="${pageContext.request.contextPath}/foods?action=list" class="btn btn-secondary">Quay lại danh sách</a>
    </form>
    </div>
  </div>

</body>
<script>

CKEDITOR.replace('description', {
    toolbar: [
      { name: 'document', items: [ 'Source', '-', 'Preview' ] },
      { name: 'clipboard', items: [ 'Cut', 'Copy', 'Paste', '-', 'Undo', 'Redo' ] },
      { name: 'styles', items: [ 'Format', 'Font', 'FontSize' ] },
      { name: 'colors', items: [ 'TextColor', 'BGColor' ] },
      { name: 'basicstyles', items: [ 'Bold', 'Italic', 'Underline', 'Strike', '-', 'RemoveFormat' ] },
      { name: 'paragraph', items: [ 'NumberedList', 'BulletedList', '-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight' ] },
      { name: 'insert', items: [ 'Image', 'Table', 'Link', 'Unlink' ] }
    ],
    contentsCss: [
      'https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css',
      'body { font-family: "Roboto", "Helvetica Neue", Arial, sans-serif; padding: 15px; }'
    ],
    filebrowserUploadUrl: '${pageContext.request.contextPath}/upload-image',
    imageUploadUrl: '${pageContext.request.contextPath}/upload-image',
    
    extraPlugins: 'uploadimage',
    removePlugins: 'imagebase64, elementspath',
    height: 300,
    resize_enabled: false,
    image2_alignClasses: ['image-align-left', 'image-align-center', 'image-align-right'],
});

$(document).ready(function() {
	
	// Preview ảnh khi user chọn file
	$('#imageFile').on('change', function() {
        if (this.files && this.files[0]) {
            var reader = new FileReader();

            reader.onload = function(e) {
                $('#imagePreview').show();
                $('#imagePreview img').attr('src', e.target.result);
            };

            // Đọc file ảnh như một URL dữ liệu (base64 - chỉ để preview)
            reader.readAsDataURL(this.files[0]);
        } else {
            $('#imagePreview').hide();
            $('#imagePreview img').attr('src', '');
        }
    });
});
</script>
</html>
