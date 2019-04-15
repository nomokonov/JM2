package com.example.test.controller.rest;

import com.example.test.model.Role;
import com.example.test.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/role")
public class RoleRestController {
    private RoleService roleService;

    @Autowired
    public RoleRestController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public List<Role> getAll() {
        return roleService.getRoles();
    }
}
