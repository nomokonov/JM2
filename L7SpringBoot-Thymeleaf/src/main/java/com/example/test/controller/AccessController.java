package com.example.test.controller;

import com.example.test.service.UserService;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
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
    AuthenticationManager authenticationManager;

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
            @RequestParam(required = false) String code,
            Map<String, Object> model) throws InterruptedException, ExecutionException, IOException {

        OAuth2AccessToken accessToken = oAuth20Service.getAccessToken(code);
//        get Google profile
        OAuthRequest request = new OAuthRequest(Verb.GET,"https://www.googleapis.com/oauth2/v1/userinfo?alt=json");
        oAuth20Service.signRequest(accessToken,request);
        Response response = oAuth20Service.execute(request);
        model.put("message",response.getBody());



        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken("user", "pass");
        Authentication auth = authenticationManager.authenticate(authReq);

        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        HttpSession session = req.getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);
        return "login.html";
    }

}
