package com.example.test.service;

import com.example.test.model.Role;
import com.example.test.model.User;
import com.example.test.model.UserRole;
import com.example.test.repository.RoleDao;
import com.example.test.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = userDao.findByName(s);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getName(),user.getPassword(),user.getAuthorities()
        );
    }

    public User getUserById(long id) {
        return userDao.findById(id);
    }

    public User getUserByName(String name) {
        return userDao.findByName(name);
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

    public void updateUser(User user) {
        userDao.update(user);
    }


}
