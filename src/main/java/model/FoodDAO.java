package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import utils.DataSourceUtil;

public class FoodDAO {

	private int id;
	private String nameFood;
	private Double priceFood;
	private int inventoryFood;
	
	public FoodDAO() {
		this.setId(-1);
		this.setNameFood("");
		this.setPriceFood(0.0);
		this.setInventoryFood(0);
	}
	
	public FoodDAO(int id, String name, double price, int inventory) {
		this.setId(id);
		this.setNameFood(name);
		this.setPriceFood(price);
		this.setInventoryFood(inventory);
	}

	public int getInventoryFood() {
		return inventoryFood;
	}

	public void setInventoryFood(int inventoryFood) {
		this.inventoryFood = inventoryFood;
	}

	public Double getPriceFood() {
		return priceFood;
	}

	public void setPriceFood(Double priceFood) {
		this.priceFood = priceFood;
	}

	public String getNameFood() {
		return nameFood;
	}

	public void setNameFood(String nameFood) {
		this.nameFood = nameFood;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public static List<FoodDAO> getAllFood() {
        List<FoodDAO> foods = new ArrayList<>();

        String sql = "SELECT id, name, price, inventory FROM Food ORDER BY id";
        DataSource ds = DataSourceUtil.getDataSource();
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
	
	public static void addFood(String name, double price, int inventory) {
        String sql = "INSERT INTO Food (name, price, inventory) VALUES (?,?,?)";
        DataSource ds = DataSourceUtil.getDataSource();
        try (
        	Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, inventory);
            ps.executeUpdate();
	    } catch (Exception e) {
	      throw new RuntimeException(e);
	    }		
	}
	
}
