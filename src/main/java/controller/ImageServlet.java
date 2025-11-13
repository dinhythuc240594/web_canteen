package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ImageDAO;
import repositoryimpl.ImageRepositoryImpl;
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
    
    private static final long serialVersionUID = 1L;
    private ImageRepositoryImpl imageRepository;
    
    @Override
    public void init() throws ServletException {
        DataSource ds = DataSourceUtil.getDataSource();
        this.imageRepository = new ImageRepositoryImpl(ds);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Lấy path info: /image/123 → pathInfo = "/123"
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.length() <= 1) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing image ID");
            return;
        }
        
        try {
            // Parse image ID
            String idStr = pathInfo.substring(1); // Bỏ dấu "/" đầu
            int imageId = Integer.parseInt(idStr);
            
            // Query parameter để chọn thumbnail hay full
            String type = request.getParameter("type"); // "thumb" hoặc null (full)
            
            // Lấy ảnh từ database
            ImageDAO image = imageRepository.findById(imageId);
            
            if (image == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found");
                return;
            }
            
            // Chọn Base64 data (thumbnail hoặc full)
            String base64Data;
            if ("thumb".equals(type) && image.getThumbnailData() != null) {
                base64Data = image.getThumbnailData();
            } else {
                base64Data = image.getImageData();
            }
            
            if (base64Data == null || base64Data.isEmpty()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Image data is empty");
                return;
            }
            
            // Decode Base64 → byte[]
            byte[] imageBytes = Base64.getDecoder().decode(base64Data);
            
            // Set headers
            response.setContentType(image.getMimeType());
            response.setContentLength(imageBytes.length);
            
            // Cache 1 năm (vì ảnh không thay đổi, ID là unique)
            response.setHeader("Cache-Control", "public, max-age=31536000, immutable");
            response.setHeader("ETag", "\"" + imageId + "\"");
            
            // Gửi ảnh về client
            response.getOutputStream().write(imageBytes);
            
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

