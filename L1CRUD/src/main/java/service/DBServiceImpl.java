package service;

import org.hibernate.SessionFactory;
import util.DBHelper;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBServiceImpl implements DBService {
    private final Connection connection;
    private static DBServiceImpl dbService = new DBServiceImpl();
    private final SessionFactory sessionFactory = DBHelper.getInstance().getSessionFactory();

    public static DBServiceImpl getDBService() {
        return dbService;
    }

    private DBServiceImpl() {
        this.connection = getPostgreSQLConnection();
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
      return DBHelper.getInstance().getConnection();
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
