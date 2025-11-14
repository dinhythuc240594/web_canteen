<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="dto.FoodDTO" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Đồ ăn - Canteen ĐH</title>
    <jsp:include page="/WEB-INF/jsp/common/head.jsp"/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/header.jsp"/>

<%

    model.Page<dto.FoodDTO> pageFood = (model.Page<dto.FoodDTO>) request.getAttribute("pageFood");
    java.util.List<dto.FoodDTO> foods = (java.util.List<dto.FoodDTO>) pageFood.getData();

    model.PageRequest pageReq = (model.PageRequest) request.getAttribute("pageReq");
    String keyword = pageReq.getKeyword();
    String orderField = pageReq.getOrderField();
    String sortField = pageReq.getSortField();
    int totalPage = pageFood.getTotalPage();

    List<dto.FoodDTO> newFoods = (List<dto.FoodDTO>) request.getAttribute("newFoods");
    List<dto.FoodDTO> promotionFoods = (List<dto.FoodDTO>) request.getAttribute("promotionFoods");
    List<dto.FoodDTO> allFoods = foods;

%>

<main class="min-h-screen bg-gradient-to-br from-gray-50 to-blue-50">
    <!-- Search Form -->
    <section class="py-4 bg-white shadow-sm">
        <div class="max-w-7xl mx-auto px-4">
            <form action="foods?action=list" method="post" class="flex flex-wrap gap-3 items-end">
                <!-- Search Input -->
                <div class="flex-1 min-w-[200px]">
                    <input type="text"
                           name="keyword"
                           id="keyword"
                           placeholder="Tìm kiếm món ăn..."
                           class="w-full px-4 py-2.5 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all">
                </div>

                <!-- Sort Field -->
                <div class="w-32">
                    <select name="sortfield"
                            class="w-full px-3 py-2.5 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all">
                        <option value="name">Tên</option>
                        <option value="price">Giá</option>
<%--                        <option value="promotion">Giảm giá</option>--%>
                    </select>
                </div>

