package service;

import java.util.List;

import model.OrderDAO;

public interface OrderService {

	OrderDAO save(OrderDAO order);
    OrderDAO findById(int id);
    List<OrderDAO> findAll();
    void deleteById(int id);

    List<OrderDAO> findByUserId(int userId);
    List<OrderDAO> findByStallIdAndStatus(int stallId, String status);
    boolean updateStatus(int id, String newStatus);
	
}
