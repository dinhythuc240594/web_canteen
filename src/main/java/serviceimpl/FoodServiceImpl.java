package serviceimpl;

import java.util.List;

import javax.sql.DataSource;

import model.FoodDAO;
import repository.FoodRepository;
import repositoryimpl.FoodRepositoryImpl;
import service.FoodService;

public class FoodServiceImpl implements FoodService {

	private FoodRepository foodServiceImpl;
	
	public FoodServiceImpl(DataSource ds) {
		this.foodServiceImpl = new FoodRepositoryImpl(ds);
	}
	
	@Override
	public List<FoodDAO> findAll() {
		return this.foodServiceImpl.findAll();
	}

	@Override
	public FoodDAO findById(int id) {
		return this.foodServiceImpl.findById(id);
	}

	@Override
	public boolean create(String nameFood, double priceFood, int inventoryFood) {
		return this.foodServiceImpl.create(nameFood, priceFood, inventoryFood);
	}

	@Override
	public boolean update(int id, String nameFood, double priceFood, int inventoryFood) {
		return this.foodServiceImpl.update(id, nameFood, priceFood, inventoryFood);
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
