package servlets;

import dao.User;
import dbService.DBService;
import dbService.DBServiceImpl;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/")
public class ListUsers extends HttpServlet {
    private static final String HTML_DIR = "templates";

    DBService dbService = new DBServiceImpl();
    private List<User> usersList = new ArrayList<>();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
// модель данных
        Map<String, Object> root = new HashMap<>();
        root.put("name", "Freemarker");
// шаблон
        Template temp = cfg.getTemplate(HTML_DIR + File.separator + "listUsers.ftl");

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
