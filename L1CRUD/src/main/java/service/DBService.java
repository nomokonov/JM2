package service;

import org.hibernate.SessionFactory;

import java.sql.Connection;

public interface DBService {
    Connection getConnection();
    SessionFactory getSessionFactory();
}
