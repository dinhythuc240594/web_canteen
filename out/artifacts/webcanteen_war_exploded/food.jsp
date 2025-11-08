<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.FoodDAO" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đồ ăn - FoodApp</title>
    <jsp:include page="/WEB-INF/jsp/common/head.jsp" />
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />

<div class="container mt-4">
    <!-- Đồ ăn mới -->
    <div class="slider-container">
        <h2 class="slider-title">Đồ ăn mới</h2>
        <div class="slider">
            <%
                List<FoodDAO> newFoods = (List<FoodDAO>) request.getAttribute("newFoods");
                if (newFoods != null) {
                    for (FoodDAO food : newFoods) {
            %>
            <div class="slider-item" onclick="location.href='food?action=detail&id=<%= food.getFood_id() %>'">
                <div class="slider-img">
                    <% if (food.getImage() != null && !food.getImage().isEmpty()) { %>
                    <img src="<%= food.getImage() %>" alt="<%= food.getFood_name() %>" style="width:100%; height:100%; object-fit:cover;">
                    <% } else { %>
                    <%= food.getFood_name() %>
                    <% } %>
                </div>
                <div class="slider-content">
                    <h3><%= food.getFood_name() %></h3>
                    <p><%= food.getDescription() != null ? food.getDescription() : "Món ngon" %></p>
                </div>
            </div>
            <%
                    }
                }
            %>
        </div>
    </div>

    <!-- Ưu đãi -->
    <div class="slider-container">
        <h2 class="slider-title">Ưu đãi</h2>
        <div class="slider">
            <%
                List<FoodDAO> promotionFoods = (List<FoodDAO>) request.getAttribute("promotionFoods");
                if (promotionFoods != null) {
                    for (FoodDAO food : promotionFoods) {
            %>
            <div class="slider-item" onclick="location.href='food?action=detail&id=<%= food.getFood_id() %>'">
                <div class="slider-img">
                    <% if (food.getImage() != null && !food.getImage().isEmpty()) { %>
                    <img src="<%= food.getImage() %>" alt="<%= food.getFood_name() %>" style="width:100%; height:100%; object-fit:cover;">
                    <% } else { %>
                    Ưu đãi
                    <% } %>
                </div>
                <div class="slider-content">
                    <h3><%= food.getFood_name() %></h3>
                    <p>Giảm giá đặc biệt</p>
                </div>
            </div>
            <%
                    }
                }
            %>
        </div>
    </div>

    <!-- Danh sách món (A -> Z) -->
    <h2 class="slider-title">Danh sách món (A -> Z)</h2>
    <div class="food-list">
        <%
            List<FoodDAO> allFoods = (List<FoodDAO>) request.getAttribute("allFoods");
            if (allFoods != null) {
                for (FoodDAO food : allFoods) {
        %>
        <div class="food-item" onclick="location.href='food?action=detail&id=<%= food.getFood_id() %>'">
            <div class="food-img">
                <% if (food.getImage() != null && !food.getImage().isEmpty()) { %>
                <img src="<%= food.getImage() %>" alt="<%= food.getFood_name() %>" style="width:100%; height:100%; object-fit:cover;">
                <% } else { %>
                <%= food.getFood_name() %>
                <% } %>
            </div>
            <div class="food-name"><%= food.getFood_name() %></div>
            <div class="food-price"><%= String.format("%,d", food.getPrice()) %>đ</div>
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