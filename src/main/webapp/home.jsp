<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Đồ ăn - Canteen ĐH</title>
    <jsp:include page="/WEB-INF/jsp/common/head.jsp" />
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />

<main class="min-h-screen bg-gradient-to-br from-gray-50 to-blue-50">
    <!-- Featured Foods Section -->
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
                    <!-- Mock Featured Food 1 -->
                    <div class="flex-shrink-0 w-48 bg-white rounded-lg shadow-sm overflow-hidden border border-gray-200 hover:shadow-md transition-shadow">
                        <div class="relative">
                            <img src="https://placehold.co/200x150/e74c3c/white?text=Cơm+Chiên"
                                 alt="Cơm chiên dương châu"
                                 class="w-full h-28 object-cover">
                            <span class="absolute top-2 right-2 bg-red-500 text-white text-xs px-1 py-0.5 rounded">-10%</span>
                        </div>
                        <div class="p-3">
                            <h3 class="font-medium text-gray-800 text-sm line-clamp-1">Cơm chiên dương châu</h3>
                            <div class="flex items-center mt-1">
                                <span class="text-sm font-bold text-blue-600">22,500đ</span>
                                <span class="ml-1 text-xs text-red-500 line-through">25,000đ</span>
                            </div>
                            <div class="flex items-center mt-1">
                                <i data-lucide="star" class="w-3 h-3 text-yellow-400 fill-current"></i>
                                <span class="ml-1 text-xs text-gray-600">4.5</span>
                                <span class="ml-auto bg-red-500 text-white text-xs px-1 py-0.5 rounded">-10%</span>
                            </div>
                            <button onclick="addToCart(1)"
                                    class="mt-2 w-full bg-blue-600 text-white py-1.5 rounded text-sm hover:bg-blue-700 transition-colors">
                                Thêm vào giỏ
                            </button>
                        </div>
                    </div>

                    <!-- Mock Featured Food 2 -->
                    <div class="flex-shrink-0 w-48 bg-white rounded-lg shadow-sm overflow-hidden border border-gray-200 hover:shadow-md transition-shadow">
                        <div class="relative">
                            <img src="https://placehold.co/200x150/27ae60/white?text=Sinh+Tố"
                                 alt="Sinh tố bơ"
                                 class="w-full h-28 object-cover">
                            <span class="absolute top-2 right-2 bg-red-500 text-white text-xs px-1 py-0.5 rounded">-15%</span>
                        </div>
                        <div class="p-3">
                            <h3 class="font-medium text-gray-800 text-sm line-clamp-1">Sinh tố bơ</h3>
                            <div class="flex items-center mt-1">
                                <span class="text-sm font-bold text-blue-600">18,700đ</span>
                                <span class="ml-1 text-xs text-red-500 line-through">22,000đ</span>
                            </div>
                            <div class="flex items-center mt-1">
                                <i data-lucide="star" class="w-3 h-3 text-yellow-400 fill-current"></i>
                                <span class="ml-1 text-xs text-gray-600">4.6</span>
                                <span class="ml-auto bg-red-500 text-white text-xs px-1 py-0.5 rounded">-15%</span>
                            </div>
                            <button onclick="addToCart(2)"
                                    class="mt-2 w-full bg-blue-600 text-white py-1.5 rounded text-sm hover:bg-blue-700 transition-colors">
                                Thêm vào giỏ
                            </button>
                        </div>
                    </div>

                    <!-- Mock Featured Food 3 -->
                    <div class="flex-shrink-0 w-48 bg-white rounded-lg shadow-sm overflow-hidden border border-gray-200 hover:shadow-md transition-shadow">
                        <div class="relative">
                            <img src="https://placehold.co/200x150/2ecc71/white?text=Cơm+Chay"
                                 alt="Cơm chay thập cẩm"
                                 class="w-full h-28 object-cover">
                            <span class="absolute top-2 right-2 bg-red-500 text-white text-xs px-1 py-0.5 rounded">-12%</span>
                        </div>
                        <div class="p-3">
                            <h3 class="font-medium text-gray-800 text-sm line-clamp-1">Cơm chay thập cẩm</h3>
                            <div class="flex items-center mt-1">
                                <span class="text-sm font-bold text-blue-600">24,640đ</span>
                                <span class="ml-1 text-xs text-red-500 line-through">28,000đ</span>
                            </div>
                            <div class="flex items-center mt-1">
                                <i data-lucide="star" class="w-3 h-3 text-yellow-400 fill-current"></i>
                                <span class="ml-1 text-xs text-gray-600">4.7</span>
                                <span class="ml-auto bg-red-500 text-white text-xs px-1 py-0.5 rounded">-12%</span>
                            </div>
                            <button onclick="addToCart(3)"
                                    class="mt-2 w-full bg-blue-600 text-white py-1.5 rounded text-sm hover:bg-blue-700 transition-colors">
                                Thêm vào giỏ
                            </button>
                        </div>
                    </div>

                    <!-- Mock Featured Food 4 -->
                    <div class="flex-shrink-0 w-48 bg-white rounded-lg shadow-sm overflow-hidden border border-gray-200 hover:shadow-md transition-shadow">
                        <div class="relative">
                            <img src="https://placehold.co/200x150/1abc9c/white?text=Salad"
                                 alt="Salad rau củ"
                                 class="w-full h-28 object-cover">
                            <span class="absolute top-2 right-2 bg-red-500 text-white text-xs px-1 py-0.5 rounded">-8%</span>
                        </div>
                        <div class="p-3">
                            <h3 class="font-medium text-gray-800 text-sm line-clamp-1">Salad rau củ</h3>
                            <div class="flex items-center mt-1">
                                <span class="text-sm font-bold text-blue-600">18,400đ</span>
                                <span class="ml-1 text-xs text-red-500 line-through">20,000đ</span>
                            </div>
                            <div class="flex items-center mt-1">
                                <i data-lucide="star" class="w-3 h-3 text-yellow-400 fill-current"></i>
                                <span class="ml-1 text-xs text-gray-600">4.2</span>
                                <span class="ml-auto bg-red-500 text-white text-xs px-1 py-0.5 rounded">-8%</span>
                            </div>
                            <button onclick="addToCart(4)"
                                    class="mt-2 w-full bg-blue-600 text-white py-1.5 rounded text-sm hover:bg-blue-700 transition-colors">
                                Thêm vào giỏ
                            </button>
                        </div>
                    </div>

                    <!-- Mock Featured Food 5 -->
                    <div class="flex-shrink-0 w-48 bg-white rounded-lg shadow-sm overflow-hidden border border-gray-200 hover:shadow-md transition-shadow">
                        <div class="relative">
                            <img src="https://placehold.co/200x150/9b59b6/white?text=Bánh+Mì"
                                 alt="Bánh mì thịt nướng"
                                 class="w-full h-28 object-cover">
                            <span class="absolute top-2 right-2 bg-red-500 text-white text-xs px-1 py-0.5 rounded">-5%</span>
                        </div>
                        <div class="p-3">
                            <h3 class="font-medium text-gray-800 text-sm line-clamp-1">Bánh mì thịt nướng</h3>
                            <div class="flex items-center mt-1">
                                <span class="text-sm font-bold text-blue-600">17,100đ</span>
                                <span class="ml-1 text-xs text-red-500 line-through">18,000đ</span>
                            </div>
                            <div class="flex items-center mt-1">
                                <i data-lucide="star" class="w-3 h-3 text-yellow-400 fill-current"></i>
                                <span class="ml-1 text-xs text-gray-600">4.3</span>
                                <span class="ml-auto bg-red-500 text-white text-xs px-1 py-0.5 rounded">-5%</span>
                            </div>
                            <button onclick="addToCart(5)"
                                    class="mt-2 w-full bg-blue-600 text-white py-1.5 rounded text-sm hover:bg-blue-700 transition-colors">
                                Thêm vào giỏ
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- New Foods Section -->
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
                    <!-- Mock New Food 1 -->
                    <div class="flex-shrink-0 w-48 bg-white rounded-lg shadow-sm overflow-hidden border border-gray-200 hover:shadow-md transition-shadow">
                        <img src="https://placehold.co/200x150/3498db/white?text=Phở+Bò"
                             alt="Phở bò"
                             class="w-full h-28 object-cover">
                        <div class="p-3">
                            <h3 class="font-medium text-gray-800 text-sm line-clamp-1">Phở bò</h3>
                            <div class="flex items-center mt-1">
                                <span class="text-sm font-bold text-blue-600">30,000đ</span>
                            </div>
                            <div class="flex items-center mt-1">
                                <i data-lucide="star" class="w-3 h-3 text-yellow-400 fill-current"></i>
                                <span class="ml-1 text-xs text-gray-600">4.8</span>
                            </div>
                            <button onclick="addToCart(6)"
                                    class="mt-2 w-full bg-blue-600 text-white py-1.5 rounded text-sm hover:bg-blue-700 transition-colors">
                                Thêm vào giỏ
                            </button>
                        </div>
                    </div>

                    <!-- Mock New Food 2 -->
                    <div class="flex-shrink-0 w-48 bg-white rounded-lg shadow-sm overflow-hidden border border-gray-200 hover:shadow-md transition-shadow">
                        <img src="https://placehold.co/200x150/f39c12/white?text=Sữa+Chua"
                             alt="Sữa chua nếp cẩm"
                             class="w-full h-28 object-cover">
                        <div class="p-3">
                            <h3 class="font-medium text-gray-800 text-sm line-clamp-1">Sữa chua nếp cẩm</h3>
                            <div class="flex items-center mt-1">
                                <span class="text-sm font-bold text-blue-600">15,000đ</span>
                            </div>
                            <div class="flex items-center mt-1">
                                <i data-lucide="star" class="w-3 h-3 text-yellow-400 fill-current"></i>
                                <span class="ml-1 text-xs text-gray-600">4.4</span>
                            </div>
                            <button onclick="addToCart(7)"
                                    class="mt-2 w-full bg-blue-600 text-white py-1.5 rounded text-sm hover:bg-blue-700 transition-colors">
                                Thêm vào giỏ
                            </button>
                        </div>
                    </div>

                    <!-- Mock New Food 3 -->
                    <div class="flex-shrink-0 w-48 bg-white rounded-lg shadow-sm overflow-hidden border border-gray-200 hover:shadow-md transition-shadow">
                        <img src="https://placehold.co/200x150/16a085/white?text=Bún+Chả"
                             alt="Bún chả Hà Nội"
                             class="w-full h-28 object-cover">
                        <div class="p-3">
                            <h3 class="font-medium text-gray-800 text-sm line-clamp-1">Bún chả Hà Nội</h3>
                            <div class="flex items-center mt-1">
                                <span class="text-sm font-bold text-blue-600">35,000đ</span>
                            </div>
                            <div class="flex items-center mt-1">
                                <i data-lucide="star" class="w-3 h-3 text-yellow-400 fill-current"></i>
                                <span class="ml-1 text-xs text-gray-600">4.5</span>
                            </div>
                            <button onclick="addToCart(8)"
                                    class="mt-2 w-full bg-blue-600 text-white py-1.5 rounded text-sm hover:bg-blue-700 transition-colors">
                                Thêm vào giỏ
                            </button>
                        </div>
                    </div>

                    <!-- Mock New Food 4 -->
                    <div class="flex-shrink-0 w-48 bg-white rounded-lg shadow-sm overflow-hidden border border-gray-200 hover:shadow-md transition-shadow">
                        <img src="https://placehold.co/200x150/8e44ad/white?text=Cơm+Tấm"
                             alt="Cơm tấm sườn bì"
                             class="w-full h-28 object-cover">
                        <div class="p-3">
                            <h3 class="font-medium text-gray-800 text-sm line-clamp-1">Cơm tấm sườn bì</h3>
                            <div class="flex items-center mt-1">
                                <span class="text-sm font-bold text-blue-600">32,000đ</span>
                            </div>
                            <div class="flex items-center mt-1">
                                <i data-lucide="star" class="w-3 h-3 text-yellow-400 fill-current"></i>
                                <span class="ml-1 text-xs text-gray-600">4.6</span>
                            </div>
                            <button onclick="addToCart(9)"
                                    class="mt-2 w-full bg-blue-600 text-white py-1.5 rounded text-sm hover:bg-blue-700 transition-colors">
                                Thêm vào giỏ
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- All Foods A-Z Section -->
    <section class="py-6 bg-gradient-to-b from-gray-50 to-blue-50">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <h2 class="text-xl font-bold text-gray-800 mb-4">Tất cả món ăn (A → Z)</h2>
            <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-3">
                <!-- Mock Food Items -->
                <div class="bg-white rounded-lg shadow-sm overflow-hidden border border-gray-200 hover:shadow-md transition-shadow">
                    <img src="https://placehold.co/200x150/e67e22/white?text=Trứng"
                         alt="Trứng ốp la"
                         class="w-full h-28 object-cover">
                    <div class="p-3">
                        <h3 class="font-medium text-gray-800 text-sm line-clamp-1">Trứng ốp la</h3>
                        <div class="flex items-center mt-1">
                            <span class="text-sm font-bold text-blue-600">8,000đ</span>
                        </div>
                        <div class="flex items-center mt-1">
                            <i data-lucide="star" class="w-3 h-3 text-yellow-400 fill-current"></i>
                            <span class="ml-1 text-xs text-gray-600">4.1</span>
                        </div>
                        <button onclick="addToCart(10)"
                                class="mt-2 w-full bg-blue-600 text-white py-1.5 rounded text-sm hover:bg-blue-700 transition-colors">
                            Thêm vào giỏ
                        </button>
                    </div>
                </div>

                <div class="bg-white rounded-lg shadow-sm overflow-hidden border border-gray-200 hover:shadow-md transition-shadow">
                    <div class="relative">
                        <img src="https://placehold.co/200x150/9b59b6/white?text=Bánh+Mì"
                             alt="Bánh mì thịt nướng"
                             class="w-full h-28 object-cover">
                        <span class="absolute top-2 right-2 bg-red-500 text-white text-xs px-1 py-0.5 rounded">-5%</span>
                    </div>
                    <div class="p-3">
                        <h3 class="font-medium text-gray-800 text-sm line-clamp-1">Bánh mì thịt nướng</h3>
                        <div class="flex items-center mt-1">
                            <span class="text-sm font-bold text-blue-600">17,100đ</span>
                            <span class="ml-1 text-xs text-red-500 line-through">18,000đ</span>
                        </div>
                        <div class="flex items-center mt-1">
                            <i data-lucide="star" class="w-3 h-3 text-yellow-400 fill-current"></i>
                            <span class="ml-1 text-xs text-gray-600">4.3</span>
                            <span class="ml-auto bg-red-500 text-white text-xs px-1 py-0.5 rounded">-5%</span>
                        </div>
                        <button onclick="addToCart(5)"
                                class="mt-2 w-full bg-blue-600 text-white py-1.5 rounded text-sm hover:bg-blue-700 transition-colors">
                            Thêm vào giỏ
                        </button>
                    </div>
                </div>

                <div class="bg-white rounded-lg shadow-sm overflow-hidden border border-gray-200 hover:shadow-md transition-shadow">
                    <img src="https://placehold.co/200x150/3498db/white?text=Phở+Bò"
                         alt="Phở bò"
                         class="w-full h-28 object-cover">
                    <div class="p-3">
                        <h3 class="font-medium text-gray-800 text-sm line-clamp-1">Phở bò</h3>
                        <div class="flex items-center mt-1">
                            <span class="text-sm font-bold text-blue-600">30,000đ</span>
                        </div>
                        <div class="flex items-center mt-1">
                            <i data-lucide="star" class="w-3 h-3 text-yellow-400 fill-current"></i>
                            <span class="ml-1 text-xs text-gray-600">4.8</span>
                        </div>
                        <button onclick="addToCart(6)"
                                class="mt-2 w-full bg-blue-600 text-white py-1.5 rounded text-sm hover:bg-blue-700 transition-colors">
                            Thêm vào giỏ
                        </button>
                    </div>
                </div>

                <div class="bg-white rounded-lg shadow-sm overflow-hidden border border-gray-200 hover:shadow-md transition-shadow">
                    <div class="relative">
                        <img src="https://placehold.co/200x150/e74c3c/white?text=Cơm+Chiên"
                             alt="Cơm chiên dương châu"
                             class="w-full h-28 object-cover">
                        <span class="absolute top-2 right-2 bg-red-500 text-white text-xs px-1 py-0.5 rounded">-10%</span>
                    </div>
                    <div class="p-3">
                        <h3 class="font-medium text-gray-800 text-sm line-clamp-1">Cơm chiên dương châu</h3>
                        <div class="flex items-center mt-1">
                            <span class="text-sm font-bold text-blue-600">22,500đ</span>
                            <span class="ml-1 text-xs text-red-500 line-through">25,000đ</span>
                        </div>
                        <div class="flex items-center mt-1">
                            <i data-lucide="star" class="w-3 h-3 text-yellow-400 fill-current"></i>
                            <span class="ml-1 text-xs text-gray-600">4.5</span>
                            <span class="ml-auto bg-red-500 text-white text-xs px-1 py-0.5 rounded">-10%</span>
                        </div>
                        <button onclick="addToCart(1)"
                                class="mt-2 w-full bg-blue-600 text-white py-1.5 rounded text-sm hover:bg-blue-700 transition-colors">
                            Thêm vào giỏ
                        </button>
                    </div>
                </div>

                <div class="bg-white rounded-lg shadow-sm overflow-hidden border border-gray-200 hover:shadow-md transition-shadow">
                    <img src="https://placehold.co/200x150/f39c12/white?text=Sữa+Chua"
                         alt="Sữa chua nếp cẩm"
                         class="w-full h-28 object-cover">
                    <div class="p-3">
                        <h3 class="font-medium text-gray-800 text-sm line-clamp-1">Sữa chua nếp cẩm</h3>
                        <div class="flex items-center mt-1">
                            <span class="text-sm font-bold text-blue-600">15,000đ</span>
                        </div>
                        <div class="flex items-center mt-1">
                            <i data-lucide="star" class="w-3 h-3 text-yellow-400 fill-current"></i>
                            <span class="ml-1 text-xs text-gray-600">4.4</span>
                        </div>
                        <button onclick="addToCart(7)"
                                class="mt-2 w-full bg-blue-600 text-white py-1.5 rounded text-sm hover:bg-blue-700 transition-colors">
                            Thêm vào giỏ
                        </button>
                    </div>
                </div>

                <div class="bg-white rounded-lg shadow-sm overflow-hidden border border-gray-200 hover:shadow-md transition-shadow">
                    <div class="relative">
                        <img src="https://placehold.co/200x150/2ecc71/white?text=Cơm+Chay"
                             alt="Cơm chay thập cẩm"
                             class="w-full h-28 object-cover">
                        <span class="absolute top-2 right-2 bg-red-500 text-white text-xs px-1 py-0.5 rounded">-12%</span>
                    </div>
                    <div class="p-3">
                        <h3 class="font-medium text-gray-800 text-sm line-clamp-1">Cơm chay thập cẩm</h3>
                        <div class="flex items-center mt-1">
                            <span class="text-sm font-bold text-blue-600">24,640đ</span>
                            <span class="ml-1 text-xs text-red-500 line-through">28,000đ</span>
                        </div>
                        <div class="flex items-center mt-1">
                            <i data-lucide="star" class="w-3 h-3 text-yellow-400 fill-current"></i>
                            <span class="ml-1 text-xs text-gray-600">4.7</span>
                            <span class="ml-auto bg-red-500 text-white text-xs px-1 py-0.5 rounded">-12%</span>
                        </div>
                        <button onclick="addToCart(3)"
                                class="mt-2 w-full bg-blue-600 text-white py-1.5 rounded text-sm hover:bg-blue-700 transition-colors">
                            Thêm vào giỏ
                        </button>
                    </div>
                </div>

                <div class="bg-white rounded-lg shadow-sm overflow-hidden border border-gray-200 hover:shadow-md transition-shadow">
                    <div class="relative">
                        <img src="https://placehold.co/200x150/1abc9c/white?text=Salad"
                             alt="Salad rau củ"
                             class="w-full h-28 object-cover">
                        <span class="absolute top-2 right-2 bg-red-500 text-white text-xs px-1 py-0.5 rounded">-8%</span>
                    </div>
                    <div class="p-3">
                        <h3 class="font-medium text-gray-800 text-sm line-clamp-1">Salad rau củ</h3>
                        <div class="flex items-center mt-1">
                            <span class="text-sm font-bold text-blue-600">18,400đ</span>
                            <span class="ml-1 text-xs text-red-500 line-through">20,000đ</span>
                        </div>
                        <div class="flex items-center mt-1">
                            <i data-lucide="star" class="w-3 h-3 text-yellow-400 fill-current"></i>
                            <span class="ml-1 text-xs text-gray-600">4.2</span>
                            <span class="ml-auto bg-red-500 text-white text-xs px-1 py-0.5 rounded">-8%</span>
                        </div>
                        <button onclick="addToCart(4)"
                                class="mt-2 w-full bg-blue-600 text-white py-1.5 rounded text-sm hover:bg-blue-700 transition-colors">
                            Thêm vào giỏ
                        </button>
                    </div>
                </div>

                <div class="bg-white rounded-lg shadow-sm overflow-hidden border border-gray-200 hover:shadow-md transition-shadow">
                    <img src="https://placehold.co/200x150/27ae60/white?text=Sinh+Tố"
                         alt="Sinh tố bơ"
                         class="w-full h-28 object-cover">
                    <div class="p-3">
                        <h3 class="font-medium text-gray-800 text-sm line-clamp-1">Sinh tố bơ</h3>
                        <div class="flex items-center mt-1">
                            <span class="text-sm font-bold text-blue-600">18,700đ</span>
                            <span class="ml-1 text-xs text-red-500 line-through">22,000đ</span>
                        </div>
                        <div class="flex items-center mt-1">
                            <i data-lucide="star" class="w-3 h-3 text-yellow-400 fill-current"></i>
                            <span class="ml-1 text-xs text-gray-600">4.6</span>
                            <span class="ml-auto bg-red-500 text-white text-xs px-1 py-0.5 rounded">-15%</span>
                        </div>
                        <button onclick="addToCart(2)"
                                class="mt-2 w-full bg-blue-600 text-white py-1.5 rounded text-sm hover:bg-blue-700 transition-colors">
                            Thêm vào giỏ
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
<jsp:include page="/WEB-INF/jsp/common/cart-sidebar.jsp" />

