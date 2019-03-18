package daofactory;

import dao.UserDao;
import dao.UserDaoHibernateImpl;
import dao.UserDaoJdbcImpl;
import util.PropertiesHelper;

import java.util.Properties;

public class UserDaoFactoryImpl implements UserDaoFactory{

    private UserDao createUserDaoHibernate() {
        return UserDaoHibernateImpl.getInstance();
    }

    private UserDao createUserDaoJdbc() {
        return UserDaoJdbcImpl.getInstance();
    }

    @Override
    public UserDao createUserDaoByProps(String file){
        Properties properties = PropertiesHelper.getInstance().getProperties(file);
        switch (properties.getProperty("user.dao")){
            case "UserDaoHibernateImpl":
                return createUserDaoHibernate();
            case "UserDaoJdbcImpl":
                return createUserDaoJdbc();
            default:
                return createUserDaoHibernate();
        }
    }

}
