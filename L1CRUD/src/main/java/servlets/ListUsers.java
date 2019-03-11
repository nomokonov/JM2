package servlets;

import dao.User;
import dbService.DBService;
import dbService.DBServiceImpl;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/*")
public class ListUsers extends HttpServlet {
    private static final String HTML_DIR = "templates/";
    private DBService dbService;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // create table 'users" if not exist
        dbService = new DBServiceImpl();
        dbService.init();
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
        switch (pathInfo){
//            List users
            case "/":
                root.put("title", "User list");
                List<User> userList = dbService.getUsers();
                root.put("users",userList);
                 temp = cfg.getTemplate(HTML_DIR + "listUsers.ftl");
                break;
//                New users or edit
            case "/edituser":
                root.put("title", "Edit user");
                temp = cfg.getTemplate(HTML_DIR + "editUser.ftl");
                break;
            case "/newuser" :
                root.put("title", "New user");
                temp = cfg.getTemplate(HTML_DIR + "editUser.ftl");
                break;
        }
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        try {
            temp.process(root, writer);
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
