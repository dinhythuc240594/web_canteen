package repository;

import java.util.List;

import model.FoodDAO;
import model.PageRequest;

public interface FoodRepository extends Repository<FoodDAO>{
	
	List<FoodDAO> findAll(PageRequest pageRequest);
	boolean create(String nameFood, double priceFood, int inventoryFood);
	boolean update(int id, String nameFood, double priceFood, int inventoryFood);
}
