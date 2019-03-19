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

@WebServlet(urlPatterns = {"/login","/"})
public class Login extends HttpServlet {
    private static final String ADMIN = "admin";
    private static final String USER = "user";
    private UserService userService = UserServiceImpl.getUserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> root = new HashMap<>();
        Template temp = ConfigFreemaker.getConfiguration().getTemplate("login.ftl");
        resp.setContentType("text/html;charset=utf-8");

        root.put("username","Login");
        PrintWriter writer = resp.getWriter();
        try {
            temp.process(root, writer);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (TemplateException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Map<String, Object> root = new HashMap<>();
        PrintWriter writer = resp.getWriter();
        resp.setContentType("text/html;charset=utf-8");

        try {
            User user = userService.getUserByName(username);
            if ( user == null  || !user.getPassword().equals(password) ){
                Template temp = ConfigFreemaker.getConfiguration().getTemplate("login.ftl");
                root.put("message","Username or password inccorrect!");
                temp.process(root, writer);
                resp.setStatus(HttpServletResponse.SC_OK);
                return;
            } else {
                req.getSession().setAttribute("loginUser",user);
                if (user.getRole().equalsIgnoreCase(ADMIN)) {

                    String path = req.getContextPath() + "/admin";
                    resp.sendRedirect(path);
                }else {
                    String path = req.getContextPath() + "/user";
                    resp.sendRedirect(path);
                }


            }

        } catch (TemplateException e) {
            e.printStackTrace();
        }

    }
}
