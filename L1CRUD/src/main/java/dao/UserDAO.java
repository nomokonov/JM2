package dao;

import model.User;
import java.util.List;

public interface UserDAO {
    void createTable();
    User findById(long id);
    List<User> findAll();
    void save(User user);
    void update(User user);
    void delete(User user);
}
