package com.example.test.service;

import com.example.test.model.User;
import com.example.test.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        User user = userRepo.findByName(name);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getName(), user.getPassword(), user.getAuthorities()
        );
    }

    public User getUserById(long id) {
         return userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not founnd by id = " + id));
    }

    public User getUserByName(String name) {
        return userRepo.findByName(name);
    }

    public List<User> getUsers() {
        return (List<User>) userRepo.findAll(new Sort(Sort.Direction.ASC, "id"));
    }

    public void saveUser(User user) {
        userRepo.save(user);
    }

    public void deleteUser(User user) {
       userRepo.delete(user);
    }

    public void updateUser(User user) {
        userRepo.save(user);
    }


}
