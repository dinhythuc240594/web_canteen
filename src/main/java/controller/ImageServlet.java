package controller;

import dto.ImageDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ImageDAO;
import repositoryimpl.ImageRepositoryImpl;
import service.ImageService;
import serviceimpl.ImageServiceIplm;
import utils.DataSourceUtil;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Base64;

/**
 * Servlet để serve ảnh từ database
 * URL pattern: /image/{imageId}
 * VD: /image/123 → trả về ảnh có ID 123
 */
@WebServlet("/image/*")
public class ImageServlet extends HttpServlet {
    
//    private static final long serialVersionUID = 1L;
    private ImageService imageService;
    
    @Override
    public void init() throws ServletException {
        DataSource ds = DataSourceUtil.getDataSource();
        this.imageService = new ImageServiceIplm(ds);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Lấy path info: /image/123 → pathInfo = "/123"
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.length() <= 1) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing image ID - Thiếu ID ảnh");
            return;
        }
        
        try {
            // Parse image ID
            String idStr = pathInfo.substring(1); // Bỏ dấu "/" đầu
            int imageId = Integer.parseInt(idStr);

            // Query parameter để chọn thumbnail hay full
            String type = request.getParameter("type"); // "thumb" hoặc null (full)

            //gọi service để xử lý và lấy data hình ảnh
            ImageDTO imageDTO = imageService.GetImageByID(imageId, type);

            if (imageDTO == null || imageDTO.getImageBytes().length == 0) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Missing image ID");
                return;
            }

            // Set headers
            response.setContentType(imageDTO.getMimeType());
            response.setContentLength(imageDTO.getImageBytes().length);
            
            // Cache 1 năm (vì ảnh không thay đổi, ID là unique)
            response.setHeader("Cache-Control", "public, max-age=31536000, immutable");
            response.setHeader("ETag", "\"" + imageId + "\"");
            
            // Gửi ảnh về client - trình duyệt
            response.getOutputStream().write(imageDTO.getImageBytes());
            
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid image ID");
        } catch (IllegalArgumentException e) {
            // Base64 decode error
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Invalid image data");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error serving image");
        }
    }
}

