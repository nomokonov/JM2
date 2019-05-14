package com.example.test.service;

import com.example.test.model.User;
import com.example.test.util.GoogleUser;
import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Service
public class SocialAuthService {


    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;
    @Autowired
    PasswordEncoder passwordEncoder;


    public boolean GoogleOAuth2(String code) throws IOException, InterruptedException, ExecutionException {
        OAuth2AccessToken accessToken = oAuth20Service().getAccessToken(code);
//        get Google profile
        OAuthRequest request = new OAuthRequest(Verb.GET, "https://www.googleapis.com/oauth2/v3/userinfo");
        oAuth20Service().signRequest(accessToken, request);
        Response response = oAuth20Service().execute(request);

        Gson gson = new Gson();
        GoogleUser person = gson.fromJson(response.getBody(), GoogleUser.class);
        User userFromGoogle = new User(person.getName(), passwordEncoder.encode(person.getSub()), person.getSub());
        userFromGoogle.addRole(roleService.getByName("ROLE_USER"));
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(userFromGoogle,
                roleService.getByName("ROLE_USER"), userFromGoogle.getAuthorities());
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(authReq);
        return true;
    }


    public OAuth20Service oAuth20Service() {
        return new ServiceBuilder("8557833449-mgqon77b3gturt3debee5i4rsts1e1lr.apps.googleusercontent.com")
                .apiSecret("IBhDFvBzmJ66tiO8CMPuBZCA")
                .defaultScope("profile")
                .callback("http://localhost:8080/oauth2")
                .build(GoogleApi20.instance());
    }
}
