package com.example.test.service;

import com.example.test.model.Role;
import com.example.test.repository.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    RoleDao roleDao;

    @Autowired
    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public List<Role> getRoles() {
        return roleDao.findAll();
    }

    public Role getRoleById(Long id) {
        return roleDao.findById(id);
    }

    public Role getByName(String name) {
        return roleDao.findByName(name);
    }
}