<script>
    // Mock cart data (stored in browser localStorage)
    let cart = JSON.parse(localStorage.getItem('cart')) || [];

    document.addEventListener('DOMContentLoaded', function() {
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
            1: { id: 1, name: 'Cơm chiên dương châu', price: 22500, image: 'https://placehold.co/200x150/e74c3c/white?text=Cơm+Chiên' },
            2: { id: 2, name: 'Sinh tố bơ', price: 18700, image: 'https://placehold.co/200x150/27ae60/white?text=Sinh+Tố' },
            3: { id: 3, name: 'Cơm chay thập cẩm', price: 24640, image: 'https://placehold.co/200x150/2ecc71/white?text=Cơm+Chay' },
            4: { id: 4, name: 'Salad rau củ', price: 18400, image: 'https://placehold.co/200x150/1abc9c/white?text=Salad' },
            5: { id: 5, name: 'Bánh mì thịt nướng', price: 17100, image: 'https://placehold.co/200x150/9b59b6/white?text=Bánh+Mì' },
            6: { id: 6, name: 'Phở bò', price: 30000, image: 'https://placehold.co/200x150/3498db/white?text=Phở+Bò' },
            7: { id: 7, name: 'Sữa chua nếp cẩm', price: 15000, image: 'https://placehold.co/200x150/f39c12/white?text=Sữa+Chua' },
            8: { id: 8, name: 'Bún chả Hà Nội', price: 35000, image: 'https://placehold.co/200x150/16a085/white?text=Bún+Chả' },
            9: { id: 9, name: 'Cơm tấm sườn bì', price: 32000, image: 'https://placehold.co/200x150/8e44ad/white?text=Cơm+Tấm' },
            10: { id: 10, name: 'Trứng ốp la', price: 8000, image: 'https://placehold.co/200x150/e67e22/white?text=Trứng' }
        };

        const food = foods[foodId];
        if (!food) return;

        const existingItem = cart.find(item => item.id === foodId);
        if (existingItem) {
            existingItem.quantity += 1;
        } else {
            cart.push({ ...food, quantity: 1 });
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