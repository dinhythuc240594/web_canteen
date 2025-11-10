package service;

import java.util.List;

import model.Order_FoodDAO;

public interface Order_FoodService {
	Order_FoodDAO create(Order_FoodDAO orderFood);
    Order_FoodDAO findById(int id);
    List<Order_FoodDAO> findAll();
    void deleteById(int id);

    List<Order_FoodDAO> findByOrderId(int orderId);
    void deleteByOrderId(int orderId);
}
