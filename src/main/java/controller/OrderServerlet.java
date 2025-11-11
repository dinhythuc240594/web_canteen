package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.OrderDAO;
import model.Order_FoodDAO;
import model.PageRequest;
import repository.OrderRepository;
import repository.Order_FoodRepository;
import repositoryimpl.OrderRepositoryImpl;
import repositoryimpl.Order_FoodRepositoryImpl;
import utils.DataSourceUtil;
import utils.RequestUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

/**
 * Servlet implementation class OrderServerlet
 */
@WebServlet("/order")
public class OrderServerlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderServerlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    private OrderRepository orderRepository;
    private Order_FoodRepository orderFoodRepository;
    private int PAGE_SIZE = 25;

    @Override
    public void init() throws ServletException {
    	DataSource ds = DataSourceUtil.getDataSource();
        orderRepository = new OrderRepositoryImpl(ds);
        orderFoodRepository = new Order_FoodRepositoryImpl(ds);
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 HttpSession session = request.getSession(false);
	        if (session == null || session.getAttribute("userId") == null) {
	        	response.sendRedirect(request.getContextPath() + "/login");
	            return;
	        }

	        int userId = (int) session.getAttribute("userId");

//			String keyword = RequestUtil.getString(request, "keyword", "");
//			String sortField = RequestUtil.getString(request, "sortField", "name");
//			String orderField = RequestUtil.getString(request, "orderField", "ASC");
//			int page = RequestUtil.getInt(request, "page", 1);
//	        
//	        PageRequest pageReq = new PageRequest(page, PAGE_SIZE, sortField, orderField, keyword);
//	        List<OrderDAO> orders = this.orderRepository.findAll(pageReq);

	        List<OrderDAO> orders = this.orderRepository.findByUserId(userId);	        
	        Map<Integer, List<Order_FoodDAO>> orderFoodMap = new HashMap<>();
	        for (OrderDAO order : orders) {
	            List<Order_FoodDAO> items = orderFoodRepository.findByOrderId(order.getId());
	            orderFoodMap.put(order.getId(), items);
	        }
	        
	        request.setAttribute("orders", orders);
	        request.setAttribute("orderFoodMap", orderFoodMap);

	        request.getRequestDispatcher("order.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
