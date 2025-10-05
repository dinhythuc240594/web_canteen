package repositoryimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.FoodDAO;
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
        String sql = "SELECT id, name, price, inventory FROM food where id = ?";
        try (
        	Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);) {

            ps.setInt(1, idFood);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                String nameFood = rs.getString("name");
                double priceFood = rs.getDouble("price");
                int inventoryFood  = rs.getInt("inventory");

                foundFood = new FoodDAO(idFood, nameFood, priceFood, inventoryFood);
                break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return foundFood;
	}

	@Override
	public boolean create(String nameFood, double priceFood, int inventoryFood) {
        String sql = "INSERT INTO food (name, price, inventory) VALUES (?,?,?)";
        try (
        	Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, nameFood);
            ps.setDouble(2, priceFood);
            ps.setInt(3, inventoryFood);
            ps.executeUpdate();
            return true;
	    } catch (Exception e) {
	      throw new RuntimeException(e);
	    }
	}

	@Override
	public boolean update(int id, String nameFood, double priceFood, int inventoryFood) {
        String sql = "UPDATE food SET name = ?, price = ?, inventory = ? WHERE id = ?";
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
	          throw new RuntimeException(e);
	  }
	}

	@Override
	public boolean delete(int id) {
      String sql = "DELETE FROM food WHERE id = ?";
      try (Connection conn = ds.getConnection();
           PreparedStatement ps = conn.prepareStatement(sql)) {
      	 ps.setInt(1, id); 
           ps.executeUpdate();
           return true;
		} catch (Exception e) {
          throw new RuntimeException(e);
      }
	}	
	
	@Override
	public List<FoodDAO> findAll() {
        List<FoodDAO> foods = new ArrayList<>();

        String sql = "SELECT id, name, price, inventory FROM food ORDER BY id";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int inventory = rs.getInt("inventory");

                foods.add(new FoodDAO(id, name, price, inventory));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return foods;
	}
}
