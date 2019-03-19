package servlet;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import model.User;
import service.UserService;
import service.UserServiceImpl;
import util.ConfigFreemaker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/user"})
public class WelcomUser extends HttpServlet {
    private UserService userService = UserServiceImpl.getUserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> root = new HashMap<>();
        Template temp = ConfigFreemaker.getConfiguration().getTemplate("welcomeuser.ftl");
        resp.setContentType("text/html;charset=utf-8");
        User user = (User) req.getSession().getAttribute("loginUser");
        root.put("user", user);
        PrintWriter writer = resp.getWriter();
        try {
            temp.process(root, writer);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (TemplateException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }
    }
}
