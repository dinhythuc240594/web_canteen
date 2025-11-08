package repository;

import java.util.List;

import model.FoodDAO;
import model.PageRequest;

public interface FoodRepository extends Repository<FoodDAO>{
	
	List<FoodDAO> findAll(PageRequest pageRequest);
	List<FoodDAO> newFoods();
	List<FoodDAO> promotionFoods();
	boolean create(String nameFood, double priceFood, int inventoryFood);
	boolean update(int id, String nameFood, double priceFood, int inventoryFood);
}
