package com.example.test.repository;

import com.example.test.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoHibernateImpl implements UserDao {
    @Autowired
    UserDao userDAO;
    @Autowired
    private SessionFactory sessionFactory;

    public User findById(long id) {
        return sessionFactory.openSession().get(User.class, id);
    }

    public User findByName(String username) {
        Session session = sessionFactory.openSession();
        String hql = "FROM User where name = :paramName";
        Query query = session.createQuery(hql);
        query.setParameter("paramName", username);
        List<User> users = query.list();
        session.close();
        if (users.size() == 0) {
            return null;
        } else {
            return users.get(0);
        }

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
        List<User> users;
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        users = session.createQuery("from User").list();
        session.getTransaction().commit();
        session.close();
        return users;
    }
}
