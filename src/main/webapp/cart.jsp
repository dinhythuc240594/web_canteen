<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.Order_FoodDAO" %>

<%
    // Lấy thông tin người dùng và giỏ hàng từ session
    Integer userId = (Integer) session.getAttribute("userId");
    List<Order_FoodDAO> cart = (List<Order_FoodDAO>) session.getAttribute("cart");
    if (cart == null) {
        cart = new ArrayList<>();
        session.setAttribute("cart", cart);
    }

    String error = request.getParameter("error");
    String success = request.getParameter("success");
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Giỏ hàng của bạn</title>
    <jsp:include page="/WEB-INF/jsp/common/head.jsp" />
    <script>
        // Hàm tính tổng tiền trên client-side
        function updateTotal() {
            let rows = document.querySelectorAll('.cart-row');
            let total = 0;
            rows.forEach(row => {
                let price = parseFloat(row.querySelector('.price').textContent);
                let qty = parseInt(row.querySelector('.quantity').value);
                total += price * qty;
            });
            document.getElementById('totalPrice').textContent = total.toFixed(2) + " VND";
        }

        function checkLoginBeforeCheckout(userId) {
            if (userId === null) {
                alert("Vui lòng đăng nhập để thanh toán.");
                window.location.href = 'login.jsp';
                return false;
            }
            return true;
        }
    </script>
</head>

<body class="bg-gray-50">
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<section class="py-8 bg-gradient-to-b from-gray-50 to-blue-50">
  <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
  
      <h2 class="text-center mb-4">🛒 Giỏ hàng của bạn</h2>

	    <% if (error != null) { %>
	        <div class="alert alert-danger text-center">
	            <%= error.equals("empty_cart") ? "Giỏ hàng đang trống!" :
	                error.equals("not_logged_in") ? "Bạn cần đăng nhập trước khi thanh toán!" :
	                error.equals("empty_address") ? "Vui lòng nhập địa chỉ giao hàng!" :
	                "Có lỗi xảy ra, vui lòng thử lại!" %>
	        </div>
	    <% } else if (success != null) { %>
	        <div class="alert alert-success text-center">
	            Thanh toán thành công! Đơn hàng của bạn đã được ghi nhận.
	        </div>
	    <% } %>
	
	    <table class="table table-bordered table-striped">
	        <thead class="table-dark">
	        <tr>
	            <th>Tên món</th>
	            <th>Giá món</th>
	            <th>Số lượng</th>
	            <th>Thành tiền</th>
	        </tr>
	        </thead>
	        <tbody>
	        <% 
	            double total = 0;
	            for (Order_FoodDAO item : cart) {
	                double itemTotal = item.getPriceAtOrder() * item.getQuantity();
	                total += itemTotal;
	        %>
	        <tr class="cart-row">
	            <td><%= item.getName() %></td>
	            <td class="price"><%= item.getPriceAtOrder() %></td>
	            <td>
	                <input type="number" class="quantity form-control" name="quantity_<%= item.getFoodId() %>" 
	                       value="<%= item.getQuantity() %>" min="1" onchange="updateTotal()">
	            </td>
	            <td><%= itemTotal %></td>
	        </tr>
	        <% } %>
	        </tbody>
	    </table>
	
	    <div class="d-flex justify-content-between align-items-center">
	        <h4>Tổng tiền: <span id="totalPrice"><%= String.format("%.2f VND", total) %></span></h4>
	        <form action="${pageContext.request.contextPath}/cart" method="post" 
	              onsubmit="return checkLoginBeforeCheckout(<%= userId %>)">
	            <input type="hidden" name="action" value="checkout">
	            <input type="hidden" name="stallId" value="1">
	            <input type="text" name="address" placeholder="Nhập địa chỉ giao hàng" class="form-control d-inline w-50" required>
	            <button type="submit" class="btn btn-success ms-2">Thanh toán</button>
	        </form>
	    </div>
	
	    <div class="text-center mt-4">
	        <a href="menu.jsp" class="btn btn-secondary">← Tiếp tục chọn món</a>
	    </div>
  </div>
</section>
<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
<script>
    updateTotal();
</script>
</body>
</html>