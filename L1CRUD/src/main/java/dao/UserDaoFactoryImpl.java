package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class UserDaoFactoryImpl implements UserDaoFactory{
    @Override
    public UserDao createUserDaoHibernate() {
        return UserDaoHibernateImpl.getInstance();
    }

    @Override
    public UserDao createUserDaoJdbc() {
        return UserDaoJdbcImpl.getInstance();
    }

    @Override
    public UserDao createUserDaoByProps(String file){

        Properties properties = getProperties(file);

        ;
        switch (properties.getProperty("user.dao")){
            case "UserDaoHibernateImpl":
                return createUserDaoHibernate();
            case "UserDaoJdbcImpl":
                return createUserDaoJdbc();
            default:
                return createUserDaoHibernate();
        }
    }

    private Properties getProperties(String file){
        ClassLoader classLoader = this.getClass().getClassLoader();
        File configFile = new File(classLoader.getResource(file).getFile());
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(configFile);
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

}
