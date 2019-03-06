package servlets;



import dao.User;
import dbService.DBService;
import dbService.DBServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/")
public class ListUsers extends HttpServlet {

    DBService dbService = new DBServiceImpl();
    private List<User> usersList = new ArrayList<>();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.println("<H1>Hello!</H1><br>");
        writer.println("<p>Jetty based WebApp!</p><br>");
        writer.println(req.getContextPath());
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
