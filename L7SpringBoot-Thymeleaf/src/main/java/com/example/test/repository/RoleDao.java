package com.example.test.repository;

import com.example.test.model.Role;

import java.util.List;

public interface RoleDao {
    Role findById(Long id);
    Role findByName(String name);
    List<Role> findAll();

}
