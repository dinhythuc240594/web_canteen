package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.TokenDAO;
import model.UserDAO;
import serviceimpl.TokenServiceImpl;
import serviceimpl.UserServiceImpl;
import utils.DataSourceUtil;
import utils.SHA256;

import java.io.IOException;
import java.util.UUID;

import javax.sql.DataSource;

/**
 * Servlet Filter implementation class RememberMeFilter
 */
@WebFilter("/*")
public class RememberMeFilter extends HttpFilter implements Filter {
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public RememberMeFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	private UserServiceImpl userSerImpl;
	private TokenServiceImpl tokenSerImpl;

	@Override
	public void init() throws ServletException {
		DataSource ds = DataSourceUtil.getDataSource();
		this.userSerImpl = new UserServiceImpl(ds);
		this.tokenSerImpl = new TokenServiceImpl(ds);
	}
	
	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req  = (HttpServletRequest) request;
		HttpServletResponse resp  = (HttpServletResponse) response;

        HttpSession ses = req.getSession(false);        
        
        if (ses != null && ses.getAttribute("is_login") != null) {
    		chain.doFilter(request, response);
        }

        String rawToken = getRememberMeToken(req);        
        if (rawToken != null) {

            String tokenHash = SHA256.hash256(rawToken);

            TokenDAO dbToken = this.tokenSerImpl.findTokenByHash(tokenHash);

            if (dbToken != null) {
                if(!isTokenExpired(dbToken)) {
                    UserDAO user = this.userSerImpl.getUser(dbToken.getUsername());
                    
                    HttpSession newSession = req.getSession(true);
                    newSession.setAttribute("is_login", true);
                    newSession.setAttribute("username", user.getUsername());
                    
                    String newToken = UUID.randomUUID().toString();
                    String newTokenHash = SHA256.hash256(newToken);
                    
                    Cookie newCookie = new Cookie("canteenSID", newToken);

                    this.tokenSerImpl.updateTokenHash(dbToken.getSeries(), newTokenHash);

                    ((HttpServletResponse) response).addCookie(newCookie);	
                } else {
                	this.tokenSerImpl.deleteTokenBySeries(dbToken.getSeries());
                	deleteRememberMeCookie(resp);
                }
            } else{
            	deleteRememberMeCookie(resp);
            }
        } else {
            chain.doFilter(req, resp); 
            return;
        }
        
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	private void deleteRememberMeCookie(HttpServletResponse response) {
        Cookie expiredCookie = new Cookie("canteenSID", "");
        expiredCookie.setMaxAge(0);
        expiredCookie.setHttpOnly(true);
        expiredCookie.setPath("/");
        response.addCookie(expiredCookie);
    }
	
	private String getRememberMeToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("canteenSID")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
	
	private boolean isTokenExpired(TokenDAO dbToken) {
	    if (dbToken == null || dbToken.getExpires() == null) {
	        return true; 
	    }
	    
	    long expirationTimeMillis = dbToken.getExpires().getTime();
	    
	    long currentTimeMillis = System.currentTimeMillis();
	    
	    return expirationTimeMillis <= currentTimeMillis;
	}
	
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
