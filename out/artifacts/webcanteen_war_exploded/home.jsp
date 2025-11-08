<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Đồ ăn - Canteen ĐH</title>
    <jsp:include page="/WEB-INF/jsp/common/head.jsp" />
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />

<main class="py-4">
    <div class="container">
        <!-- Featured/Discount Foods -->
        <section class="mb-4 fade-in">
            <h4 class="fw-bold text-dark mb-3">
                <i class="bi bi-fire text-danger"></i> Đồ ăn ưu đãi
            </h4>

            <div class="horizontal-scroll">
                <div class="scroll-container">
                    <!-- Food Item 1 -->
                    <div class="card food-card border-0 shadow-sm">
                        <div class="position-relative">
                            <img src="https://placehold.co/200x120/e74c3c/white?text=Com+Chien"
                                 class="card-img-top food-card-img" alt="Cơm chiên dương châu">
                            <span class="discount-badge">-10%</span>
                        </div>
                        <div class="card-body">
                            <h6 class="card-title text-truncate mb-2">Cơm chiên dương châu</h6>
                            <div class="d-flex align-items-center mb-2">
                                <span class="text-primary fw-bold">22,500đ</span>
                                <span class="text-decoration-line-through text-muted ms-2 small">25,000đ</span>
                            </div>
                            <div class="d-flex align-items-center mb-2">
                                <i class="bi bi-star-fill rating-star small"></i>
                                <span class="ms-1 small text-muted">4.5</span>
                            </div>
                            <a href="login.jsp" class="btn btn-primary btn-sm w-100">
                                <i class="bi bi-cart-plus"></i> Thêm vào giỏ
                            </a>
                        </div>
                    </div>

                    <!-- Food Item 2 -->
                    <div class="card food-card border-0 shadow-sm">
                        <div class="position-relative">
                            <img src="https://placehold.co/200x120/27ae60/white?text=Sinh+To"
                                 class="card-img-top food-card-img" alt="Sinh tố bơ">
                            <span class="discount-badge">-15%</span>
                        </div>
                        <div class="card-body">
                            <h6 class="card-title text-truncate mb-2">Sinh tố bơ</h6>
                            <div class="d-flex align-items-center mb-2">
                                <span class="text-primary fw-bold">18,700đ</span>
                                <span class="text-decoration-line-through text-muted ms-2 small">22,000đ</span>
                            </div>
                            <div class="d-flex align-items-center mb-2">
                                <i class="bi bi-star-fill rating-star small"></i>
                                <span class="ms-1 small text-muted">4.6</span>
                            </div>
                            <a href="login.jsp" class="btn btn-primary btn-sm w-100">
                                <i class="bi bi-cart-plus"></i> Thêm vào giỏ
                            </a>
                        </div>
                    </div>

                    <!-- Food Item 3 -->
                    <div class="card food-card border-0 shadow-sm">
                        <div class="position-relative">
                            <img src="https://placehold.co/200x120/2ecc71/white?text=Com+Chay"
                                 class="card-img-top food-card-img" alt="Cơm chay thập cẩm">
                            <span class="discount-badge">-12%</span>
                        </div>
                        <div class="card-body">
                            <h6 class="card-title text-truncate mb-2">Cơm chay thập cẩm</h6>
                            <div class="d-flex align-items-center mb-2">
                                <span class="text-primary fw-bold">24,640đ</span>
                                <span class="text-decoration-line-through text-muted ms-2 small">28,000đ</span>
                            </div>
                            <div class="d-flex align-items-center mb-2">
                                <i class="bi bi-star-fill rating-star small"></i>
                                <span class="ms-1 small text-muted">4.7</span>
                            </div>
                            <a href="login.jsp" class="btn btn-primary btn-sm w-100">
                                <i class="bi bi-cart-plus"></i> Thêm vào giỏ
                            </a>
                        </div>
                    </div>

                    <!-- Food Item 4 -->
                    <div class="card food-card border-0 shadow-sm">
                        <div class="position-relative">
                            <img src="https://placehold.co/200x120/1abc9c/white?text=Salad"
                                 class="card-img-top food-card-img" alt="Salad rau củ">
                            <span class="discount-badge">-8%</span>
                        </div>
                        <div class="card-body">
                            <h6 class="card-title text-truncate mb-2">Salad rau củ</h6>
                            <div class="d-flex align-items-center mb-2">
                                <span class="text-primary fw-bold">18,400đ</span>
                                <span class="text-decoration-line-through text-muted ms-2 small">20,000đ</span>
                            </div>
                            <div class="d-flex align-items-center mb-2">
                                <i class="bi bi-star-fill rating-star small"></i>
                                <span class="ms-1 small text-muted">4.2</span>
                            </div>
                            <a href="login.jsp" class="btn btn-primary btn-sm w-100">
                                <i class="bi bi-cart-plus"></i> Thêm vào giỏ
                            </a>
                        </div>
                    </div>

                    <!-- Food Item 5 -->
                    <div class="card food-card border-0 shadow-sm">
                        <div class="position-relative">
                            <img src="https://placehold.co/200x120/9b59b6/white?text=Banh+Mi"
                                 class="card-img-top food-card-img" alt="Bánh mì">
                            <span class="discount-badge">-5%</span>
                        </div>
                        <div class="card-body">
                            <h6 class="card-title text-truncate mb-2">Bánh mì thịt nướng</h6>
                            <div class="d-flex align-items-center mb-2">
                                <span class="text-primary fw-bold">17,100đ</span>
                                <span class="text-decoration-line-through text-muted ms-2 small">18,000đ</span>
                            </div>
                            <div class="d-flex align-items-center mb-2">
                                <i class="bi bi-star-fill rating-star small"></i>
                                <span class="ms-1 small text-muted">4.3</span>
                            </div>
                            <a href="login.jsp" class="btn btn-primary btn-sm w-100">
                                <i class="bi bi-cart-plus"></i> Thêm vào giỏ
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <!-- New Foods -->
        <section class="mb-4 fade-in">
            <h4 class="fw-bold text-dark mb-3">
                <i class="bi bi-stars text-warning"></i> Đồ ăn mới
            </h4>

            <div class="horizontal-scroll">
                <div class="scroll-container">
                    <!-- New Food Item 1 -->
                    <div class="card food-card border-0 shadow-sm">
                        <img src="https://placehold.co/200x120/3498db/white?text=Pho+Bo"
                             class="card-img-top food-card-img" alt="Phở bò">
                        <div class="card-body">
                            <h6 class="card-title text-truncate mb-2">Phở bò</h6>
                            <div class="mb-2">
                                <span class="text-primary fw-bold">30,000đ</span>
                            </div>
                            <div class="d-flex align-items-center mb-2">
                                <i class="bi bi-star-fill rating-star small"></i>
                                <span class="ms-1 small text-muted">4.8</span>
                            </div>
                            <a href="login.jsp" class="btn btn-primary btn-sm w-100">
                                <i class="bi bi-cart-plus"></i> Thêm vào giỏ
                            </a>
                        </div>
                    </div>

                    <!-- New Food Item 2 -->
                    <div class="card food-card border-0 shadow-sm">
                        <img src="https://placehold.co/200x120/f39c12/white?text=Sua+Chua"
                             class="card-img-top food-card-img" alt="Sữa chua">
                        <div class="card-body">
                            <h6 class="card-title text-truncate mb-2">Sữa chua nếp cẩm</h6>
                            <div class="mb-2">
                                <span class="text-primary fw-bold">15,000đ</span>
                            </div>
                            <div class="d-flex align-items-center mb-2">
                                <i class="bi bi-star-fill rating-star small"></i>
                                <span class="ms-1 small text-muted">4.4</span>
                            </div>
                            <a href="login.jsp" class="btn btn-primary btn-sm w-100">
                                <i class="bi bi-cart-plus"></i> Thêm vào giỏ
                            </a>
                        </div>
                    </div>

                    <!-- New Food Item 3 -->
                    <div class="card food-card border-0 shadow-sm">
                        <img src="https://placehold.co/200x120/16a085/white?text=Bun+Cha"
                             class="card-img-top food-card-img" alt="Bún chả">
                        <div class="card-body">
                            <h6 class="card-title text-truncate mb-2">Bún chả Hà Nội</h6>
                            <div class="mb-2">
                                <span class="text-primary fw-bold">35,000đ</span>
                            </div>
                            <div class="d-flex align-items-center mb-2">
                                <i class="bi bi-star-fill rating-star small"></i>
                                <span class="ms-1 small text-muted">4.5</span>
                            </div>
                            <a href="login.jsp" class="btn btn-primary btn-sm w-100">
                                <i class="bi bi-cart-plus"></i> Thêm vào giỏ
                            </a>
                        </div>
                    </div>

                    <!-- New Food Item 4 -->
                    <div class="card food-card border-0 shadow-sm">
                        <img src="https://placehold.co/200x120/8e44ad/white?text=Com+Tam"
                             class="card-img-top food-card-img" alt="Cơm tấm">
                        <div class="card-body">
                            <h6 class="card-title text-truncate mb-2">Cơm tấm sườn bì</h6>
                            <div class="mb-2">
                                <span class="text-primary fw-bold">32,000đ</span>
                            </div>
                            <div class="d-flex align-items-center mb-2">
                                <i class="bi bi-star-fill rating-star small"></i>
                                <span class="ms-1 small text-muted">4.6</span>
                            </div>
                            <a href="login.jsp" class="btn btn-primary btn-sm w-100">
                                <i class="bi bi-cart-plus"></i> Thêm vào giỏ
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <!-- All Foods A-Z -->
        <section class="fade-in">
            <h4 class="fw-bold text-dark mb-3">
                <i class="bi bi-list-ul"></i> Tất cả món ăn (A → Z)
            </h4>

            <div class="row g-3">
                <!-- Grid Food Items -->
                <div class="col-6 col-sm-4 col-md-3 col-lg-2">
                    <div class="card food-card border-0 shadow-sm h-100">
                        <img src="https://placehold.co/200x120/e67e22/white?text=Banh+Beo"
                             class="card-img-top food-card-img" alt="Bánh bèo">
                        <div class="card-body p-2">
                            <h6 class="card-title text-truncate small mb-1">Bánh bèo</h6>
                            <div class="mb-1">
                                <span class="text-primary fw-bold small">12,000đ</span>
                            </div>
                            <div class="d-flex align-items-center mb-2">
                                <i class="bi bi-star-fill rating-star" style="font-size: 10px;"></i>
                                <span class="ms-1" style="font-size: 11px;">4.0</span>
                            </div>
                            <a href="login.jsp" class="btn btn-primary btn-sm w-100" style="font-size: 11px;">
                                <i class="bi bi-plus-lg"></i> Thêm
                            </a>
                        </div>
                    </div>
                </div>

                <div class="col-6 col-sm-4 col-md-3 col-lg-2">
                    <div class="card food-card border-0 shadow-sm h-100">
                        <img src="https://placehold.co/200x120/9b59b6/white?text=Banh+Mi"
                             class="card-img-top food-card-img" alt="Bánh mì">
                        <div class="card-body p-2">
                            <h6 class="card-title text-truncate small mb-1">Bánh mì thịt nướng</h6>
                            <div class="mb-1">
                                <span class="text-primary fw-bold small">18,000đ</span>
                            </div>
                            <div class="d-flex align-items-center mb-2">
                                <i class="bi bi-star-fill rating-star" style="font-size: 10px;"></i>
                                <span class="ms-1" style="font-size: 11px;">4.3</span>
                            </div>
                            <a href="login.jsp" class="btn btn-primary btn-sm w-100" style="font-size: 11px;">
                                <i class="bi bi-plus-lg"></i> Thêm
                            </a>
                        </div>
                    </div>
                </div>

                <div class="col-6 col-sm-4 col-md-3 col-lg-2">
                    <div class="card food-card border-0 shadow-sm h-100">
                        <img src="https://placehold.co/200x120/16a085/white?text=Bun+Cha"
                             class="card-img-top food-card-img" alt="Bún chả">
                        <div class="card-body p-2">
                            <h6 class="card-title text-truncate small mb-1">Bún chả</h6>
                            <div class="mb-1">
                                <span class="text-primary fw-bold small">35,000đ</span>
                            </div>
                            <div class="d-flex align-items-center mb-2">
                                <i class="bi bi-star-fill rating-star" style="font-size: 10px;"></i>
                                <span class="ms-1" style="font-size: 11px;">4.5</span>
                            </div>
                            <a href="login.jsp" class="btn btn-primary btn-sm w-100" style="font-size: 11px;">
                                <i class="bi bi-plus-lg"></i> Thêm
                            </a>
                        </div>
                    </div>
                </div>

                <div class="col-6 col-sm-4 col-md-3 col-lg-2">
                    <div class="card food-card border-0 shadow-sm h-100">
                        <div class="position-relative">
                            <img src="https://placehold.co/200x120/e74c3c/white?text=Com+Chien"
                                 class="card-img-top food-card-img" alt="Cơm chiên">
                            <span class="discount-badge">-10%</span>
                        </div>
                        <div class="card-body p-2">
                            <h6 class="card-title text-truncate small mb-1">Cơm chiên dương châu</h6>
                            <div class="mb-1">
                                <span class="text-primary fw-bold small">22,500đ</span>
                            </div>
                            <div class="d-flex align-items-center mb-2">
                                <i class="bi bi-star-fill rating-star" style="font-size: 10px;"></i>
                                <span class="ms-1" style="font-size: 11px;">4.5</span>
                            </div>
                            <a href="login.jsp" class="btn btn-primary btn-sm w-100" style="font-size: 11px;">
                                <i class="bi bi-plus-lg"></i> Thêm
                            </a>
                        </div>
                    </div>
                </div>

                <div class="col-6 col-sm-4 col-md-3 col-lg-2">
                    <div class="card food-card border-0 shadow-sm h-100">
                        <img src="https://placehold.co/200x120/8e44ad/white?text=Com+Tam"
                             class="card-img-top food-card-img" alt="Cơm tấm">
                        <div class="card-body p-2">
                            <h6 class="card-title text-truncate small mb-1">Cơm tấm sườn bì</h6>
                            <div class="mb-1">
                                <span class="text-primary fw-bold small">32,000đ</span>
                            </div>
                            <div class="d-flex align-items-center mb-2">
                                <i class="bi bi-star-fill rating-star" style="font-size: 10px;"></i>
                                <span class="ms-1" style="font-size: 11px;">4.6</span>
                            </div>
                            <a href="login.jsp" class="btn btn-primary btn-sm w-100" style="font-size: 11px;">
                                <i class="bi bi-plus-lg"></i> Thêm
                            </a>
                        </div>
                    </div>
                </div>

                <div class="col-6 col-sm-4 col-md-3 col-lg-2">
                    <div class="card food-card border-0 shadow-sm h-100">
                        <img src="https://placehold.co/200x120/3498db/white?text=Pho+Bo"
                             class="card-img-top food-card-img" alt="Phở bò">
                        <div class="card-body p-2">
                            <h6 class="card-title text-truncate small mb-1">Phở bò</h6>
                            <div class="mb-1">
                                <span class="text-primary fw-bold small">30,000đ</span>
                            </div>
                            <div class="d-flex align-items-center mb-2">
                                <i class="bi bi-star-fill rating-star" style="font-size: 10px;"></i>
                                <span class="ms-1" style="font-size: 11px;">4.8</span>
                            </div>
                            <a href="login.jsp" class="btn btn-primary btn-sm w-100" style="font-size: 11px;">
                                <i class="bi bi-plus-lg"></i> Thêm
                            </a>
                        </div>
                    </div>
                </div>

                <div class="col-6 col-sm-4 col-md-3 col-lg-2">
                    <div class="card food-card border-0 shadow-sm h-100">
                        <div class="position-relative">
                            <img src="https://placehold.co/200x120/1abc9c/white?text=Salad"
                                 class="card-img-top food-card-img" alt="Salad">
                            <span class="discount-badge">-8%</span>
                        </div>
                        <div class="card-body p-2">
                            <h6 class="card-title text-truncate small mb-1">Salad rau củ</h6>
                            <div class="mb-1">
                                <span class="text-primary fw-bold small">18,400đ</span>
                            </div>
                            <div class="d-flex align-items-center mb-2">
                                <i class="bi bi-star-fill rating-star" style="font-size: 10px;"></i>
                                <span class="ms-1" style="font-size: 11px;">4.2</span>
                            </div>
                            <a href="login.jsp" class="btn btn-primary btn-sm w-100" style="font-size: 11px;">
                                <i class="bi bi-plus-lg"></i> Thêm
                            </a>
                        </div>
                    </div>
                </div>

                <div class="col-6 col-sm-4 col-md-3 col-lg-2">
                    <div class="card food-card border-0 shadow-sm h-100">
                        <img src="https://placehold.co/200x120/d35400/white?text=Xoi+Ga"
                             class="card-img-top food-card-img" alt="Xôi gà">
                        <div class="card-body p-2">
                            <h6 class="card-title text-truncate small mb-1">Xôi gà</h6>
                            <div class="mb-1">
                                <span class="text-primary fw-bold small">25,000đ</span>
                            </div>
                            <div class="d-flex align-items-center mb-2">
                                <i class="bi bi-star-fill rating-star" style="font-size: 10px;"></i>
                                <span class="ms-1" style="font-size: 11px;">4.1</span>
                            </div>
                            <a href="login.jsp" class="btn btn-primary btn-sm w-100" style="font-size: 11px;">
                                <i class="bi bi-plus-lg"></i> Thêm
                            </a>
                        </div>
                    </div>
                </div>

                <div class="col-6 col-sm-4 col-md-3 col-lg-2">
                    <div class="card food-card border-0 shadow-sm h-100">
                        <img src="https://placehold.co/200x120/c0392b/white?text=Mi+Xao"
                             class="card-img-top food-card-img" alt="Mì xào">
                        <div class="card-body p-2">
                            <h6 class="card-title text-truncate small mb-1">Mì xào hải sản</h6>
                            <div class="mb-1">
                                <span class="text-primary fw-bold small">28,000đ</span>
                            </div>
                            <div class="d-flex align-items-center mb-2">
                                <i class="bi bi-star-fill rating-star" style="font-size: 10px;"></i>
                                <span class="ms-1" style="font-size: 11px;">4.4</span>
                            </div>
                            <a href="login.jsp" class="btn btn-primary btn-sm w-100" style="font-size: 11px;">
                                <i class="bi bi-plus-lg"></i> Thêm
                            </a>
                        </div>
                    </div>
                </div>

                <div class="col-6 col-sm-4 col-md-3 col-lg-2">
                    <div class="card food-card border-0 shadow-sm h-100">
                        <img src="https://placehold.co/200x120/27ae60/white?text=Tra+Sua"
                             class="card-img-top food-card-img" alt="Trà sữa">
                        <div class="card-body p-2">
                            <h6 class="card-title text-truncate small mb-1">Trà sữa truyền thống</h6>
                            <div class="mb-1">
                                <span class="text-primary fw-bold small">20,000đ</span>
                            </div>
                            <div class="d-flex align-items-center mb-2">
                                <i class="bi bi-star-fill rating-star" style="font-size: 10px;"></i>
                                <span class="ms-1" style="font-size: 11px;">4.3</span>
                            </div>
                            <a href="login.jsp" class="btn btn-primary btn-sm w-100" style="font-size: 11px;">
                                <i class="bi bi-plus-lg"></i> Thêm
                            </a>
                        </div>
                    </div>
                </div>

                <div class="col-6 col-sm-4 col-md-3 col-lg-2">
                    <div class="card food-card border-0 shadow-sm h-100">
                        <img src="https://placehold.co/200x120/2980b9/white?text=Ca+Phe"
                             class="card-img-top food-card-img" alt="Cà phê">
                        <div class="card-body p-2">
                            <h6 class="card-title text-truncate small mb-1">Cà phê sữa đá</h6>
                            <div class="mb-1">
                                <span class="text-primary fw-bold small">15,000đ</span>
                            </div>
                            <div class="d-flex align-items-center mb-2">
                                <i class="bi bi-star-fill rating-star" style="font-size: 10px;"></i>
                                <span class="ms-1" style="font-size: 11px;">4.7</span>
                            </div>
                            <a href="login.jsp" class="btn btn-primary btn-sm w-100" style="font-size: 11px;">
                                <i class="bi bi-plus-lg"></i> Thêm
                            </a>
                        </div>
                    </div>
                </div>

                <div class="col-6 col-sm-4 col-md-3 col-lg-2">
                    <div class="card food-card border-0 shadow-sm h-100">
                        <img src="https://placehold.co/200x120/f39c12/white?text=Sua+Chua"
                             class="card-img-top food-card-img" alt="Sữa chua">
                        <div class="card-body p-2">
                            <h6 class="card-title text-truncate small mb-1">Sữa chua nếp cẩm</h6>
                            <div class="mb-1">
                                <span class="text-primary fw-bold small">15,000đ</span>
                            </div>
                            <div class="d-flex align-items-center mb-2">
                                <i class="bi bi-star-fill rating-star" style="font-size: 10px;"></i>
                                <span class="ms-1" style="font-size: 11px;">4.4</span>
                            </div>
                            <a href="login.jsp" class="btn btn-primary btn-sm w-100" style="font-size: 11px;">
                                <i class="bi bi-plus-lg"></i> Thêm
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</main>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>