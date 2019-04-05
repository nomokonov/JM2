package com.example.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class AccessController {
    @GetMapping(value = "/403")
    public String listUser(Map<String, Object> model) {
        model.put("message", "Access denied (403)");
        return "login.html";
    }

    //  Authorization
    @GetMapping(value = {"/login", "/"})
    public String login(
            @RequestParam(required = false) boolean error,
            Map<String, Object> model) {
        if (error) {
            model.put("message", "Wrong username or password");
        }
        return "login.html";
    }
}
