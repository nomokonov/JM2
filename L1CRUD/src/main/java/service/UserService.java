package service;

import model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    void init();

    List<User> getUsers();

    User getUser(long id);

    void addUser(User user) throws SQLException;

    void userUpdate(User user) throws SQLException;

    void deleteUser(User user) throws SQLException;
}
