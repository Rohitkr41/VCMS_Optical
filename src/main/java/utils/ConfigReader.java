//
//package utils;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.Properties;
//
//public class ConfigReader {
//
//    private static Properties properties;
//
//    static {
//        try {
//            FileInputStream fis = new FileInputStream(
//                System.getProperty("user.dir") + "/src/test/resources/config.properties"
//            );
//            properties = new Properties();
//            properties.load(fis);
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to load config.properties file");
//        }
//    }
//
//    public static String getProperty(String key) {
//        return properties.getProperty(key);
//    }
//
//}


package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties = new Properties();

    static {
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties not found in resources folder");
            }
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load config.properties: " + e.getMessage());
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}