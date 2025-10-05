package service;

import java.util.List;

import model.FoodDAO;

public interface FoodService {

	List<FoodDAO> findAll();
	FoodDAO findById(int id);
	boolean create(String nameFood, double priceFood, int inventoryFood);
	boolean update(int id, String nameFood, double priceFood, int inventoryFood);
	boolean delete(int id);

}
