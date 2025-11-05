package repository;

import model.UserDAO;

public interface UserRepository {
	Boolean isAuthenticated(String username, String password);
	UserDAO getUser(String username);
}
