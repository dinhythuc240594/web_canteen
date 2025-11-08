package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UserDAO;
import serviceimpl.UserServiceImpl;
import utils.DataSourceUtil;

import java.io.IOException;

import javax.sql.DataSource;

import com.mysql.cj.Session;

/**
 * Servlet implementation class LoginServerlet
 */
@WebServlet("/login")
public class LoginServerlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6032073007253274585L;
	private UserServiceImpl userSerImpl;

	@Override
	public void init() throws ServletException {
		DataSource ds = DataSourceUtil.getDataSource();
		this.userSerImpl = new UserServiceImpl(ds);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
        rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		boolean isLogin = this.userSerImpl.isAuthenticated(username, password);
		HttpSession session = request.getSession();
		
		if(isLogin) {
			UserDAO user = this.userSerImpl.getUser(username);
			session.setAttribute("is_login", isLogin);
			session.setAttribute("username", user.getUsername());
			session.setAttribute("type_user", user.getRole());
			response.sendRedirect(request.getContextPath()+"/foods?action=list");
		}
		else {
            request.setAttribute("error", "Login Failed, check user name and password");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
		}
	}

}
