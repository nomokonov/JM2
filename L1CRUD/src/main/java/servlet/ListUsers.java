package servlet;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import model.User;
import service.UserService;
import service.UserServiceImpl;
import util.ConfigFreemaker;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("")
public class ListUsers extends HttpServlet {

    private UserService userService = UserServiceImpl.getUserService();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> root = new HashMap<>();
        root.put("title", "User list");
        List<User> userList = userService.getUsers();
        root.put("users", userList);
        Template temp = ConfigFreemaker.getConfiguration().getTemplate("listUsers.ftl");
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


}
