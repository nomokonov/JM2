package Filter;

import model.User;
import org.hibernate.Session;
import service.UserService;
import service.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/user","/admin"})
public class Authorization implements Filter {
    private UserService userService = UserServiceImpl.getUserService();
    private User user;

    public Authorization() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        if (((HttpServletRequest) servletRequest).getServletPath().equals("/login")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
           user = (User) req.getSession().getAttribute("loginUser");

        if (user != null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        } else {
            RequestDispatcher dispatcher
                    = servletRequest.getServletContext().getRequestDispatcher("/login");
            dispatcher.forward(servletRequest, servletResponse);
            return;
        }
    }

    @Override
    public void destroy() {
    }
}
