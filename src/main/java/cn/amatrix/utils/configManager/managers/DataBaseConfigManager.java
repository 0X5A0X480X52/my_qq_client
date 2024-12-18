package cn.amatrix.utils.configManager.managers;

import cn.amatrix.utils.configManager.ConfigManager;

public class DataBaseConfigManager {
    private static final String DEFALT_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DEFALT_URL = "jdbc:mysql://localhost:8080/onlineChatApp?serverTimezone=UTC";
    private static final String DEFALT_USER = "root";
    private static final String DEFALT_PASSWORD = "ZHRhenry20050305";

    static public String getDriver() {
        String driver = ConfigManager.getProperty("db.driver");
        if (driver == null) {
            System.out.println("db.driver is null");
            driver = DEFALT_DRIVER;
            System.out.println("db.driver is set to default: " + driver);
        }
        return driver;
    }

    static public String getUrl() {
        String url = ConfigManager.getProperty("db.url");
        if (url == null) {
            System.out.println("db.url is null");
            url = DEFALT_URL;
            System.out.println("db.url is set to default: " + url);
        }
        return url;
    }

    static public String getUser() {
        String user = ConfigManager.getProperty("db.user");
        if (user == null) {
            System.out.println("db.user is null");
            user = DEFALT_USER;
            System.out.println("db.user is set to default: " + user);
        }
        return user;
    }

    static public String getPassword() {
        String password = ConfigManager.getProperty("db.password");
        if (password == null) {
            System.out.println("db.password is null");
            password = DEFALT_PASSWORD;
            System.out.println("db.password is set to default: " + password);
        }
        return password;
    }

}
