package com.example.test.config;

import com.example.test.model.User;
import com.example.test.service.RoleService;
import com.example.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DataInitializer {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    PasswordEncoder passwordEncoder;



    private void init() {

        roleService.addRole("ROLE_ADMIN","admin");
        roleService.addRole("ROLE_USER","user");
        roleService.addRole("ROLE_BUR","buratin");

        // DEFAULT STATUS AND FIRST STATUS FOR RELEASE
        User admin = new User("admin",passwordEncoder.encode("admin"),"admin");
        admin.addRole(roleService.getByName("ROLE_ADMIN"));
        admin.addRole(roleService.getByName("ROLE_USER"));
        userService.saveUser(admin);

        User user1 = new User ("user",passwordEncoder.encode("user"));
        user1.addRole(roleService.getByName("ROLE_USER"));
        userService.saveUser(user1);

    }
}
