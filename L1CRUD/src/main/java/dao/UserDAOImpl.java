package dao;

import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import service.DBServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private static UserDAOImpl userDAO = new UserDAOImpl();
    private SessionFactory sessionFactory = DBServiceImpl.getDBService().getSessionFactory();

    private UserDAOImpl() {
    }

    public static UserDAOImpl getUserDAO() {
        return userDAO;
    }

    public User findById(long id) {
        return sessionFactory.openSession().get(User.class, id);
    }

    public void save(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    public void update(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        users = session.createQuery("from User").list();
        session.getTransaction().commit();
        session.close();
        return users;
    }
}
