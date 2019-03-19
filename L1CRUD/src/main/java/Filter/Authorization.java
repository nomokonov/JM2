package Filter;

import model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class Authorization implements Filter {
    private User user;

    public Authorization() {
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        user = (User) req.getSession().getAttribute("loginUser");
        if (user != null || ((HttpServletRequest) servletRequest).getServletPath().equals("/login")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        } else {
            String path = req.getContextPath() + "/login";
            ((HttpServletResponse) servletResponse).sendRedirect(path);
            return;
        }
    }
}
