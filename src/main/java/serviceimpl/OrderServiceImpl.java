package serviceimpl;

import java.util.List;

import javax.sql.DataSource;

import model.OrderDAO;
import repository.OrderRepository;
import repositoryimpl.OrderRepositoryImpl;
import service.OrderService;

public class OrderServiceImpl implements OrderService{

	private OrderRepository orderRepository;
	
	public OrderServiceImpl(DataSource ds) {
		this.orderRepository = new OrderRepositoryImpl(ds);
	}
	
	@Override
	public OrderDAO save(OrderDAO order) {
		return this.orderRepository.save(order);
	}

	@Override
	public OrderDAO findById(int id) {
		return this.orderRepository.findById(id);
	}

	@Override
	public List<OrderDAO> findAll() {
		return this.orderRepository.findAll();
	}

	@Override
	public void deleteById(int id) {
		this.orderRepository.deleteById(id);
	}

	@Override
	public List<OrderDAO> findByUserId(int userId) {
		return this.orderRepository.findByUserId(userId);
	}

	@Override
	public List<OrderDAO> findByStallIdAndStatus(int stallId, String status) {
		return this.orderRepository.findByStallIdAndStatus(stallId, status);
	}

	@Override
	public boolean updateStatus(int id, String newStatus) {
		return this.orderRepository.updateStatus(id, newStatus);
	}

}
