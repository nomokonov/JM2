package servlet;

import model.User;
import service.UserService;
import service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/deleteuser")
public class UserDelete extends HttpServlet {
    private UserService userService = UserServiceImpl.getUserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = userService.getUserById(Long.valueOf(req.getParameter("id")));
        if (user != null) {
            try {
                userService.deleteUser(user);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String path = req.getContextPath() + "/admin";
            resp.sendRedirect(path);
        }


    }
}