<%--                <!-- Sort Order -->--%>
<%--                <div class="w-28">--%>
<%--                    <select name="orderfield"--%>
<%--                            class="w-full px-3 py-2.5 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all">--%>
<%--                        <option value="ASC">A → Z</option>--%>
<%--                        <option value="DESC">Z → A</option>--%>
<%--                    </select>--%>
<%--                </div>--%>

                <!-- Search Button -->
                <button type="submit"
                        class="px-6 py-2.5 bg-gradient-to-r from-blue-500 to-blue-600 hover:from-blue-600 hover:to-blue-700 text-white font-medium rounded-lg shadow-md hover:shadow-lg transition-all">
                    Tìm kiếm
                </button>
            </form>
        </div>
    </section>

    <!-- Featured Foods Section - Đồ ăn ưu đãi-->
    <section class="py-6 bg-white/80 backdrop-blur-sm">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <div class="flex items-center justify-between mb-4">
                <h2 class="text-xl font-bold text-gray-800">Đồ ăn ưu đãi</h2>
                <div class="flex space-x-2">
                    <button onclick="scrollSection('featured', 'left')"
                            class="p-1 bg-gray-200 hover:bg-gray-300 rounded-full transition-colors shadow-sm">
                        <i data-lucide="chevron-left" class="w-4 h-4"></i>
                    </button>
                    <button onclick="scrollSection('featured', 'right')"
                            class="p-1 bg-gray-200 hover:bg-gray-300 rounded-full transition-colors shadow-sm">
                        <i data-lucide="chevron-right" class="w-4 h-4"></i>
                    </button>
                </div>
            </div>
            <div id="featured-container" class="overflow-x-auto scrollbar-hide"
            <%--                 style="scrollbar-width: none; -ms-overflow-style: none; -webkit-overflow-scrolling: touch;">--%>>
                <div class="flex space-x-3 pb-3" style="min-width: max-content;">
                    <%
                        if (promotionFoods != null) {
                            for (FoodDTO food : promotionFoods) {
                    %>

                    <div class="flex-shrink-0 w-48 bg-white rounded-xl shadow-sm overflow-hidden border border-gray-100 hover:shadow-lg hover:scale-105 transition-all duration-300">
                        <!-- Image Container -->
                        <div class="relative group">
                            <img src="<%= request.getContextPath() + "/" + food.getImage() %>"
                                 alt="<%= food.getNameFood() %>"
                                 class="w-full h-44 object-cover group-hover:scale-110 transition-transform duration-300">

                            <!-- Promotion Badge - Chỉ hiện nếu có giảm giá -->
                            <% if (food.getPromotion() > 0) { %>
                            <span class="absolute top-2 right-2 bg-gradient-to-r from-red-500 to-pink-500 text-white text-xs font-bold px-2 py-1 rounded-lg shadow-lg">-<%= food.getPromotion() %>%</span>
                            <% } %>
                        </div>


                        <!-- Content -->
                        <div class="p-1">
                            <!-- Food Name -->
                            <h3 class="font-semibold text-gray-800 text-sm line-clamp-1 mb-1"><%= food.getNameFood() %></h3>

                            <!-- Price - Tự động hiển thị theo có/không có promotion -->
                            <div class="flex items-center justify-between mb-1.5"><% if (food.getPromotion() > 0) { %>
                                <!-- Có giảm giá: Hiện giá sau giảm + giá gốc -->
                                <span class="text-sm font-bold text-blue-600"><%= String.format("%,.0f", food.getPriceAfterPromotion()) %>đ</span>
                                <span class="text-xs text-gray-400 line-through"><%= String.format("%,.0f", food.getPriceFood()) %>đ</span>
                                <% } else { %>
                                <!-- Không giảm giá: Chỉ hiện giá gốc -->
                                <span class="text-sm font-bold text-blue-600"><%= String.format("%,.0f", food.getPriceFood()) %>đ</span>
                                <% } %>
                            </div>

                            <!-- Button -->
                            <button onclick="addToCart(<%= food.getId() %>)"
                                    class="w-full bg-gradient-to-r from-blue-500 to-blue-600 hover:from-blue-600 hover:to-blue-700 text-white text-xs font-medium py-1.5 rounded-lg shadow-md hover:shadow-lg transition-all">
                                Thêm vào giỏ
                            </button>
                        </div>
                    </div>

                    <%
                            }
                        }
                    %>
                </div>
            </div>
        </div>
    </section>

    <!-- New Foods Section - Đồ ăn mới -->
    <section class="py-6 bg-white/80 backdrop-blur-sm">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <div class="flex items-center justify-between mb-4">
                <h2 class="text-xl font-bold text-gray-800">Đồ ăn mới</h2>
                <div class="flex space-x-2">
                    <button onclick="scrollSection('new', 'left')"
                            class="p-1 bg-gray-200 hover:bg-gray-300 rounded-full transition-colors shadow-sm">
                        <i data-lucide="chevron-left" class="w-4 h-4"></i>
                    </button>
                    <button onclick="scrollSection('new', 'right')"
                            class="p-1 bg-gray-200 hover:bg-gray-300 rounded-full transition-colors shadow-sm">
                        <i data-lucide="chevron-right" class="w-4 h-4"></i>
                    </button>
                </div>
            </div>
            <div id="new-container" class="overflow-x-auto scrollbar-hide"
            <%--                 style="scrollbar-width: none; -ms-overflow-style: none; -webkit-overflow-scrolling: touch;">--%>>
                <div class="flex space-x-3 pb-3" style="min-width: max-content;">
                    <%

                        if (newFoods != null) {
                            for (FoodDTO food : newFoods) {
                    %>
                    <div class="flex-shrink-0 w-48 bg-white rounded-xl shadow-sm overflow-hidden border border-gray-100 hover:shadow-lg hover:scale-105 transition-all duration-300">
                        <!-- Image Container -->
                        <div class="relative group">
                            <img src="<%= request.getContextPath() + "/" + food.getImage() %>"
                                 alt="<%= food.getNameFood() %>"
                                 class="w-full h-44 object-cover group-hover:scale-110 transition-transform duration-300">

                            <!-- Promotion Badge - Chỉ hiện nếu có giảm giá -->
                            <% if (food.getPromotion() > 0) { %>
                            <span class="absolute top-2 right-2 bg-gradient-to-r from-red-500 to-pink-500 text-white text-xs font-bold px-2 py-1 rounded-lg shadow-lg">-<%= food.getPromotion() %>%</span>
                            <% } %>
                        </div>


                        <!-- Content -->
                        <div class="p-1">
                            <!-- Food Name -->
                            <h3 class="font-semibold text-gray-800 text-sm line-clamp-1 mb-1"><%= food.getNameFood() %></h3>

                            <!-- Price - Tự động hiển thị theo có/không có promotion -->
                            <div class="flex items-center justify-between mb-1.5"><% if (food.getPromotion() > 0) { %>
                                <!-- Có giảm giá: Hiện giá sau giảm + giá gốc -->
                                <span class="text-sm font-bold text-blue-600"><%= String.format("%,.0f", food.getPriceAfterPromotion()) %>đ</span>
                                <span class="text-xs text-gray-400 line-through"><%= String.format("%,.0f", food.getPriceFood()) %>đ</span>
                                <% } else { %>
                                <!-- Không giảm giá: Chỉ hiện giá gốc -->
                                <span class="text-sm font-bold text-blue-600"><%= String.format("%,.0f", food.getPriceFood()) %>đ</span>
                                <% } %>
                            </div>

                            <!-- Button -->
                            <button onclick="addToCart(<%= food.getId() %>)"
                                    class="w-full bg-gradient-to-r from-blue-500 to-blue-600 hover:from-blue-600 hover:to-blue-700 text-white text-xs font-medium py-1.5 rounded-lg shadow-md hover:shadow-lg transition-all">
                                Thêm vào giỏ
                            </button>
                        </div>
                    </div>

                    <%
                            }
                        }
                    %>

                </div>
            </div>
        </div>
    </section>

    <!-- All Foods A-Z Section - Tất cả món ăn-->
    <section class="py-6 bg-gradient-to-b from-gray-50 to-blue-50">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <h2 class="text-xl font-bold text-gray-800 mb-4">Tất cả món ăn</h2>
            <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-3">

                <%

                    if (allFoods != null) {
                        for (FoodDTO food : allFoods) {
                %>

                <div class="bg-white rounded-xl shadow-sm overflow-hidden border border-gray-100 hover:shadow-lg hover:scale-105 transition-all duration-300">
                    <!-- Image Container -->
                    <div class="relative group">
                        <img src="<%= request.getContextPath() + "/" + food.getImage() %>"
                             alt="<%= food.getNameFood() %>"
                             class="w-full h-48 object-cover group-hover:scale-110 transition-transform duration-300">

                        <!-- Promotion Badge - Chỉ hiện nếu có giảm giá -->
                        <% if (food.getPromotion() > 0) { %>
                        <span class="absolute top-2 right-2 bg-gradient-to-r from-red-500 to-pink-500 text-white text-xs font-bold px-2 py-1 rounded-lg shadow-lg">-<%= food.getPromotion() %>%</span>
                        <% } %>
                    </div>

                    <!-- Content -->
                    <div class="p-2.5">
                        <!-- Food Name -->
                        <h3 class="font-semibold text-gray-800 text-sm line-clamp-1 mb-1.5">
                            <%= food.getNameFood() %>
                        </h3>

                        <!-- Price -->
                        <div class="flex items-center justify-between mb-2">
                            <% if (food.getPromotion() > 0) { %>
                            <span class="text-base font-bold text-blue-600"><%= String.format("%,.0f", food.getPriceAfterPromotion()) %>đ</span>
                            <span class="text-xs text-gray-400 line-through"><%= String.format("%,.0f", food.getPriceFood()) %>đ</span>
                            <% } else { %>
                            <span class="text-base font-bold text-blue-600"><%= String.format("%,.0f", food.getPriceFood()) %>đ</span>
                            <% } %>
                        </div>

                        <!-- Button -->
                        <button onclick="addToCart(<%= food.getId() %>)"
                                class="w-full bg-gradient-to-r from-blue-500 to-blue-600 hover:from-blue-600 hover:to-blue-700 text-white text-sm font-medium py-2 rounded-lg shadow-md hover:shadow-lg transition-all">
                            Thêm vào giỏ
                        </button>
                    </div>
                </div>

                <%
                        }
                    }
                %>

                <nav aria-label="Page navigation">
                    <ul class="pagination">
                        <% for (int i = 1; i < totalPage; i++) { %>
                        <li class="page-item"><a class="page-link"
                                                 href="books?page=<%= i %>&keyword=<%= keyword %>"><%= i %>
                        </a></li>
                        <% } %>
                    </ul>
                </nav>
            </div>
        </div>
    </section>
