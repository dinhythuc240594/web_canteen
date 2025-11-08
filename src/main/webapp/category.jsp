<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Food_CategoryDAO" %>
<%@ page import="model.FoodDAO" %>
<!DOCTYPE html>
<html>
<head>
    <title>Danh mục - FoodApp</title>
    <jsp:include page="/WEB-INF/jsp/common/head.jsp" />
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />

<div class="container mt-4">
    <div class="category-section">
        <!-- Category List -->
        <div class="category-list">
            <%
                List<Food_CategoryDAO> categories = (List<Food_CategoryDAO>) request.getAttribute("categories");
                String selectedCategoryId = (String) request.getParameter("categoryId");
                if (categories != null) {
                    for (Food_CategoryDAO category : categories) {
                        String activeClass = category.getCategory_id().equals(selectedCategoryId) ? "active" : "";
            %>
            <div class="category-item <%= activeClass %>"
                 onclick="location.href='category?categoryId=<%= category.getCategory_id() %>'">
                <%= category.getCategory_name() %>
            </div>
            <%
                    }
                }
            %>
        </div>

        <!-- Category Foods -->
        <div class="category-foods">
            <h2 class="slider-title">Món ăn theo danh mục (A -> Z)</h2>
            <div class="food-list">
                <%
                    List<FoodDAO> categoryFoods = (List<FoodDAO>) request.getAttribute("categoryFoods");
                    if (categoryFoods != null && !categoryFoods.isEmpty()) {
                        for (FoodDAO food : categoryFoods) {
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
                } else {
                %>
                <p class="text-muted">Chưa có món ăn nào trong danh mục này.</p>
                <%
                    }
                %>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
</body>
</html>