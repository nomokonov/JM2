package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import service.UserService;

import java.util.Map;

@Controller
public class UsersList   {

    @Autowired
    private UserService userService;

    @GetMapping
    public String listUser (Map<String,Object> model){
        model.put("users",userService.getUsers());
        return "listUsers";
    }
}
