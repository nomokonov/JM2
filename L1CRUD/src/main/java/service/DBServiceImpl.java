package service;

import model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import util.HibernateUtil;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBServiceImpl implements DBService {
    private final Connection connection;
    private static DBServiceImpl dbService = new DBServiceImpl();

    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "create";
    private final SessionFactory sessionFactory;


    public static DBServiceImpl getDBService() {
        return dbService;
    }

    private DBServiceImpl() {
        this.connection = getPostgreSQLConnection();
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private static Connection getPostgreSQLConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("org.postgresql.Driver").newInstance());
            String url = "jdbc:postgresql://localhost/test";
            Properties props = new Properties();
            props.setProperty("user", "postgres");
            props.setProperty("password", "123456");
//            props.setProperty("ssl", "true");
            Connection conn = DriverManager.getConnection(url, props);
            return conn;
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("UnusedDeclaration")
    private static Connection getMysqlConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());
            StringBuilder url = new StringBuilder();
            url.
                    append("jdbc:mysql://").        //db type
                    append("localhost:").           //host name
                    append("3306/").                //port
                    append("db_example?").          //db name
                    append("user=admin&").          //login
                    append("password=admin");       //password
            System.out.println("URL: " + url + "\n");
            Connection connection = DriverManager.getConnection(url.toString());
            return connection;
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
