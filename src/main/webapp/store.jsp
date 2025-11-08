<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Quầy - Canteen ĐH</title>
    <jsp:include page="/WEB-INF/jsp/common/head.jsp" />
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />

<div class="min-h-screen bg-gradient-to-br from-gray-50 to-blue-50 py-6">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <h2 class="text-xl font-bold text-gray-800 mb-6">Danh sách quầy</h2>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <!-- Mock Store 1 -->
            <div class="bg-white rounded-lg shadow-sm overflow-hidden border border-gray-200 hover:shadow-md transition-shadow">
                <div class="p-4">
                    <h3 class="text-lg font-semibold text-gray-800 mb-2">Quầy cơm và món khô</h3>
                    <p class="text-gray-600 text-sm mb-3">Cơm, mì xào, hủ tiếu xào...</p>
                    <div class="flex items-center justify-between">
                        <span class="text-xs text-gray-500">Có sẵn</span>
                        <div class="flex space-x-2">
                            <span class="px-2 py-0.5 bg-blue-100 text-blue-800 text-xs rounded-full">
                                Mở cửa
                            </span>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Mock Store 2 -->
            <div class="bg-white rounded-lg shadow-sm overflow-hidden border border-gray-200 hover:shadow-md transition-shadow">
                <div class="p-4">
                    <h3 class="text-lg font-semibold text-gray-800 mb-2">Quầy món nước</h3>
                    <p class="text-gray-600 text-sm mb-3">Phở, bún, hủ tiếu, cháo...</p>
                    <div class="flex items-center justify-between">
                        <span class="text-xs text-gray-500">Có sẵn</span>
                        <div class="flex space-x-2">
                            <span class="px-2 py-0.5 bg-blue-100 text-blue-800 text-xs rounded-full">
                                Mở cửa
                            </span>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Mock Store 3 -->
            <div class="bg-white rounded-lg shadow-sm overflow-hidden border border-gray-200 hover:shadow-md transition-shadow">
                <div class="p-4">
                    <h3 class="text-lg font-semibold text-gray-800 mb-2">Quầy đồ ăn nhanh</h3>
                    <p class="text-gray-600 text-sm mb-3">Bánh mì, hamburger, pizza...</p>
                    <div class="flex items-center justify-between">
                        <span class="text-xs text-gray-500">Có sẵn</span>
                        <div class="flex space-x-2">
                            <span class="px-2 py-0.5 bg-blue-100 text-blue-800 text-xs rounded-full">
                                Mở cửa
                            </span>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Mock Store 4 -->
            <div class="bg-white rounded-lg shadow-sm overflow-hidden border border-gray-200 hover:shadow-md transition-shadow">
                <div class="p-4">
                    <h3 class="text-lg font-semibold text-gray-800 mb-2">Quầy đồ uống, ăn vặt, tráng miệng</h3>
                    <p class="text-gray-600 text-sm mb-3">Nước ngọt, sinh tố, kem, sữa chua...</p>
                    <div class="flex items-center justify-between">
                        <span class="text-xs text-gray-500">Có sẵn</span>
                        <div class="flex space-x-2">
                            <span class="px-2 py-0.5 bg-blue-100 text-blue-800 text-xs rounded-full">
                                Mở cửa
                            </span>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Mock Store 5 -->
            <div class="bg-white rounded-lg shadow-sm overflow-hidden border border-gray-200 hover:shadow-md transition-shadow">
                <div class="p-4">
                    <h3 class="text-lg font-semibold text-gray-800 mb-2">Quầy món chay và eatclean</h3>
                    <p class="text-gray-600 text-sm mb-3">Món chay, salad, thực phẩm organic...</p>
                    <div class="flex items-center justify-between">
                        <span class="text-xs text-gray-500">Có sẵn</span>
                        <div class="flex space-x-2">
                            <span class="px-2 py-0.5 bg-blue-100 text-blue-800 text-xs rounded-full">
                                Mở cửa
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
<script>
    document.addEventListener('DOMContentLoaded', function() {
        lucide.createIcons();
    });
</script>
</body>
</html>