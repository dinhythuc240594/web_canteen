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
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

/**
 * Servlet implementation class CartServerlet
 */
@WebServlet("/cart")
public class CartServerlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private OrderRepository orderRepo;
	private Order_FoodRepository orderFoodRepo;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartServerlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void init() throws ServletException {
    	DataSource ds = DataSourceUtil.getDataSource();

        this.orderRepo = new OrderRepositoryImpl(ds);
        this.orderFoodRepo = new Order_FoodRepositoryImpl(ds);
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Order_FoodDAO> cart = (List<Order_FoodDAO>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        request.setAttribute("cartItems", cart);
        request.getRequestDispatcher("cart.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        List<Order_FoodDAO> cart = (List<Order_FoodDAO>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        if ("add".equalsIgnoreCase(action)) {
            int foodId = RequestUtil.getInt(request, "foodId", 0);
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            double price = Double.parseDouble(request.getParameter("price"));

            boolean found = false;
            for (Order_FoodDAO item : cart) {
                if (item.getFoodId() == foodId) {
                    item.setQuantity(item.getQuantity() + quantity);
                    found = true;
                    break;
                }
            }

            if (!found) {
                Order_FoodDAO newItem = new Order_FoodDAO();
                newItem.setFoodId(foodId);
                newItem.setQuantity(quantity);
                newItem.setPriceAtOrder(price);
                cart.add(newItem);
            }

            session.setAttribute("cart", cart);
            response.sendRedirect(request.getContextPath() + "/cart");

        } else if ("checkout".equalsIgnoreCase(action)) {
            processCheckout(request, response, session, cart);
        }
	}
	
    private void processCheckout(HttpServletRequest req, HttpServletResponse resp,
            HttpSession session, List<Order_FoodDAO> cart) throws IOException {
			
			if (cart == null || cart.isEmpty()) {
				resp.sendRedirect(req.getContextPath() + "/cart?error=empty");
				return;
			}

			Integer userId = (Integer) session.getAttribute("userId");
			if (userId == null) {
				resp.sendRedirect(req.getContextPath() + "/login");
				return;
			}
			
			int stallId = Integer.parseInt(req.getParameter("stallId"));
			
			double totalPrice = cart.stream()
			.mapToDouble(item -> item.getPriceAtOrder() * item.getQuantity())
			.sum();
			
			OrderDAO order = new OrderDAO();
			order.setUserId(userId);
			order.setStallId(stallId);
			order.setStatus("PENDING");
			order.setTotalPrice(totalPrice);
			order.setPaymentMethod("COD"); // mặc định, có thể đổi
			order.setDeliveryLocation(req.getParameter("address"));
			
			order = orderRepo.save(order);
			
			for (Order_FoodDAO item : cart) {
				item.setOrderId(order.getId());
				orderFoodRepo.create(item);
			}
			
			session.removeAttribute("cart");
			
			resp.sendRedirect(req.getContextPath() + "/orders?success=true");
	}
    
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        HttpSession session = req.getSession();
        List<Order_FoodDAO> cart = (List<Order_FoodDAO>) session.getAttribute("cart");
        if (cart == null) return;

        int foodId = Integer.parseInt(req.getParameter("foodId"));
        cart.removeIf(item -> item.getFoodId() == foodId);

        session.setAttribute("cart", cart);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

}
