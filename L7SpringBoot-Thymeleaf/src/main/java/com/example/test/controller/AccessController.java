package com.example.test.controller;

import com.example.test.service.SocialAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Controller
public class AccessController {

    @Autowired
    SocialAuthService socialAuthService;


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

        final String authorizationUrl = socialAuthService.oAuth20Service().getAuthorizationUrl();

        model.put("authorizationUrl", authorizationUrl);
        if (error) {
            model.put("message", "Wrong username or password");
        }

        return "login.html";
    }

    @GetMapping(value = "/useronly/")
    public String useronly(
            Principal user,
            Map<String, Object> model) {

        model.put("user", user);
        return "user.html";
    }


    //  Authorization OAuth2
    @GetMapping(value = {"/oauth2"})
    public String ouath2(
            @RequestParam(required = false) String code,
            Map<String, Object> model) throws InterruptedException, ExecutionException, IOException {

        if (socialAuthService.GoogleOAuth2(code)) {
            return "redirect:/user/";
        } else {
            model.put("message", "Need authorized or Sign in");
            return "login.html";
        }

    }

}
