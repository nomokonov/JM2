package com.example.test.controller;

import com.example.test.model.User;
import com.example.test.service.RoleService;
import com.example.test.service.UserService;
import com.example.test.util.GoogleUser;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Controller
public class AccessController {
    @Autowired
    OAuth20Service oAuth20Service;
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;
    @Autowired
    PasswordEncoder passwordEncoder;

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

        final String authorizationUrl = oAuth20Service.getAuthorizationUrl();

        model.put("authorizationUrl", authorizationUrl);
        if (error) {
            model.put("message", "Wrong username or password");
        }

        return "login.html";
    }

    //  Authorization OAuth2
    @GetMapping(value = {"/oauth2"})
    public String ouath2(
            HttpServletRequest req,
            HttpServletResponse httpResponse,
            @RequestParam(required = false) String code,
            Map<String, Object> model) throws InterruptedException, ExecutionException, IOException {

        OAuth2AccessToken accessToken = oAuth20Service.getAccessToken(code);
//        get Google profile
        OAuthRequest request = new OAuthRequest(Verb.GET, "https://www.googleapis.com/oauth2/v3/userinfo");
        oAuth20Service.signRequest(accessToken, request);
        Response response = oAuth20Service.execute(request);

        Gson gson = new Gson();
        GoogleUser person = gson.fromJson(response.getBody(), GoogleUser.class);

        User userFromDB = new User(person.getName(), passwordEncoder.encode(person.getSub()), person.getSub());

        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(userFromDB,
                roleService.getByName("ROLE_USER"),userFromDB.getAuthorities());
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(authReq);

        return "redirect:/user/";
    }

}
