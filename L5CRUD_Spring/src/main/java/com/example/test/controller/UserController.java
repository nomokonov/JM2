package com.example.test.controller;

import com.example.test.model.Role;
import com.example.test.model.User;
import com.example.test.service.RoleService;
import com.example.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping(value = {"/welcome", "/"})
    public String welcome(
//            @AuthenticationPrincipal User user,
            Principal user,
            Map<String, Object> model) {

        model.put("user", user);
        return "welcomeuser";
    }

    @GetMapping(value = "/only")
    public String useronly(
            Principal user,
            Map<String, Object> model) {

        model.put("user", user);
        return "welcomeuser";
    }


}
