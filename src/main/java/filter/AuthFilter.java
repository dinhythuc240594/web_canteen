package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet Filter implementation class AuthFilter
 */
@WebFilter(urlPatterns = {"/*"})
public class AuthFilter extends HttpFilter implements Filter {
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public AuthFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
		HttpServletRequest req  = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);

        HttpSession ses = req.getSession(false);

        String path = req.getRequestURI().substring(req.getContextPath().length());
        boolean loggedIn = (ses != null && ses.getAttribute("username") != null);
        
        boolean isLoginRequest = path.equals("/login") 
        		|| path.equals("/login.jsp")
        		|| path.equals("/home")
        		|| path.equals("/");
        System.out.println(path.equals("/"));
        System.out.println(loggedIn);
        if (loggedIn && isLoginRequest) {
            resp.sendRedirect(req.getContextPath() + "/foods");
            return;
        }
        
        if (!loggedIn && !isLoginRequest) {
        	String next = req.getRequestURI();
        	resp.sendRedirect(req.getContextPath() + "/login?next=" + next);
            return;
        }
        
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
