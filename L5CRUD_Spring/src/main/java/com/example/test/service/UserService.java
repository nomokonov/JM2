package com.example.test.service;

import com.example.test.model.User;
import com.example.test.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public User getUserById(long id) {
        return userDao.findById(id);
    }
    public User getUserByName(String name){
        return  userDao.findByName(name);
    }

    public List<User> getUsers() {
        return (List<User>) userDao.findAll();
    }

    public void saveUser(User user) {
        userDao.save(user);
    }

    public void deleteUser(User user) {
        userDao.delete(user);
    }




}
