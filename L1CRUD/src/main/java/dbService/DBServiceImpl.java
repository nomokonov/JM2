package dbService;

import dao.User;
import dao.UserDAO;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class DBServiceImpl implements DBService {
    private final Connection connection;

    @Override
    public void init() {
        UserDAO.createTable();  // Дабы было с чем работать
//        UserDAO.addUser(new User("User1","Pass1"));
//        UserDAO.addUser(new User("User2","Pass2"));
//        UserDAO.addUser(new User("User3","Pass3"));

    }

    public DBServiceImpl() {
        this.connection = getPostgreSQLConnection();
        UserDAO.instanceOf().setConnection(this.connection);
    }

    public User getUser(long id) throws SQLException {
        try {
            return (UserDAO.instanceOf().get(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getUsers() {
        try {
            return UserDAO.instanceOf().findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getPostgreSQLConnection() {
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
    public static Connection getMysqlConnection() {
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

//    public static Connection getH2Connection() {
//        try {
//            String url = "jdbc:h2:./h2db";
//            String name = "admin";
//            String pass = "admin";
//
//            JdbcDataSource ds = new JdbcDataSource();
//            ds.setURL(url);
//            ds.setUser(name);
//            ds.setPassword(pass);
//
//            Connection connection = DriverManager.getConnection(url, name, pass);
//            return connection;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


}
