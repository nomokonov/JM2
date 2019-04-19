package com.example.test.controller.rest;

import com.example.test.model.User;
import com.example.test.service.RoleService;
import com.example.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/user/")
public class UserRestController {
    private UserService userService;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    // All people must have! annotation @Autowired over constructor
    @Autowired
    public UserRestController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getUsers();
    }

    @GetMapping("{id}")
    public User getById(
            @PathVariable String id) {
        return userService.getUserById(Long.valueOf(id));
    }

    @PutMapping("new")
    public User createUser(@RequestBody User user) {
        userService.saveUser(user);
        return user;
    }

    @PostMapping("update/{id}")
    public User updateUser(
            @PathVariable String id,
            @RequestBody User user) {

        user.setId(Long.valueOf(id));
        userService.saveUser(user);

        return user;
    }

    @DeleteMapping("delete/{id}")
    public void deleteUser(@PathVariable String id) {
        User userFromDB = userService.getUserById(Long.valueOf(id));
        userService.deleteUser(userFromDB);
    }
}
