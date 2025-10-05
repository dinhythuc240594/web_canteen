package repository;

import java.util.List;

import model.FoodDAO;

public interface FoodRepository extends Repository<FoodDAO>{
	
	boolean create(String nameFood, double priceFood, int inventoryFood);
	boolean update(int id, String nameFood, double priceFood, int inventoryFood);
	List<FoodDAO> findAll();
	
}
