package controller;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

@Controller
public class UserNew {
    @Autowired
    private UserService userService;

    @GetMapping
    public String userNew (Model model){
        model.addAttribute("users",userService.getUsers());
        model.addAllAttributes("title", "New user");
        model.addAllAttributes("action", "adduser");
        return "editUser";
    }
    @PostMapping
    public String userNew (
            @RequestParam(name="username") String,
            Map<String,Object> model){

        model.put("users",userService.getUsers());
        model.put("title", "New user");
        model.put("action", "adduser");
        return "redirect:listUser";
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String description = req.getParameter("description");
        String role = req.getParameter("role");
        try {
            User user = new User(username, password, description);
            user.setRole(role);
            userService.addUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String path = req.getContextPath() + "/admin";
        resp.sendRedirect(path);
    }

}
