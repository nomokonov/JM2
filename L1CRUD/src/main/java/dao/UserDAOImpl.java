package dao;

import executor.Executor;
import model.User;
import service.DBService;
import service.DBServiceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements   UserDAO {
    private DBService dbService = DBServiceImpl.getDBService();
    private  Connection connection;
    private  Executor executor;

    public UserDAOImpl() {
        this.connection = dbService.getConnection();
        this.executor = new Executor(connection);
    }

    public User findById(long id)  {
        try {
            return executor.execQuery("select * from users where id=" + id, result -> {
                result.next();
                return new User(result.getLong(1), result.getString(2),
                        result.getString(3), result.getString(4));
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(User user)  {
        String zpt = ",";
        StringBuilder query = new StringBuilder();
        query.append("insert into users (username, password, description) values(");
        query.append("'").append(user.getName()).append("'");
        query.append(zpt);
        query.append("'").append(user.getPassword()).append("'");
        query.append(zpt);
        query.append("'").append(user.getDescription()).append("'");
        query.append(");");
        try {
            executor.execUpdate(query.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  void update(User user)  {
        String zpt = ",";
        StringBuilder query = new StringBuilder();
        query.append("update  users  set (username, password, description) = (");
        query.append("'").append(user.getName()).append("'");
        query.append(zpt);
        query.append("'").append(user.getPassword()).append("'");
        query.append(zpt);
        query.append("'").append(user.getDescription()).append("'");
        query.append(") where id=").append(user.getId()).append(";");
        try {
            executor.execUpdate(query.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  void delete(User user)  {
        StringBuilder query = new StringBuilder();
        query.append("delete from  users   where id=")
                .append(user.getId()).append(";");
        try {
            executor.execUpdate(query.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  void createTable() {

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

    public List<User> findAll()  {
        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
