package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.FoodDAO;
import serviceimpl.FoodServiceImpl;
import utils.DataSourceUtil;

import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

/**
 * Servlet implementation class FoodServerlet
 */
@WebServlet("/foods")
public class FoodServerlet extends HttpServlet {

	private FoodServiceImpl foodServiceImpl;
	
	@Override
	public void init() throws ServletException {
		DataSource ds = DataSourceUtil.getDataSource();
		this.foodServiceImpl = new FoodServiceImpl(ds);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		System.out.println("redirect page: " + action);
		if (action == null) {
			action = "list";
		}
		
		int idParam = 0;
		String id = request.getParameter("id");
		FoodDAO foundFood = null;

		HttpSession session = request.getSession();
		request.setAttribute("username", session.getAttribute("username"));
//		request.setAttribute("role", session.getAttribute("role"));
		
		RequestDispatcher rd;
		switch (action) {
			case "list":
				List<FoodDAO> lstFood = this.foodServiceImpl.findAll();
		        request.setAttribute("foods", lstFood);
		        rd = request.getRequestDispatcher("/foodTemplates/food-list.jsp");
		        rd.forward(request, response);
		        break;
			
			case "create":
		        rd = request.getRequestDispatcher("/foodTemplates/food-form-create.jsp");
		        rd.forward(request, response);
		        break;

			case "detail":

				if (id == null) {
		        	response.sendError(HttpServletResponse.SC_NOT_FOUND);
		        	return;
		        }
		        
				idParam = Integer.parseInt(id);
				
		        foundFood = this.foodServiceImpl.findById(idParam);

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

				if (id == null) {
		        	response.sendError(HttpServletResponse.SC_NOT_FOUND);
		        	return;
		        }
		        
				idParam = Integer.parseInt(id);
				
		        foundFood = this.foodServiceImpl.findById(idParam);

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
		        
				if (id == null) {
		        	response.sendError(HttpServletResponse.SC_NOT_FOUND);
		        	return;
		        }
		        
				idParam = Integer.parseInt(id);
				
		        boolean isDelete = this.foodServiceImpl.delete(idParam);
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