</main>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>
<jsp:include page="/WEB-INF/jsp/common/cart-sidebar.jsp"/>

<script>
    // Mock cart data (stored in browser localStorage)
    let cart = JSON.parse(localStorage.getItem('cart')) || [];

    document.addEventListener('DOMContentLoaded', function () {
        lucide.createIcons();
        updateCartCount();
        renderCart();
    });

    function scrollSection(sectionId, direction) {
        const container = document.getElementById(sectionId + '-container');
        if (container) {
            const scrollAmount = 200;
            container.scrollBy({
                left: direction === 'right' ? scrollAmount : -scrollAmount,
                behavior: 'smooth'
            });
        }
    }

    function addToCart(foodId) {
        // Mock food data
        const foods = {
            1: {
                id: 1,
                name: 'Cơm chiên dương châu',
                price: 22500,
                image: 'https://placehold.co/200x150/e74c3c/white?text=Cơm+Chiên'
            },
            2: {
                id: 2,
                name: 'Sinh tố bơ',
                price: 18700,
                image: 'https://placehold.co/200x150/27ae60/white?text=Sinh+Tố'
            },
            3: {
                id: 3,
                name: 'Cơm chay thập cẩm',
                price: 24640,
                image: 'https://placehold.co/200x150/2ecc71/white?text=Cơm+Chay'
            },
            4: {
                id: 4,
                name: 'Salad rau củ',
                price: 18400,
                image: 'https://placehold.co/200x150/1abc9c/white?text=Salad'
            },
            5: {
                id: 5,
                name: 'Bánh mì thịt nướng',
                price: 17100,
                image: 'https://placehold.co/200x150/9b59b6/white?text=Bánh+Mì'
            },
            6: {id: 6, name: 'Phở bò', price: 30000, image: 'https://placehold.co/200x150/3498db/white?text=Phở+Bò'},
            7: {
                id: 7,
                name: 'Sữa chua nếp cẩm',
                price: 15000,
                image: 'https://placehold.co/200x150/f39c12/white?text=Sữa+Chua'
            },
            8: {
                id: 8,
                name: 'Bún chả Hà Nội',
                price: 35000,
                image: 'https://placehold.co/200x150/16a085/white?text=Bún+Chả'
            },
            9: {
                id: 9,
                name: 'Cơm tấm sườn bì',
                price: 32000,
                image: 'https://placehold.co/200x150/8e44ad/white?text=Cơm+Tấm'
            },
            10: {
                id: 10,
                name: 'Trứng ốp la',
                price: 8000,
                image: 'https://placehold.co/200x150/e67e22/white?text=Trứng'
            }
        };

        const food = foods[foodId];
        if (!food) return;

        const existingItem = cart.find(item => item.id === foodId);
        if (existingItem) {
            existingItem.quantity += 1;
        } else {
            cart.push({...food, quantity: 1});
        }

        localStorage.setItem('cart', JSON.stringify(cart));
        updateCartCount();
        renderCart();

        // Show notification
        alert('Đã thêm ' + food.name + ' vào giỏ hàng!');
    }

    function updateCartCount() {
        const count = cart.reduce((sum, item) => sum + item.quantity, 0);
        const cartCountElement = document.getElementById('cart-count');
        if (cartCountElement) {
            if (count > 0) {
                cartCountElement.textContent = count;
                cartCountElement.classList.remove('hidden');
            } else {
                cartCountElement.classList.add('hidden');
            }
        }
    }

    function renderCart() {
        const cartItemsContainer = document.getElementById('cart-items');
        const cartFooter = document.getElementById('cart-footer');

        if (!cartItemsContainer) return;

        if (cart.length === 0) {
            cartItemsContainer.innerHTML = `
                <div class="text-center py-6">
                    <i data-lucide="shopping-cart" class="w-8 h-8 mx-auto text-gray-400"></i>
                    <p class="mt-2 text-sm text-gray-500">Giỏ hàng trống</p>
                </div>
            `;
            if (cartFooter) cartFooter.classList.add('hidden');
        } else {
            let total = 0;
            cartItemsContainer.innerHTML = cart.map(item => {
                total += item.price * item.quantity;
                return `
                    <div class="flex items-center space-x-3 bg-gray-50 p-2 rounded mb-2">
                        <img src="${item.image}" alt="${item.name}" class="w-12 h-12 object-cover rounded">
                        <div class="flex-1 min-w-0">
                            <h3 class="text-sm font-medium text-gray-800 truncate">${item.name}</h3>
                            <p class="text-blue-600 text-sm font-semibold">${item.price.toLocaleString('vi-VN')}đ</p>
                        </div>
                        <div class="flex items-center space-x-1">
                            <button onclick="updateQuantity(${item.id}, ${item.quantity - 1})"
                                    class="p-0.5 rounded-full bg-gray-200 hover:bg-gray-300">
                                <i data-lucide="minus" class="w-3 h-3"></i>
                            </button>
                            <span class="w-6 text-center text-sm">${item.quantity}</span>
                            <button onclick="updateQuantity(${item.id}, ${item.quantity + 1})"
                                    class="p-0.5 rounded-full bg-gray-200 hover:bg-gray-300">
                                <i data-lucide="plus" class="w-3 h-3"></i>
                            </button>
                        </div>
                        <button onclick="removeFromCart(${item.id})"
                                class="p-0.5 text-red-600 hover:text-red-800">
                            <i data-lucide="x" class="w-3.5 h-3.5"></i>
                        </button>
                    </div>
                `;
            }).join('');

            if (cartFooter) {
                document.getElementById('cart-total').textContent = total.toLocaleString('vi-VN') + 'đ';
                cartFooter.classList.remove('hidden');
            }

            lucide.createIcons();
        }
    }

    function updateQuantity(foodId, newQuantity) {
        if (newQuantity === 0) {
            removeFromCart(foodId);
        } else {
            const item = cart.find(item => item.id === foodId);
            if (item) {
                item.quantity = newQuantity;
                localStorage.setItem('cart', JSON.stringify(cart));
                updateCartCount();
                renderCart();
            }
        }
    }

    function removeFromCart(foodId) {
        cart = cart.filter(item => item.id !== foodId);
        localStorage.setItem('cart', JSON.stringify(cart));
        updateCartCount();
        renderCart();
    }
</script>
</body>
</html>