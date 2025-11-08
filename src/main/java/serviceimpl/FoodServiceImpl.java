package serviceimpl;

import java.util.List;

import javax.sql.DataSource;

import model.FoodDAO;
import model.Page;
import model.PageRequest;
import repository.FoodRepository;
import repositoryimpl.FoodRepositoryImpl;
import service.FoodService;

public class FoodServiceImpl implements FoodService {

	private FoodRepository foodRepository;
	
	public FoodServiceImpl(DataSource ds) {
		this.foodRepository = new FoodRepositoryImpl(ds);
	}
	
	@Override
	public Page<FoodDAO> findAll(PageRequest pageRequest) {
		List<FoodDAO> data = this.foodRepository.findAll(pageRequest); 
		return new Page<>(data, pageRequest.getPage(), this.foodRepository.count(pageRequest.getKeyword()), pageRequest.getPageSize());
	}

	@Override
	public FoodDAO findById(int id) {
		return this.foodRepository.findById(id);
	}

	@Override
	public boolean create(String nameFood, double priceFood, int inventoryFood) {
		return this.foodRepository.create(nameFood, priceFood, inventoryFood);
	}

	@Override
	public boolean update(int id, String nameFood, double priceFood, int inventoryFood) {
		return this.foodRepository.update(id, nameFood, priceFood, inventoryFood);
	}

	@Override
	public boolean delete(int id) {
		return this.foodRepository.delete(id);
	}

	@Override
	public int count(String keyword) {
		return this.foodRepository.count(keyword);
	}

	@Override
	public List<FoodDAO> newFoods() {
		return this.foodRepository.newFoods();
	}

	@Override
	public List<FoodDAO> promotionFoods() {
		return this.foodRepository.promotionFoods();
	}

}
