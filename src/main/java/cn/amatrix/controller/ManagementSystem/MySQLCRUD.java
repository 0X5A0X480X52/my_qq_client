package cn.amatrix.controller.ManagementSystem;

import java.sql.*;

public class MySQLCRUD {

    // MySQL数据库驱动
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    // 数据库连接URL
    private static final String URL = "jdbc:mysql://localhost:3306/bookManageMentSystem?serverTimezone=UTC";
    // 数据库用户名
    private static final String USER = "visitor"; // 替换为你的数据库用户名
    // 数据库密码
    private static final String PASSWORD = "123456"; // 替换为你的数据库密码
    Connection connection;

    public static void main(String[] args) {
        try {
            // 加载MySQL数据库驱动
            Class.forName(DRIVER);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("未能成功加载数据库驱动程序！");
        }

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // 创建用户
            createUser(connection, "John Doe", "john@example.com");
            // 读取用户
            readUsers(connection);
            // 更新用户
            updateUser(connection, 1, "John Smith", "johnsmith@example.com");
            // 删除用户
            deleteUser(connection, 1);
            System.out.println(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 构造函数，初始化数据库连接
    public MySQLCRUD() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 获取数据库连接
    public Connection getConnection() {
        return connection;
    }

    // 创建用户
    public static void createUser(Connection connection, String name, String email) {
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
            System.out.println("User created: " + name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 读取用户
    public static void readUsers(Connection connection) {
        String sql = "SELECT * FROM users";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 更新用户
    public static void updateUser(Connection connection, int id, String name, String email) {
        String sql = "UPDATE users SET name = ?, email = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
            System.out.println("User updated: " + name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除用户
    public static void deleteUser(Connection connection, int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("User deleted with ID: " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
