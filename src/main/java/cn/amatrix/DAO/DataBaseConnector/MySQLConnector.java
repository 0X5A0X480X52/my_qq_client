package cn.amatrix.DAO.DataBaseConnector;

import cn.amatrix.utils.configManager.managers.DataBaseConfigManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector {

    private static final String DRIVER = DataBaseConfigManager.getDriver();
    private static final String URL = DataBaseConfigManager.getUrl();
    private static final String USER = DataBaseConfigManager.getUser();
    private static final String PASSWORD = DataBaseConfigManager.getPassword();

    public static Connection getConnection() throws SQLException {

        try {
            // 加载MySQL数据库驱动
            Class.forName(DRIVER);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("未能成功加载数据库驱动程序！");
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try {
            MySQLConnector.getConnection();
            System.out.println("数据库连接成功！");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库连接失败！");
        }
    }
}
