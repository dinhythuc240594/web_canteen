package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
			session.setAttribute("username", username);
			response.sendRedirect(request.getContextPath()+"/foods?action=list");
		}
		else {
            request.setAttribute("error", "Login Failed, check user name and password");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
		}
	}

}
