package repositoryimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import dto.FoodDTO;
import model.FoodDAO;
import model.PageRequest;
import repository.FoodRepository;

public class FoodRepositoryImpl implements FoodRepository{
	private static final String PLACEHOLDER_IMAGE = "image/food-thumbnail.png";

	private final DataSource ds;
	
	public FoodRepositoryImpl(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public FoodDTO findById(int id) {
        FoodDTO foundFood = null;
        String sql = """
        		SELECT f.id, f.name, f.price, f.inventory, f.description,
        		       f.category_id, f.promotion, f.image_id
        		FROM foods f
        		WHERE f.id = ?
        		""";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String nameFood = rs.getString("name");
                double priceFood = rs.getDouble("price");
                int inventoryFood  = rs.getInt("inventory");
                String descriptionFood = rs.getString("description");
                double promotion = rs.getDouble("promotion");
                int categoryId = rs.getInt("category_id");
                int imageId = rs.getInt("image_id");

                FoodDAO entity = new FoodDAO(id, nameFood, priceFood, inventoryFood);
                entity.setDescription(descriptionFood);
                entity.setCategory_id(categoryId);
                entity.setImage(resolveImagePath(imageId));

                foundFood = FoodDTO.toDto(entity, promotion);
            }

        } catch (Exception e) {
        	System.err.println("Lỗi findById: " + e.getMessage());
        }
        return foundFood;
	}

	@Override
	public boolean create(String nameFood, double priceFood, int inventoryFood) {
        String sql = "INSERT INTO foods (name, price, inventory) VALUES (?,?,?)";
        try (
        	Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, nameFood);
            ps.setDouble(2, priceFood);
            ps.setInt(3, inventoryFood);
            ps.executeUpdate();
            return true;
	    } catch (Exception e) {
	    	System.err.println("Lỗi create: " + e.getMessage());
	    	return false;
	    }
	}

	@Override
	public boolean update(int id, String nameFood, double priceFood, int inventoryFood) {
        String sql = "UPDATE foods SET name = ?, price = ?, inventory = ? WHERE id = ?";
      try (
      	Connection conn = ds.getConnection();
          PreparedStatement ps = conn.prepareStatement(sql);) {

          ps.setString(1, nameFood);
          ps.setDouble(2, priceFood);
          ps.setInt(3, inventoryFood);
          ps.setInt(4, id); 
          ps.executeUpdate();
          return true;
	  } catch (Exception e) {
		  System.err.println("Lỗi update: " + e.getMessage());
		  return false;
	  }
	}

	@Override
	public boolean delete(int id) {
      String sql = "DELETE FROM foods WHERE id = ?";
      try (Connection conn = ds.getConnection();
           PreparedStatement ps = conn.prepareStatement(sql)) {
      	 	ps.setInt(1, id); 
      	 	ps.executeUpdate();
      	 	return true;
		} catch (Exception e) {
			System.err.println("Lỗi delete: " + e.getMessage());
			return false;
      }
	}

	@Override
	public List<FoodDTO> findAll(PageRequest pageRequest) {
        List<FoodDTO> foods = new ArrayList<>();

        int pageSize = pageRequest.getPageSize();
        int offset = pageRequest.getOffset();
        String keyword = pageRequest.getKeyword();
        String sortField = mapSortField(pageRequest.getSortField());
        String orderField = "ASC".equalsIgnoreCase(pageRequest.getOrderField()) ? "ASC" : "DESC";
        boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
        
        StringBuilder sql = new StringBuilder("""
                SELECT f.id, f.name, f.price, f.inventory,
                       f.description, f.category_id, f.promotion,
                       f.image_id
                FROM foods f
                """);
        if(hasKeyword) {
        	sql.append("WHERE f.name LIKE ? ");
        }
        sql.append("ORDER BY ").append(sortField).append(' ').append(orderField)
           .append(" LIMIT ? OFFSET ?");
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

        	int paramIndex = 1;
        	if(hasKeyword) {
        		ps.setString(paramIndex++, "%" + keyword + "%");
        	}
        	ps.setInt(paramIndex++, pageSize);
        	ps.setInt(paramIndex, offset);
        	
        	ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int inventory = rs.getInt("inventory");
                double promotion = rs.getDouble("promotion");
                String description = rs.getString("description");
                int categoryId = rs.getInt("category_id");
                int imageId = rs.getInt("image_id");

                FoodDAO entity = new FoodDAO(id, name, price, inventory);
                entity.setDescription(description);
                entity.setCategory_id(categoryId);
                entity.setImage(resolveImagePath(imageId));

                foods.add(FoodDTO.toDto(entity, promotion));
            }
        } catch (Exception e) {
        	System.err.println("Lỗi findAll: " + e.getMessage());
        }
        return foods;
	}

	@Override
	public int count(String keyword) {
        String sql = "SELECT COUNT(1) FROM foods";
        int total = 0;
        
        boolean hasKeywords = keyword != null && !keyword.trim().isEmpty();
        
        if (hasKeywords) {
            sql += " WHERE title LIKE ? OR author LIKE ?";
        }
        
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (hasKeywords) {
                String searchPattern = "%" + keyword + "%";
                ps.setString(1, searchPattern);
                ps.setString(2, searchPattern);
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (Exception e) {
        	System.err.println("Lỗi count: " + e.getMessage());
            return -1;
        }
        
        return total;
	}

	@Override
	public List<FoodDTO> newFoods() {
        List<FoodDTO> foods = new ArrayList<>();
        
        String sql = """
        		SELECT f.id, f.name, f.price, f.inventory,
        		       f.description, f.category_id, f.promotion,
        		       f.image_id
        		FROM foods f
        		ORDER BY f.updated_at DESC
        		LIMIT 10
        		""";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

        	ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int inventory = rs.getInt("inventory");
                double promotion = rs.getDouble("promotion");
                String description = rs.getString("description");
                int categoryId = rs.getInt("category_id");
                int imageId = rs.getInt("image_id");
                
                FoodDAO entity = new FoodDAO(id, name, price, inventory);
                entity.setDescription(description);
                entity.setCategory_id(categoryId);
                entity.setImage(resolveImagePath(imageId));

                foods.add(FoodDTO.toDto(entity, promotion));
            }
        } catch (Exception e) {
        	System.err.println("Lỗi newFoods: " + e.getMessage());
        }
        return foods;
	}

	@Override
	public List<FoodDTO> promotionFoods() {
        List<FoodDTO> foods = new ArrayList<>();
        
        String sql = """
        		SELECT f.id, f.name, f.price, f.inventory,
        		       f.description, f.category_id, f.promotion,
        		       f.image_id
        		FROM foods f
        		WHERE f.promotion > 0
        		ORDER BY f.promotion DESC
   
        		""";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

        	ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int inventory = rs.getInt("inventory");
                double promotion = rs.getDouble("promotion");

                String description = rs.getString("description");
                int categoryId = rs.getInt("category_id");
                int imageId = rs.getInt("image_id");

                FoodDAO entity = new FoodDAO(id, name, price, inventory);
                entity.setDescription(description);
                entity.setCategory_id(categoryId);
                entity.setImage(resolveImagePath(imageId));

                foods.add(FoodDTO.toDto(entity, promotion));
            }
        } catch (Exception e) {
        	System.err.println("Lỗi promotionFoods: " + e.getMessage());
        }
        return foods;
	}

	private String resolveImagePath(int imageId) {
		return imageId > 0 ? ("image/" + imageId) : PLACEHOLDER_IMAGE;
	}

	private String mapSortField(String sortField) {
		if (sortField == null) {
			return "f.id";
		}
		return switch (sortField) {
			case "name" -> "f.name";
			case "price" -> "f.price";
			case "inventory" -> "f.inventory";
			case "promotion" -> "f.promotion";
			default -> "f.id";
		};
	}
}
