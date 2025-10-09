package repository;

public interface UserRepository {
	Boolean isAuthenticated(String username, String password);
}
