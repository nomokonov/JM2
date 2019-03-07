package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {
//    private dbService.executor.Executor executor;
    Connection connection;

    public UserDAO(Connection connection) {
//        this.executor = new dbService.executor.Executor(connection);
        this.connection = connection;
    }

    public User get(long id) throws SQLException {
        String query = "Se"
        Statement statement = connection.createStatement();
        statement.execute(query);
//        return executor.execQuery("select * from users where id=" + id, result -> {
//            result.next();
//            return new User(result.getLong(1), result.getString(2));
        });
    }


}
