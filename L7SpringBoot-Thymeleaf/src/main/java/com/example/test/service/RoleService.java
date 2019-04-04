package com.example.test.service;

import com.example.test.model.Role;
import com.example.test.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    RoleRepo roleRepo;

    @Autowired
    public RoleService(RoleRepo roleDao) {
        this.roleRepo = roleDao;
    }

    public List<Role> getRoles() {
        return roleRepo.findAll();
    }

    public Role getRoleById(Long id) {
        return roleRepo.findById(id).get();
    }

    public Role getByName(String name) {
        return roleRepo.findByName(name);
    }
}
