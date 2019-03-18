package daofactory;

import dao.UserDao;

public interface UserDaoFactory {
    UserDao createUserDaoByProps(String file);
//    UserDao createUserDaoHibernate();
//    UserDao createUserDaoJdbc();
}
