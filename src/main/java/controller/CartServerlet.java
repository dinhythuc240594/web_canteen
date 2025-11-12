package controller;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
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
import java.io.StringReader;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.apache.tomcat.util.json.JSONParser;

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
        String action = RequestUtil.getString(request, "action", "");

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
                    response.sendRedirect(request.getContextPath() + "/login");
                    return;
                }

                if (cart.isEmpty()) {
                    response.sendRedirect(request.getContextPath() + "/cart?error=empty_cart");
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
            	request.getRequestDispatcher("/cart.jsp").forward(request, response);
                return;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = RequestUtil.getString(request, "action", "");
        String orders_raw = RequestUtil.getString(request, "orders", "[]");
        String orders = orders_raw.replace('\'', '"');
        HttpSession session = request.getSession();

        List<Order_FoodDAO> cart = (List<Order_FoodDAO>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();

        switch (action) {
            case "add":
                try {
                		JsonReader reader = Json.createReader(new StringReader(orders));
                		JsonArray jsonArray = reader.readArray();
                		reader.close();
                		
                		for (JsonValue value : jsonArray) {
                			JsonObject obj = value.asJsonObject();
                			int id = obj.getInt("id");
                			
//                			Order_FoodDAO newItem = new Order_FoodDAO();
//                            newItem.setFoodId();
//                            newItem.setName(foodName);
//                            newItem.setPriceAtOrder(price);
//                            newItem.setQuantity(quantity);
//                            cart.add(newItem);
                		}
                	    
                        

                    session.setAttribute("cart", cart);
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
