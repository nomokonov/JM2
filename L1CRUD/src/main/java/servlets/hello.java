package servlets;



import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/")
public class hello extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.println("<H1>Hello!</H1><br>");
        writer.println("<p>Jetty based WebApp!</p><br>");
        writer.println(req.getContextPath());
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
