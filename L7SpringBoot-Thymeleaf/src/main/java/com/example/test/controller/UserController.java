package com.example.test.controller;

import com.example.test.model.User;
import com.example.test.service.RoleService;
import com.example.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    // All people must have! annotation @Autowired over construcor
    @Autowired
    public UserController(UserService userService,RoleService roleService, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping(value = {"/welcome", "/"})
    public String welcome(
//            @AuthenticationPrincipal User user,
            Principal user,
            Map<String, Object> model) {

        model.put("user", user);
        return "user.html";
    }



}
