<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.OrderDAO" %>
<%@ page import="java.util.Map" %>
<html>
<head>
    <title>Lịch sử đặt hàng</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f5f6f7; margin: 20px; }
        h1 { color: #333; }
        table { border-collapse: collapse; width: 100%; margin-bottom: 30px; background: #fff; border-radius: 6px; }
        th, td { padding: 12px; border-bottom: 1px solid #ddd; }
        th { background-color: #007bff; color: white; text-align: left; }
        .food-table { margin-left: 30px; width: 90%; background: #fafafa; }
        .order-id { color: #007bff; font-weight: bold; }
    </style>
</head>
<body>
    <h1>Lịch sử đặt hàng của bạn</h1>

	<%
		model.Page<OrderDAO> pageOrder = (model.Page<OrderDAO>) request.getAttribute("orders");
		List<OrderDAO> orders = (java.util.List<OrderDAO>) pageOrder.getData();
		
		model.PageRequest pageReq = (model.PageRequest) request.getAttribute("pageReq");
		String keyword = pageReq.getKeyword();
		String orderField = pageReq.getOrderField();
		String sortField = pageReq.getSortField();
		int totalPage = pageOrder.getTotalPage();
		
		
	%>

    <%if (orders == null) { %>
        <p>Bạn chưa có đơn hàng nào.</p>
     <% } %>

        <table>
            <tr>
                <th>Mã đơn hàng</th>
                <th>Ngày đặt</th>
                <th>Tổng tiền</th>
                <th>Trạng thái</th>
            </tr>
            <%
		    	for (OrderDAO order: orders) {
		    %>
            <tr>
                <td class="order-id"><%= order.getId() %></td>
                <td><%= order.getStallId() %>₫</td>
                <td><%= order.getUserId() %></td>
                <td><%= order.getPaymentMethod() %></td>
                <td><%= order.getTotalPrice() %></td>
                <td><%= order.getStatus() %></td>
                <td><%= order.getCreatedAt() %></td>
                <td><a href="order?id=<%= order.getId() %>">Chi Tiết</a></td>
            </tr>           	
		    <%
            	}
            %>
        </table>
</body>
</html>
