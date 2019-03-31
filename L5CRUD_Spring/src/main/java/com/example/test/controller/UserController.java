package com.example.test.controller;

import com.example.test.model.Role;
import com.example.test.model.User;
import com.example.test.model.UserRole;
import com.example.test.service.RoleService;
import com.example.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/admin/listuser")
    public String listUser(Map<String, Object> model) {
        model.put("users", userService.getUsers());
        return "listUsers";
    }

    @GetMapping(value = "/user/*")
    public String welcome(Map<String, Object> model) {
        model.put("users", userService.getUsers());
        return "welcomeuser";
    }

    @GetMapping(value = "/admin/newuser")
    public String userNew(Map<String, Object> model) {
        model.put("title", "New user");
        model.put("action", "adduser");
        model.put("roles",roleService.getRoles());
        return "editUser";
    }

    @PostMapping(value = "/admin/adduser")
    public String addUser(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "listRoles") long[] roles,
            Map<String, Object> model) {

        User userFromDB = userService.getUserByName(username);
        if (userFromDB == null) {
            User user = new User(username, password, description);
            for (long role: roles) {
                Role roleFromDB = roleService.getRoleById(role);
                user.addRole(roleFromDB);
            }

            userService.saveUser(user);
            return "redirect:/admin/listuser";
        } else {
            model.put("title", "Editing user");
            model.put("message", "the user name is occupied " + userFromDB.getName());
            model.put("action", "adduser");
            model.put("user", userFromDB);
            return "editUser";
        }
    }

    @GetMapping(value = "/admin/edituser")
    public String listUser(
            @RequestParam Long id,
            Map<String, Object> model) {
        model.put("title", "Editing user");
        model.put("action", "saveuser");
        User userfromDB = userService.getUserById(id);
        model.put("user", userfromDB );
//for views roles gets
        List<Long> rolesForView = getRolesForView(userfromDB);
        model.put("userroles", rolesForView);
        model.put("roles", roleService.getRoles());
        return "editUser";
    }

    @PostMapping(value = "/admin/saveuser")
    public String addUser(
            @RequestParam Long id,
            @RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "listRoles") long[] listRoles) {

        User userFromDB = userService.getUserById(id);
        userFromDB.setName(username);
        userFromDB.setPassword(password);
        userFromDB.setDescription(description);
        userFromDB.getRoles().clear();
        if (listRoles.length > 0){
            for (long role: listRoles) {
                Role roleFromDB = roleService.getRoleById(role);
                userFromDB.addRole(roleFromDB);
            }
        }
        userService.updateUser(userFromDB);
        return "redirect:/admin/listuser";
    }

    @PostMapping(value = "/admin/deleteuser")
    public String addUser(@RequestParam long id) {
        User userFromDB = userService.getUserById(id);
        userService.deleteUser(userFromDB);
        return "redirect:/listuser";
    }
//  Authorization
    @GetMapping(value = {"/login","/"})
    public String login() {
        return "login";
    }



//    Вспомогательная для вывода ролей во вьюху и выбора существующих ролей  юзверя выбранного
    private List<Long> getRolesForView(User user){
        List<Long> arr = new ArrayList<Long>();
        for (UserRole role: user.getRoles()) {
            arr.add(role.getRole().getId());
        }
        return arr;
    }
}
