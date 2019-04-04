package com.example.test.repository;

import com.example.test.model.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
//@Transactional
public class RoleDaoImpl implements RoleDao {
    private SessionFactory sessionFactory;

    @Autowired
    public RoleDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Role findById(Long id) {
        return sessionFactory.getCurrentSession().get(Role.class, id);
    }

    @Override
    public Role findByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Role WHERE name = :paramName";
        Query query = session.createQuery(hql);
        query.setParameter("paramName", name);
        List<Role> roles = query.list();
        if (roles.size() == 0) {
            return null;
        } else {
            return roles.get(0);
        }
    }

    @Override
    public List<Role> findAll() {
        List<Role> roles;
        Session session = sessionFactory.getCurrentSession();
        roles = session.createQuery("FROM Role").list();
        return roles;
    }
}
