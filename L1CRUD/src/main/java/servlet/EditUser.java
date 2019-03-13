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
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/edituser", "/saveuser"})
public class EditUser extends HttpServlet {
    private UserService userService = UserServiceImpl.getUserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> root = new HashMap<>();
        User user = userService.getUser(Long.valueOf(req.getParameter("id")));
        root.put("title", "Edit user");
        root.put("user", user);
        root.put("action", "saveuser");
        Template temp = ConfigFreemaker.getConfiguration().getTemplate("editUser.ftl");

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
        long id = Long.valueOf(req.getParameter("id"));
        try {
            userService.userUpdate(new User(id, username, password, description));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String path = req.getContextPath() + "/";
        resp.sendRedirect(path);
    }

}
