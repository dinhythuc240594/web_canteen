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
}
