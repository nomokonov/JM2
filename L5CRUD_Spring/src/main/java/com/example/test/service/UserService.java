package com.example.test.service;

import com.example.test.model.User;
import com.example.test.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    public User getUserById(long id) {
        return userRepo.findById(id).get();
    }
    public User getUserByName(String name){
        return  userRepo.findUserByName(name);
    }

    public List<User> getUsers() {
        return (List<User>) userRepo.findAll();
    }

    public void saveUser(User user) {
        userRepo.save(user);
    }

    public void deleteUser(User user) {
        userRepo.delete(user);
    }




}
