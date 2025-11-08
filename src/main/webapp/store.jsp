<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.StoreDAO" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quầy - FoodApp</title>
    <jsp:include page="/WEB-INF/jsp/common/head.jsp" />
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />

<div class="container mt-4">
    <h2 class="slider-title">Danh sách quầy (A -> Z)</h2>
    <div class="counter-list">
        <%
            List<StoreDAO> stores = (List<StoreDAO>) request.getAttribute("stores");
            if (stores != null) {
                for (StoreDAO store : stores) {
        %>
        <div class="counter-item" onclick="location.href='store?action=detail&id=<%= store.getStore_id() %>'">
            <div class="counter-header">
                <div class="counter-avatar">
                    <%= store.getStore_name().substring(0, Math.min(2, store.getStore_name().length())).toUpperCase() %>
                </div>
                <div class="counter-name"><%= store.getStore_name() %></div>
            </div>
            <p><%= store.getDescription() != null ? store.getDescription() : "Chuyên các món ăn ngon" %></p>
        </div>
        <%
                }
            }
        %>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
</body>
</html>