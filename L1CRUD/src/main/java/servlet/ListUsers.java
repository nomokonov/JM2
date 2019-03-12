package servlet;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import model.User;
import service.UserService;
import service.UserServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/*")
public class ListUsers extends HttpServlet {
    private static final String HTML_DIR = "templates/";
    private UserService userService = UserServiceImpl.getUserService();

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // create table 'users" if not exist
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
        ClassTemplateLoader loader = new ClassTemplateLoader(
                new ListUsers().getClass(), "/");

        cfg.setTemplateLoader(loader);
        cfg.setDefaultEncoding("UTF-8");

        String pathInfo = req.getPathInfo();
        Map<String, Object> root = new HashMap<>();
        Template temp = null;
        switch (pathInfo) {
//            List users
            case "/":
                root.put("title", "User list");
                List<User> userList = userService.getUsers();
                root.put("users", userList);
                temp = cfg.getTemplate(HTML_DIR + "listUsers.ftl");
                break;
//                New users or edit
            case "/edituser":
                User user = userService.getUser(Long.valueOf(req.getParameter("id")));
                root.put("title", "Edit user");
                root.put("user", user);
                root.put("action", "saveuser");
                temp = cfg.getTemplate(HTML_DIR + "editUser.ftl");
                break;
            case "/newuser":
                root.put("title", "New user");
                root.put("action", "adduser");
                temp = cfg.getTemplate(HTML_DIR + "editUser.ftl");
                break;
        }
        resp.setContentType("text/html;charset=utf-8");
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
        String description = req.getParameter("description");
        String pathInfo = req.getPathInfo();
        switch (pathInfo) {
            case "/adduser":
                try {
                    userService.addUser(new User(username, password, description));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "/saveuser":
                long id = Long.valueOf( req.getParameter("id"));
                try {
                    userService.userUpdate(new User(id, username, password, description));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "/deleteuser":
                User user = userService.getUser(Long.valueOf( req.getParameter("id")));
                if (user != null) {
                    try {
                        userService.deleteUser(user);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
        String path = req.getContextPath() + "/";
        resp.sendRedirect(path);
    }

}
