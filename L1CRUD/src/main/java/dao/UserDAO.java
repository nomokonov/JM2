package dao;

import dbService.executor.Executor;

import javax.jws.soap.SOAPBinding;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static Executor executor;
    private static UserDAO userDAO;

    private UserDAO() {
    }

    public static UserDAO instanceOf() {
        if (userDAO == null) {
            userDAO = new UserDAO();
        }
        return userDAO;
    }

    public static void setConnection(Connection connection) {
        executor = new Executor(connection);
    }

    public static User get(long id) throws SQLException {
        return executor.execQuery("select * from users where id=" + id, result -> {
            result.next();
            return new User(result.getLong(1), result.getString(2),
                    result.getString(3), result.getString(4));
        });
    }

    public static void addUser(User user) throws SQLException{
        String zpt = ",";
        StringBuilder query = new StringBuilder();
        query.append("insert into users (username, password, description) values(");
        query.append("'").append(user.getName()).append("'");
        query.append(zpt);
        query.append("'").append(user.getPassword()).append("'");
        query.append(zpt);
        query.append("'").append(user.getDescription()).append("'");
        query.append(");");
            executor.execUpdate(query.toString());
    }

    public static  void updateUser(User user) throws SQLException {
        String zpt = ",";
        StringBuilder query = new StringBuilder();
        query.append("update  users  set (username, password, description) = (");
        query.append("'").append(user.getName()).append("'");
        query.append(zpt);
        query.append("'").append(user.getPassword()).append("'");
        query.append(zpt);
        query.append("'").append(user.getDescription()).append("'");
        query.append(") where id=").append(user.getId()).append(";");
        executor.execUpdate(query.toString());
    }

    public static  void deleteUser(User user) throws SQLException {
        StringBuilder query = new StringBuilder();
        query.append("delete from  users   where id=")
                .append(user.getId()).append(";");
         executor.execUpdate(query.toString());
    }

    public static void createTable() {

        try {
            executor.execUpdate("CREATE TABLE IF NOT EXISTS users(" +
                    "id    SERIAL PRIMARY KEY," +
                    "username   varchar(40) NOT NULL CHECK (username <> '')," +
                    "password   varchar(40)," +
                    "description   varchar(255) " +
                    "); ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> findAll() throws SQLException {
        return executor.execQuery("select * from users;", result -> {
            List<User> list = new ArrayList<>();
            while (result.next()) {

                list.add(new User(result.getLong(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4)
                ));
            }
            return list;
        });

    }
}
