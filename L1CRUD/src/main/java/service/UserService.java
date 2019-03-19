package service;

import model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
//    void init();   Старое наследие

    List<User> getUsers();

    User getUserById(long id);

    User getUserByName(String username);

    void addUser(User user) throws SQLException;

    void userUpdate(User user) throws SQLException;

    void deleteUser(User user) throws SQLException;
}
