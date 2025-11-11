package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.OrderDAO;
import model.Order_FoodDAO;
import repository.OrderRepository;
import repository.Order_FoodRepository;
import repositoryimpl.OrderRepositoryImpl;
import repositoryimpl.Order_FoodRepositoryImpl;
import utils.DataSourceUtil;
import utils.RequestUtil;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

/**
 * Servlet implementation class CartServerlet
 */
@WebServlet("/cart")
public class CartServerlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private OrderRepository orderRepository;
    private Order_FoodRepository orderFoodRepository;

    public CartServerlet() {
        super();
    }

    @Override
    public void init() throws ServletException {
        DataSource ds = DataSourceUtil.getDataSource();
        this.orderRepository = new OrderRepositoryImpl(ds);
        this.orderFoodRepository = new Order_FoodRepositoryImpl(ds);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String action = RequestUtil.getString(request, "action", "list");

        List<Order_FoodDAO> cart = (List<Order_FoodDAO>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        switch (action) {
            case "remove":
                int idToRemove = Integer.parseInt(request.getParameter("id"));
                cart.removeIf(item -> item.getFoodId() == idToRemove);
                session.setAttribute("cart", cart);
                response.sendRedirect(request.getContextPath() + "/cart.jsp");
                return;

            case "checkout":
                Object userIdObj = session.getAttribute("userId");
                if (userIdObj == null) {
                    response.sendRedirect(request.getContextPath() + "/login.jsp");
                    return;
                }

                if (cart.isEmpty()) {
                    response.sendRedirect(request.getContextPath() + "/cart.jsp?error=empty_cart");
                    return;
                }

                double total = cart.stream()
                        .mapToDouble(i -> i.getPriceAtOrder() * i.getQuantity())
                        .sum();

                int userId = (int) userIdObj;

                OrderDAO order = new OrderDAO();
                order.setUserId(userId);
                order.setTotalPrice(total);
                order.setStatus("Đang xử lý");

                OrderDAO createdOrder = this.orderRepository.save(order);

                for (Order_FoodDAO item : cart) {
                    item.setOrderId(createdOrder.getId());
                    this.orderFoodRepository.create(item);
                }

                session.removeAttribute("cart");
                request.setAttribute("order", createdOrder);
                request.getRequestDispatcher("/order-success.jsp").forward(request, response);
                return;

            default:
                // Mặc định hiển thị giỏ hàng
                request.getRequestDispatcher("/cart.jsp").forward(request, response);
                return;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        List<Order_FoodDAO> cart = (List<Order_FoodDAO>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        switch (action) {
            case "checkout":
                processCheckout(request, response, session, cart);
                return;

            case "add":
                int foodId = Integer.parseInt(request.getParameter("foodId"));
                String foodName = request.getParameter("foodName");
                double price = Double.parseDouble(request.getParameter("price"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));

                Optional<Order_FoodDAO> existingItem =
                        cart.stream().filter(i -> i.getFoodId() == foodId).findFirst();

                if (existingItem.isPresent()) {
                    existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
                } else {
                    Order_FoodDAO item = new Order_FoodDAO();
                    item.setFoodId(foodId);
                    item.setPriceAtOrder(price);
                    item.setQuantity(quantity);
                    item.setName(foodName);
                    cart.add(item);
                }

                session.setAttribute("cart", cart);
                response.sendRedirect(request.getContextPath() + "/cart.jsp");
                return;

            default:
                response.sendRedirect(request.getContextPath() + "/cart.jsp");
                return;
        }
    }

    private void processCheckout(HttpServletRequest request, HttpServletResponse response,
                                 HttpSession session, List<Order_FoodDAO> cart) throws IOException {
        try {
            if (cart == null || cart.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/cart.jsp?error=empty_cart");
                return;
            }

            Integer userId = (Integer) session.getAttribute("userId");
            if (userId == null) {
                session.setAttribute("redirectAfterLogin", request.getRequestURI());
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            String stallParam = request.getParameter("stallId");
            if (stallParam == null || stallParam.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/cart.jsp?error=invalid_stall");
                return;
            }
            int stallId = Integer.parseInt(stallParam);

            String address = request.getParameter("address");
            if (address == null || address.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/cart.jsp?error=empty_address");
                return;
            }

            double totalPrice = cart.stream()
                    .mapToDouble(item -> item.getPriceAtOrder() * item.getQuantity())
                    .sum();

            OrderDAO order = new OrderDAO();
            order.setUserId(userId);
            order.setStallId(stallId);
            order.setStatus("PENDING");
            order.setTotalPrice(totalPrice);
            order.setPaymentMethod("COD");
            order.setDeliveryLocation(address);

            order = this.orderRepository.save(order);
            if (order == null || order.getId() == 0) {
                response.sendRedirect(request.getContextPath() + "/cart.jsp?error=order_failed");
                return;
            }

            for (Order_FoodDAO item : cart) {
                item.setOrderId(order.getId());
                this.orderFoodRepository.create(item);
            }

            session.removeAttribute("cart");
            response.sendRedirect(request.getContextPath() + "/orders?success=checkout_complete");

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/cart.jsp?error=invalid_stallId");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/cart.jsp?error=server_error");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        List<Order_FoodDAO> cart = (List<Order_FoodDAO>) session.getAttribute("cart");
        if (cart == null) return;

        int foodId = Integer.parseInt(req.getParameter("foodId"));
        cart.removeIf(item -> item.getFoodId() == foodId);

        session.setAttribute("cart", cart);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

}
