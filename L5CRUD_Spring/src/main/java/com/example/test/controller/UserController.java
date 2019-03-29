package com.example.test.controller;

import com.example.test.model.Role;
import com.example.test.model.User;
import com.example.test.model.UserRole;
import com.example.test.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.test.service.UserService;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/")
    public String listUser(Map<String, Object> model) {
        model.put("users", userService.getUsers());
        return "listUsers";
    }

    @GetMapping(value = "/newuser")
    public String userNew(Map<String, Object> model) {
        model.put("title", "New user");
        model.put("action", "adduser");
        model.put("roles",roleService.getRoles());
        return "editUser";
    }

    @PostMapping(value = "/adduser")
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
            return "redirect:/";
        } else {
            model.put("title", "Editing user");
            model.put("message", "the user name is occupied " + userFromDB.getName());
            model.put("action", "adduser");
            model.put("user", userFromDB);
            return "editUser";
        }
    }

    @GetMapping(value = "/edituser")
    public String listUser(
            @RequestParam Long id,
            Map<String, Object> model) {
        model.put("title", "Editing user");
        model.put("action", "saveuser");
        User userfromDB = userService.getUserById(id);
        model.put("user", userfromDB );
        model.put("roles",roleService.getRoles());
        return "editUser";
    }

    @PostMapping(value = "/saveuser")
    public String addUser(
            @RequestParam Long id,
            @RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "role") String role) {

        User userFromDB = userService.getUserById(id);
        userFromDB.setName(username);
        userFromDB.setPassword(password);
        userFromDB.setDescription(description);
        Role roleFromDB = roleService.getByName(role);
        userFromDB.addRole(roleFromDB);
        userService.updateUser(userFromDB);
        return "redirect:/";
    }

    @PostMapping(value = "/deleteuser")
    public String addUser(@RequestParam long id) {
        User userFromDB = userService.getUserById(id);
        userService.deleteUser(userFromDB);
        return "redirect:/";
    }
}
