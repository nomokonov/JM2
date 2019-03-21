package com.example.test.service;

import com.example.test.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.test.repository.UserRepo;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    public User getUserById(long id) {
        return userRepo.findById(id).get();
    }

    public List<User> getUsers() {
        return (List<User>) userRepo.findAll();
    }

    public void saveUser(User user) {
        userRepo.save(user);
    }

    public void userUpdate(User user) {
        userRepo.save(user);
    }

    public void deleteUser(User user) {
        userRepo.delete(user);
    }


}
