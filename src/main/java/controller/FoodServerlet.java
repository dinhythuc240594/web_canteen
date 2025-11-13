package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.FoodDAO;
import model.Food_CategoryDAO;
import model.ImageDAO;
import model.Page;
import model.PageRequest;
import repositoryimpl.Food_CategoryRepositoryImpl;
import repositoryimpl.ImageRepositoryImpl;
import serviceimpl.FoodServiceImpl;
import serviceimpl.Food_CategoryServiceImpl;
import utils.DataSourceUtil;
import utils.ImageBase64Util;
import utils.RequestUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import dto.FoodDTO;


@WebServlet("/foods")
@MultipartConfig(
	fileSizeThreshold = 2 * 1024 * 1024,   // 2MB
	maxFileSize = 5 * 1024 * 1024,          // 5MB
	maxRequestSize = 10 * 1024 * 1024       // 10MB
)
public class FoodServerlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private FoodServiceImpl foodServiceImpl;
	private Food_CategoryServiceImpl categoryServiceImpl;
	private ImageRepositoryImpl imageRepository;
	private int PAGE_SIZE = 25;
	
	@Override
	public void init() throws ServletException {
		DataSource ds = DataSourceUtil.getDataSource();
		this.foodServiceImpl = new FoodServiceImpl(ds);
		this.categoryServiceImpl = new Food_CategoryServiceImpl(ds);
		this.imageRepository = new ImageRepositoryImpl(ds);
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
		FoodDTO foundFood = null;
		
		RequestDispatcher rd;
		switch (action) {
			case "list":
				
				PageRequest pageReq = new PageRequest(page, PAGE_SIZE, sortField, orderField, keyword);
				Page<FoodDTO> pageFood = this.foodServiceImpl.findAll(pageReq);
		        request.setAttribute("pageFood", pageFood);
		        request.setAttribute("pageReq", pageReq);
		        rd = request.getRequestDispatcher("/foodTemplates/food-list.jsp");
		        rd.forward(request, response);
		        break;
			
			case "create":
				PageRequest pageReq1 = new PageRequest(page, PAGE_SIZE, sortField, orderField, keyword);
				List<Food_CategoryDAO> categories = this.categoryServiceImpl.findAll();
				request.setAttribute("categories", categories);
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
		
		switch (action) {
			case "create":
				handleCreateWithImage(request, response);
	            break;
			
			case "update":
				handleUpdateWithImage(request, response);
				break;
				
			default:
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
		}
	}
	
	/**
	 * Xử lý tạo món mới với upload ảnh Base64
	 */
	private void handleCreateWithImage(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		try {
			// Lấy thông tin món ăn từ form
			String nameFood = request.getParameter("nameFood");
			double priceFood = Double.parseDouble(request.getParameter("priceFood"));
			int inventoryFood = Integer.parseInt(request.getParameter("inventoryFood"));
			
			// Xử lý upload ảnh
			Part imagePart = request.getPart("imageFile");
			Integer imageId = null;
			
			if (imagePart != null && imagePart.getSize() > 0) {
				System.out.println("📸 Processing image upload...");
				
				// Convert file → Base64 + metadata
				Map<String, Object> imageData = ImageBase64Util.convertToBase64(imagePart);
				
				// Tạo ImageDAO object
				ImageDAO image = new ImageDAO();
				image.setFilename((String) imageData.get("filename"));
				image.setMimeType((String) imageData.get("mimeType"));
				image.setFileSize((Integer) imageData.get("fileSize"));
				image.setImageData((String) imageData.get("imageData"));
				image.setThumbnailData((String) imageData.get("thumbnailData"));
				image.setWidth((Integer) imageData.get("width"));
				image.setHeight((Integer) imageData.get("height"));
				image.setEntityType("food");
				
				// Lưu vào bảng images, lấy ID
				imageId = imageRepository.insert(image);
				
				if (imageId <= 0) {
					throw new RuntimeException("Không thể lưu ảnh vào database");
				}
				
				System.out.println("✅ Image saved with ID: " + imageId);
			}
			
			// Tạo món ăn (giữ nguyên logic cũ)
			boolean isCreate = this.foodServiceImpl.create(nameFood, priceFood, inventoryFood);
			
			if (isCreate) {
				// TODO: Cập nhật image_id cho món vừa tạo
				// Tạm thời redirect về list
				response.sendRedirect(request.getContextPath() + "/foods?action=list&msg=create_success");
			} else {
				request.setAttribute("error", "Không thể tạo món");
				request.getRequestDispatcher("/foodTemplates/food-form-create.jsp")
					   .forward(request, response);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Lỗi: " + e.getMessage());
			request.getRequestDispatcher("/foodTemplates/food-form-create.jsp")
				   .forward(request, response);
		}
	}
	
	/**
	 * Xử lý update món với ảnh mới (nếu có)
	 */
	private void handleUpdateWithImage(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			String nameFood = request.getParameter("nameFood");
			double priceFood = Double.parseDouble(request.getParameter("priceFood"));
			int inventoryFood = Integer.parseInt(request.getParameter("inventoryFood"));
			
			// Xử lý upload ảnh mới (nếu có)
			Part imagePart = request.getPart("imageFile");
			Integer newImageId = null;
			
			if (imagePart != null && imagePart.getSize() > 0) {
				System.out.println("📸 Processing new image upload for food ID: " + id);
				
				// Convert và lưu ảnh mới
				Map<String, Object> imageData = ImageBase64Util.convertToBase64(imagePart);
				
				ImageDAO image = new ImageDAO();
				image.setFilename((String) imageData.get("filename"));
				image.setMimeType((String) imageData.get("mimeType"));
				image.setFileSize((Integer) imageData.get("fileSize"));
				image.setImageData((String) imageData.get("imageData"));
				image.setThumbnailData((String) imageData.get("thumbnailData"));
				image.setWidth((Integer) imageData.get("width"));
				image.setHeight((Integer) imageData.get("height"));
				image.setEntityType("food");
				
				newImageId = imageRepository.insert(image);
				
				if (newImageId > 0) {
					System.out.println("✅ New image saved with ID: " + newImageId);
					// TODO: Xóa ảnh cũ nếu cần
					// TODO: Cập nhật image_id trong foods
				}
			}
			
			// Update món ăn (giữ nguyên logic cũ)
			boolean isUpdate = this.foodServiceImpl.update(id, nameFood, priceFood, inventoryFood);
			
			if (isUpdate) {
				response.sendRedirect(request.getContextPath() + "/foods?action=list&msg=update_success");
			} else {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Lỗi: " + e.getMessage());
			request.getRequestDispatcher("/foodTemplates/food-form-update.jsp")
				   .forward(request, response);
		}
	}

}
