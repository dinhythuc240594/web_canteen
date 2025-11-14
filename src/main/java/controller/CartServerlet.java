package controller;

import jakarta.json.JsonArray;
import jakarta.json.JsonBuilderFactory;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import jakarta.json.stream.JsonCollectors;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.xdevapi.JsonParser;

import dto.OrderFoodDTO;

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        String cart = (String) session.getAttribute("cart");
        session.setAttribute("cart", cart);
        request.getRequestDispatcher("cart.jsp").forward(request, response);
//        response.sendRedirect(request.getContextPath() + "/cart");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = RequestUtil.getString(request, "action", "");
        String orders_raw = RequestUtil.getString(request, "orders", "[]");
        System.out.println(orders_raw);
//        ObjectMapper mapper = new ObjectMapper();
//        List<OrderFoodDTO> orders = mapper.readValue(
//        		orders_raw, new TypeReference<List<OrderFoodDTO>>() {}
//        	);
        HttpSession session = request.getSession();

        List<Order_FoodDAO> cart = (List<Order_FoodDAO>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();

        switch (action) {
            case "add":
                try {

                	    
//                    session.setAttribute("cart", cart);
                    response.sendRedirect(request.getContextPath() + "/cart");
                } catch (NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/cart?error=invalid_data");
                }
                return;

            case "checkout":
                processCheckout(request, response, session, cart);
                return;

            default:
                response.sendRedirect(request.getContextPath() + "/cart");
                break;
        }
    }

    private void processCheckout(HttpServletRequest request, HttpServletResponse response,
                                 HttpSession session, List<Order_FoodDAO> cart) throws IOException {
        try {
        	
            if (cart == null || cart.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/cart?error=empty_cart");
                return;
            }

            Integer userId = (Integer) session.getAttribute("userId");
            if (userId == null) {
                session.setAttribute("redirectAfterLogin", request.getRequestURI());
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            String address = request.getParameter("address");
            String payment = request.getParameter("payment");
            String stallParam = request.getParameter("stallId");

            if (address == null || address.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/cart?error=empty_address");
                return;
            }

            int stallId = 0;
            try {
                stallId = Integer.parseInt(stallParam);
            } catch (NumberFormatException ignored) {
            }

            double total = cart.stream()
                    .mapToDouble(i -> i.getPriceAtOrder() * i.getQuantity())
                    .sum();

            OrderDAO order = new OrderDAO();
            order.setUserId(userId);
            order.setStallId(stallId);
            order.setTotalPrice(total);
            order.setStatus("Đang xử lý");
            order.setPaymentMethod(payment != null ? payment : "COD");
            order.setDeliveryLocation(address);

            OrderDAO createdOrder = orderRepository.save(order);

            for (Order_FoodDAO item : cart) {
                item.setOrderId(createdOrder.getId());
                orderFoodRepository.create(item);
            }

            session.removeAttribute("cart");
            request.setAttribute("order", createdOrder);
            request.getRequestDispatcher("/order-success.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/cart?error=invalid_stallId");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/cart?error=server_error");
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
