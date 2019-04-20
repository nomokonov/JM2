package com.example.test.controller.rest;

import com.example.test.model.User;
import com.example.test.service.RoleService;
import com.example.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

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
    public ResponseEntity<?> LgetAll() {
        return new ResponseEntity<>(userService.getUsers(),HttpStatus.ACCEPTED);
    }

    @GetMapping("{id}")
    public ResponseEntity<User>getById(
            @PathVariable String id) {
        try{
            User user = userService.getUserById(Long.valueOf(id));
            return new ResponseEntity<User>(user, HttpStatus.OK)  ;
        }catch (EntityNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND)  ;
        }

    }

    @PutMapping("new")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<User>(user,
                HttpStatus.CREATED);
    }

    @PostMapping("update/{id}")
    public ResponseEntity<User>  updateUser(
            @PathVariable String id,
            @RequestBody User user) {

        user.setId(Long.valueOf(id));
        userService.saveUser(user);

        return new ResponseEntity<User>( user,
                HttpStatus.ACCEPTED);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable String id) {
        User userFromDB = userService.getUserById(Long.valueOf(id));
        userService.deleteUser(userFromDB);
        return new ResponseEntity<User>(userFromDB,HttpStatus.OK);
    }
}
