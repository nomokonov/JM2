package com.example.test.repository;

import com.example.test.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserDaoHibernateImpl implements UserDao {
    @Autowired
    UserDao userDAO;
    @Autowired
    private SessionFactory sessionFactory;

    public User findById(long id) {
        return sessionFactory.getCurrentSession().get(User.class, id);
    }

    public User findByName(String username) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM User WHERE name = :paramName";
        Query query = session.createQuery(hql);
        query.setParameter("paramName", username);
        List<User> users = query.list();
        if (users.size() == 0) {
            return null;
        } else {
            return users.get(0);
        }
    }

    public void save(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);

    }

    public void update(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.update(user);
    }

    public void delete(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(user);
    }

    public List<User> findAll() {
        List<User> users;
        Session session = sessionFactory.getCurrentSession();
        users = session.createQuery("FROM User").list();
        return users;
    }
}
