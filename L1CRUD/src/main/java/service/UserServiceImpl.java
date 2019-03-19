package service;

import dao.UserDao;
import daofactory.UserDaoFactory;
import daofactory.UserDaoFactoryImpl;
import model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDaoFactory userDaoFactory = new UserDaoFactoryImpl();
    private UserDao userDAO;
//    private UserDao userDAO = UserDaoJdbcImpl.getInstance();
    private static UserServiceImpl userService = new UserServiceImpl();

    private UserServiceImpl() {

        userDAO = userDaoFactory.createUserDaoByProps("userdao.properties");
    }

    public static UserServiceImpl getUserService() {
        return userService;
    }

    public User getUserById(long id) {
        return (userDAO.findById(id));
    }

    @Override
    public User getUserByName(String username) {
        return (userDAO.findByName(username));
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
