package repositoryimpl;

import utils.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import repository.UserRepository;

public class UserRepositoryImpl implements UserRepository {
	private final DataSource ds;
	
	public UserRepositoryImpl(DataSource ds) {
		this.ds = ds;
	}
	
	@Override
	public Boolean isAuthenticated(String username, String password) {
		String sql = "SELECT 1 FROM user WHERE username=? And password=? AND status=1";
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
}
