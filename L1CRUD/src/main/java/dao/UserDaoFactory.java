package dao;

public interface UserDaoFactory {
    UserDao createUserDaoByProps(String file);
//    UserDao createUserDaoHibernate();
//    UserDao createUserDaoJdbc();
}
