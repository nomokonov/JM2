package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesHelper  {
    private static PropertiesHelper propertiesHelper = new PropertiesHelper();

    private PropertiesHelper(){

    }

    public static PropertiesHelper getInstance(){
        return propertiesHelper;
    }

    public  Properties getProperties(String file){
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
