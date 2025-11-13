package repositoryimpl;

import utils.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import model.FoodDAO;
import model.UserDAO;
import repository.UserRepository;

public class UserRepositoryImpl implements UserRepository {
	private final DataSource ds;
	
	public UserRepositoryImpl(DataSource ds) {
		this.ds = ds;
	}
	
	@Override
	public Boolean isAuthenticated(String username, String password) {
		String sql = "SELECT 1 FROM users WHERE username=? And password=? AND status=1";
		String hassPwd = MD5Generator.generateMD5(password);
        try (Connection conn = ds.getConnection()){
	            PreparedStatement ps = conn.prepareStatement(sql);
	    		ps.setString(1, username);
	    		ps.setString(2, hassPwd);
	            ResultSet rs = ps.executeQuery();
	            if(rs.next()) {
	            	return true;	
	            }
           } catch (Exception e) {
               e.printStackTrace();
           }
        return false;
	}

	@Override
	public UserDAO getUser(String username) {
		UserDAO user = null;
		String sql = "SELECT id, email, full_name, phone_number, photo, role, status FROM users WHERE username=?";
		try (Connection conn = ds.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
    		ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                int id = rs.getInt("id");
                String full_name  = rs.getString("full_name");
                String email = rs.getString("email");
                String phone_number = rs.getString("phone_number");
                String avatar = rs.getString("photo");
                String role = rs.getString("role");
                boolean status = rs.getBoolean("status");
            	user = new UserDAO(id, username, full_name, email, phone_number, avatar, role, status);
            }
       } catch (Exception e) {
           e.printStackTrace();
       }
    	return user;
	}
}
