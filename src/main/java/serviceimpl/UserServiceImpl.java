package serviceimpl;

import javax.sql.DataSource;

import repository.UserRepository;
import repositoryimpl.UserRepositoryImpl;
import service.UserService;

public class UserServiceImpl implements UserService{
	private UserRepository userRepository;
	
	public UserServiceImpl(DataSource ds) {
		this.userRepository = new UserRepositoryImpl(ds);
	}

	@Override
	public Boolean isAuthenticated(String username, String password) {
		return this.userRepository.isAuthenticated(username, password);
	}
}
