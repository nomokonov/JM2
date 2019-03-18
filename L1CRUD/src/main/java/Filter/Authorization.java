package Filter;

import model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class Authorization implements Filter {
    private static User user;

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
        if (user != null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        } else {
            RequestDispatcher dispatcher
                    = servletRequest.getServletContext().getRequestDispatcher("/login");
            dispatcher.forward(servletRequest, servletResponse);
            return;
        }
//        System.out.println("#INFO " + new Date() + " - ServletPath :" + servletPath //
//                + ", URL =" + req.getRequestURL());

        // Разрешить request продвигаться дальше. (Перейти данный Filter).
//        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("Filter destroy!");
    }
}
