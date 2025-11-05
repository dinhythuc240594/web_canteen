package service;

import model.UserDAO;

public interface UserService {
	Boolean isAuthenticated(String username, String password);
	UserDAO getUser(String username);
}
