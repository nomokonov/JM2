package dao;

import executor.Executor;
import model.User;
import service.DBService;
import service.DBServiceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJdbcImpl implements UserDao {
    private static UserDaoJdbcImpl userDAO = new UserDaoJdbcImpl();
    private DBService dbService = DBServiceImpl.getDBService();
    private Connection connection;
    private Executor executor;

    private UserDaoJdbcImpl() {
        this.connection = dbService.getConnection();
        this.executor = new Executor(connection);
    }

    public static UserDaoJdbcImpl getInstance() {
        return userDAO;
    }

    public User findById(long id) {
        try {
            return executor.execQuery("SELECT * FROM users WHERE id=" + id, result -> {
                result.next();
                User user = new User(result.getLong(1), result.getString(2),
                        result.getString(3), result.getString(4));
                user.setRole(result.getString(5));
                return user;
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public User findByName(String username) {
        try {
            return executor.execQuery("SELECT * FROM users WHERE username=" + username, result -> {
                result.next();
                User user = new User(result.getLong(1), result.getString(2),
                        result.getString(3), result.getString(4));
                user.setRole(result.getString(5));
                return user;
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(User user) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(
                             "INSERT INTO users (username, password, description) values(?, ? , ?, ?)")) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getDescription());
            preparedStatement.setString(4, user.getRole());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(User user) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(
                             "UPDATE  USERS  SET (username, password, description) = (?, ?, ?) WHERE id=?")) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getDescription());
            preparedStatement.setLong(4, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(User user) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("DELETE FROM  users   WHERE id=?")) {
            preparedStatement.setLong(1, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable() {
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

    public List<User> findAll() {
        try {
            return executor.execQuery("SELECT * FROM users;", result -> {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
