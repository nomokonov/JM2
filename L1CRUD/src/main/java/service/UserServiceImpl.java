package service;

import dao.UserDAO;
import dao.UserDAOImpl;
import model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDAO userDAO = UserDAOImpl.getUserDAO();
    private static UserServiceImpl userService = new UserServiceImpl();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getUserService() {
        return userService;
    }

    @Override
    public void init() {
        userDAO.createTable();  // Дабы было с чем работать
//        UserDAOImpl.save(new User("User1","Pass1"));
//        UserDAOImpl.save(new User("User2","Pass2"));
//        UserDAOImpl.save(new User("User3","Pass3"));
    }

    public User getUser(long id) {
        return (userDAO.findById(id));
    }

    @Override
    public List<User> getUsers() {
        return userDAO.findAll();
    }

    @Override
    public void addUser(User user) {
        userDAO.save(user);
    }

    @Override
    public void userUpdate(User user) {
        userDAO.update(user);
    }

    @Override
    public void deleteUser(User user) {
        userDAO.delete(user);
    }


}
