package cn.amatrix.DAO.DataBaseConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    // private static final String URL = "jdbc:mysql://localhost:3306/onlineChatApp?serverTimezone=UTC";
    private static final String URL = "jdbc:mysql://47.97.117.157:8080/onlineChatApp?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "ZHRhenry20050305";

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
