package service;

import java.util.List;

import model.FoodDAO;
import model.Page;
import model.PageRequest;

public interface FoodService {

	Page<FoodDAO> findAll(PageRequest pageRequest);
	FoodDAO findById(int id);
	List<FoodDAO> newFoods();
	List<FoodDAO> promotionFoods();
	boolean create(String nameFood, double priceFood, int inventoryFood);
	boolean update(int id, String nameFood, double priceFood, int inventoryFood);
	boolean delete(int id);
	int count(String keyword);

}
