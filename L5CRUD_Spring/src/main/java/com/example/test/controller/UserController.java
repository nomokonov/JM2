package com.example.test.controller;

import com.example.test.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.test.service.UserService;

import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String listUser(Map<String, Object> model) {
        model.put("users", userService.getUsers());
        return "listUsers";
    }

    @RequestMapping(value = "/newuser", method = RequestMethod.GET)
    public String userNew(Map<String, Object> model) {
        model.put("title", "New user");
        model.put("action", "adduser");
        return "editUser";
    }

    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    public String addUser(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "role") String role,
            Map<String, Object> model) {

        User userFromDB = userService.getUserByName(username);
        if (userFromDB == null){
            userService.saveUser(new User(username, password, description, role));
            return "redirect:/";
        }else{
            model.put("title", "Editing user");
            model.put("message","the user name is occupied " + userFromDB.getName() );
            model.put("action","adduser");
            model.put("user",new User(username,password,description,role));
            return "editUser";
        }
    }

    @RequestMapping(value = "/edituser", method = RequestMethod.GET)
    public String listUser(
            @RequestParam Long id,
            Map<String, Object> model) {
        model.put("title", "Editing user");
        model.put("action", "saveuser");
        model.put("user", userService.getUserById(id));
        return "editUser";
    }

    @RequestMapping(value = "/saveuser", method = RequestMethod.POST)
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
        userFromDB.setRole(role);
        userService.saveUser(userFromDB);
        return "redirect:/";
    }

    @RequestMapping(value = "/deleteuser", method = RequestMethod.POST)
    public String addUser(@RequestParam long id ) {
        User userFromDB = userService.getUserById(id);
        userService.deleteUser(userFromDB);
        return "redirect:/";
    }
}
