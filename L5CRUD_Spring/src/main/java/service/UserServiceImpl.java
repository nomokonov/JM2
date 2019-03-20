package service;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserRepo;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;


    public User getUserById(long id) {
        return userRepo.findById(id).get();
    }

    @Override
    public List<User> getUsers() {
        return (List<User>) userRepo.findAll();
    }

    @Override
    public void saveUser(User user) {
        userRepo.save(user);
    }

    @Override
    public void userUpdate(User user) {
        userRepo.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepo.delete(user);
    }


}
