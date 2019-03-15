package dao;

import executor.Executor;
import model.User;
import org.hibernate.Session;
import service.DBService;
import service.DBServiceImpl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private static UserDAOImpl userDAO = new UserDAOImpl();
    private DBService dbService = DBServiceImpl.getDBService();
    private Connection connection;
    private Executor executor;

    private UserDAOImpl() {
        this.connection = dbService.getConnection();
        this.executor = new Executor(connection);
    }

    public static UserDAOImpl getUserDAO() {
        return userDAO;
    }

    public User findById(long id) {
        return (User) dbService.getSessionFactory().openSession().get(User.class, id);
    }

    public void save(User user) {
        Session session = dbService.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    public void update(User user) {
        Session session = dbService.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(User user) {
        Session session = dbService.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(user);
        session.getTransaction().commit();
        session.close();

    }

    public void createTable() {

    }

    public List<User> findAll() {
//       return dbService.getSessionFactory().getCurrentSession().createQuery("from User").list();
        List<User> users = new ArrayList<>();
        Session session = dbService.getSessionFactory().openSession();
        session.beginTransaction();
        users = session.createQuery("from User").list();
        session.getTransaction().commit();
        session.close();
        return users;
    }
}
