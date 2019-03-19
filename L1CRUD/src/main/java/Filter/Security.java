package Filter;

import model.User;
import service.UserService;
import service.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/admin/*"})
public class Security implements Filter {
    private UserService userService = UserServiceImpl.getUserService();
    private User user;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {


    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;


        user = (User) req.getSession().getAttribute("loginUser");

        if (user != null && user.getRole().equalsIgnoreCase("admin")) {
            chain.doFilter(request, response);
            return;
        } else {
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
    }

    @Override
    public void destroy() {

    }
}
