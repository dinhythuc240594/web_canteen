package repository;

import java.util.List;

import model.OrderDAO;

public interface OrderRepository {

	OrderDAO save(OrderDAO order);
    OrderDAO findById(int id);
    List<OrderDAO> findAll();
    void deleteById(int id);

    List<OrderDAO> findByUserId(int userId);
    List<OrderDAO> findByStallIdAndStatus(int stallId, String status);
    boolean updateStatus(int id, String newStatus);
	
}
