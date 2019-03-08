package dbService;

import dao.User;

import java.util.List;

public interface DBService {
     void init();
     List<User> getUsers();
}
