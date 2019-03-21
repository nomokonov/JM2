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
    public String listUser (Map<String,Object> model){
        model.put("users",userService.getUsers());
        return "listUsers";
    }

    @RequestMapping(value = "/newuser", method = RequestMethod.GET)
    public String userNew (Map<String, Object> model){
        model.put("title", "New user");
        model.put("action", "adduser");
        return "editUser";
    }
       @RequestMapping(value = "/newuser", method = RequestMethod.POST)
    public String addUser (
            @RequestParam(name="username") String username,
            @RequestParam(name="password") String password,
            @RequestParam(name="description") String description,
            @RequestParam(name="role") String role){

            userService.saveUser(new User(username,password,description,role));
        return "redirect:/listuser";
    }

    @RequestMapping(value = "/deleteuser", method = RequestMethod.POST)
    public String addUser (  @RequestParam("id") User user){
            userService.deleteUser(user);
        return "redirect:/listuser";
    }
}
