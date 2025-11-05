package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.FoodDAO;
import model.Page;
import model.PageRequest;
import serviceimpl.FoodServiceImpl;
import utils.DataSourceUtil;
import utils.RequestUtil;

import java.io.IOException;

import javax.sql.DataSource;


@WebServlet("/foods")
public class FoodServerlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private FoodServiceImpl foodServiceImpl;
	private int PAGE_SIZE = 25;
	
	@Override
	public void init() throws ServletException {
		DataSource ds = DataSourceUtil.getDataSource();
		this.foodServiceImpl = new FoodServiceImpl(ds);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = RequestUtil.getString(request, "action", "list");
		String keyword = RequestUtil.getString(request, "keyword", "");
		String sortField = RequestUtil.getString(request, "sortField", "id");
		String orderField = RequestUtil.getString(request, "orderField", "DESC");
		int page = RequestUtil.getInt(request, "page", 1);
		int id = RequestUtil.getInt(request, "id", 1);
		FoodDAO foundFood = null;

		HttpSession session = request.getSession();
		request.setAttribute("username", session.getAttribute("username"));
//		request.setAttribute("role", session.getAttribute("role"));
		
		RequestDispatcher rd;
		switch (action) {
			case "list":
				
				PageRequest pageReq = new PageRequest(page, PAGE_SIZE, sortField, orderField, keyword);
				Page<FoodDAO> pageFood = this.foodServiceImpl.findAll(pageReq);
		        request.setAttribute("pageFood", pageFood);
		        request.setAttribute("pageReq", pageReq);
		        rd = request.getRequestDispatcher("/foodTemplates/food-list.jsp");
		        rd.forward(request, response);
		        break;
			
			case "create":
		        rd = request.getRequestDispatcher("/foodTemplates/food-form-create.jsp");
		        rd.forward(request, response);
		        break;

			case "detail":
				
		        foundFood = this.foodServiceImpl.findById(id);

		        if (foundFood != null) {
		        	request.setAttribute("food", foundFood);
		            rd = request.getRequestDispatcher("/foodTemplates/food-form-detail.jsp");
		            rd.forward(request, response);
		        } else {
		        	response.sendError(HttpServletResponse.SC_NOT_FOUND);
	            	return;
		        }
		        break;    
		        
			case "update":
				
		        foundFood = this.foodServiceImpl.findById(id);

		        if (foundFood != null) {
		        	request.setAttribute("food", foundFood);
		            rd = request.getRequestDispatcher("/foodTemplates/food-form-update.jsp");
		            rd.forward(request, response);
		        } else {
		        	response.sendError(HttpServletResponse.SC_NOT_FOUND);
	            	return;
		        }
		        break;
			
			case "delete":
				
		        boolean isDelete = this.foodServiceImpl.delete(id);
		        if(isDelete) {
		        	response.sendRedirect(request.getContextPath()+"/foods?action=list");
		        }else {
		        	response.sendError(HttpServletResponse.SC_NOT_FOUND);
	            	return;
		        }	
				break;
			default:
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
            	return;
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
        String nameFood = request.getParameter("nameFood");
        double priceFood = Double.parseDouble(request.getParameter("priceFood"));
        int inventoryFood = Integer.parseInt(request.getParameter("inventoryFood"));
		
		switch (action) {
			case "create":
	            
	            boolean isCreate = this.foodServiceImpl.create(nameFood, priceFood, inventoryFood);
	            if(isCreate) {
	            	response.sendRedirect(request.getContextPath()+"/foods?action=list");
	            } else {
	            	response.sendError(HttpServletResponse.SC_NOT_FOUND);
	            	return;
	            }
	            break;
			
			case "update":
				int id = Integer.parseInt(request.getParameter("id"));
		        boolean isUpdate = this.foodServiceImpl.update(id, nameFood, priceFood, inventoryFood);
		        if(isUpdate) {
		        	response.sendRedirect(request.getContextPath()+"/foods?action=list");
		        }else {
		        	response.sendError(HttpServletResponse.SC_NOT_FOUND);
	            	return;
		        }
				break;
		}
	}

}
