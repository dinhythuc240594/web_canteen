package repositoryimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.FoodDAO;
import model.PageRequest;
import repository.FoodRepository;

public class FoodRepositoryImpl implements FoodRepository{
	private final DataSource ds;
	
	public FoodRepositoryImpl(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public FoodDAO findById(int id) {
        int idFood = id;
        FoodDAO foundFood = null;
        String sql = "SELECT id, name, price, inventory, image, description, category_id FROM foods where id = ?";
        try (
        	Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);) {

            ps.setInt(1, idFood);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                String nameFood = rs.getString("name");
                double priceFood = rs.getDouble("price");
                int inventoryFood  = rs.getInt("inventory");
                String imageFood = rs.getString("image");
                String descriptionFood = rs.getString("description");

                foundFood = new FoodDAO(idFood, nameFood, priceFood, inventoryFood);
                break;
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
	public List<FoodDAO> findAll(PageRequest pageRequest) {
        List<FoodDAO> foods = new ArrayList<>();

        int pageSize = pageRequest.getPageSize();
        int offset = pageRequest.getOffset();
        String keyword = pageRequest.getKeyword();
        String sortField = pageRequest.getSortField();
        String orderField = pageRequest.getOrderField();
        
        String sql = "SELECT id, name, price, inventory FROM foods ";
        if(keyword != "") {
        	sql += "WHERE name LIKE ? ";
        }
        sql += "ORDER BY %s %s LIMIT ? OFFSET ?";
        sql = String.format(sql, sortField, orderField);
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);) {

        	if(keyword != "") {
        		String search = "%" + keyword + "%";
        		ps.setString(1, search);
        		ps.setInt(2, pageSize);
        		ps.setInt(3, offset);
        	} else {
        		ps.setInt(1, pageSize);
        		ps.setInt(2, offset);
        	}
        	
        	ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int inventory = rs.getInt("inventory");

                foods.add(new FoodDAO(id, name, price, inventory));
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
	public List<FoodDAO> newFoods() {
        List<FoodDAO> foods = new ArrayList<>();
        
        String sql = "SELECT id, name, price, inventory, promotion FROM foods ";
        sql += "ORDER BY %s %s LIMIT 8";
        sql = String.format(sql, "promotion", "ASC");
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);) {

        	ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int inventory = rs.getInt("inventory");
                

                foods.add(new FoodDAO(id, name, price, inventory));
            }
        } catch (Exception e) {
        	System.err.println("Lỗi newFoods: " + e.getMessage());
        }
        return foods;
	}

	@Override
	public List<FoodDAO> promotionFoods() {
        List<FoodDAO> foods = new ArrayList<>();
        
        String sql = "SELECT id, name, price, inventory FROM foods ";
        sql += "ORDER BY %s %s LIMIT 8";
        sql = String.format(sql, "updated_at", "ASC");
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);) {

        	ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int inventory = rs.getInt("inventory");

                foods.add(new FoodDAO(id, name, price, inventory));
            }
        } catch (Exception e) {
        	System.err.println("Lỗi promotionFoods: " + e.getMessage());
        }
        return foods;
	}
}
