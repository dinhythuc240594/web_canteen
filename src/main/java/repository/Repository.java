package repository;

import java.util.List;

public interface Repository<T> {

	List<T> findAll();
	T findById(int id);
	boolean delete(int id);
	
}
