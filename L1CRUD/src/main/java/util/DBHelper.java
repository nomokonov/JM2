package util;

import model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBHelper {
    private static final DBHelper dbHelper = new DBHelper();
    private SessionFactory sessionFactory;
    private Configuration configuration;
    private Connection connection;

    private DBHelper() {
    }

    public static DBHelper getInstance() {
        return dbHelper;
    }

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                configuration = new Configuration();
                configuration.addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public Configuration getConfiguration() {
        if (configuration == null) {
            try {
                configuration = new Configuration();
                configuration.addAnnotatedClass(User.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return configuration;
    }

    public Connection getConnection() {
        if (connection == null) {
            Properties properties = PropertiesHelper.getInstance().getProperties("jdbc.properties");
            try {
                DriverManager.registerDriver((Driver) Class.forName(properties.getProperty("jdbc.driver_class")).newInstance());
                String url = properties.getProperty("jdbc.url"); //"jdbc:postgresql://localhost/test";
//            props.setProperty("ssl", "true");
                connection = DriverManager.getConnection(url, properties);
                return connection;
            } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
