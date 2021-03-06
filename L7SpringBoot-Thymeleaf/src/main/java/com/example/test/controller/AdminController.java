package com.example.test.controller;

import com.example.test.model.Role;
import com.example.test.model.User;
import com.example.test.service.RoleService;
import com.example.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = {"/listuser", "/"})
    public String listUser(Map<String, Object> model) {
        model.put("users", userService.getUsers());
        model.put("roles", roleService.getRoles());
        return "admin";
    }

//    @GetMapping(value = "/newuser")
//    public String userNew(Map<String, Object> model) {
//        model.put("title", "New user");
//        model.put("action", "adduser");
//        model.put("userroles", new int[]{-111});
//        model.put("roles", roleService.getRoles());
//        return "editUser";
//    }

    @PostMapping(value = "/adduser")
    public String addUser(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "listRoles") long[] roles,
            Map<String, Object> model) {

        User userFromDB = userService.getUserByName(username);
        if (userFromDB == null) {
            User user = new User(username, passwordEncoder.encode(password), description);
            user.setEmail(email);
            for (long role : roles) {
                Role roleFromDB = roleService.getRoleById(role);
                user.addRole(roleFromDB);
            }

            userService.saveUser(user);
            return "redirect:/admin/";
        } else {
            model.put("title", "Editing user");
            model.put("message", "the user name is occupied " + userFromDB.getName());
            model.put("action", "adduser");
            model.put("user", userFromDB);
            return "admin";
        }
    }

    @GetMapping(value = "/edituser")
    public String listUser(
            @RequestParam Long id,
            Map<String, Object> model) {
        model.put("title", "Editing user");
        model.put("action", "saveuser");
        User userfromDB = userService.getUserById(id);
        model.put("user", userfromDB);
//for views roles gets
        List<Long> rolesForView = getRolesForView(userfromDB);
        model.put("userroles", rolesForView);
        model.put("roles", roleService.getRoles());
        return "editUser";
    }

    @PostMapping(value = "/saveuser")
    public String addUser(
            @RequestParam Long id,
            @RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "listRoles") long[] listRoles) {

        User userFromDB = userService.getUserById(id);
        userFromDB.setName(username);
        if ( !userFromDB.getPassword().equals(password) ){
            userFromDB.setPassword(passwordEncoder.encode(password));
        }

        userFromDB.setEmail(email);
        userFromDB.setDescription(description);
        userFromDB.getRoles().clear();
        if (listRoles.length > 0) {
            for (long role : listRoles) {
                Role roleFromDB = roleService.getRoleById(role);
                userFromDB.addRole(roleFromDB);
            }
        }
        userService.updateUser(userFromDB);
        return "redirect:/admin/";
    }

    @PostMapping(value = "/deleteuser")
    public String addUser(@RequestParam long id) {
        User userFromDB = userService.getUserById(id);
        userService.deleteUser(userFromDB);
        return "redirect:/admin/listuser";
    }

    //    Вспомогательная для вывода ролей во вьюху и выбора существующих ролей  юзверя выбранного
    private List<Long> getRolesForView(User user) {
        List<Long> arr = new ArrayList<Long>();
        for (Role role : user.getRoles()) {
            arr.add(role.getId());
        }
        return arr;
    }
}
