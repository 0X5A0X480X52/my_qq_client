package cn.amatrix.DAO.users.mySQL;

import cn.amatrix.DAO.DataBaseConnector.MySQLConnector;
import cn.amatrix.DAO.users.Imp.UserDAOImp;
import cn.amatrix.model.users.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements UserDAOImp {

    public User getUserById(int userId) throws SQLException {
        User user = null;
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "SELECT * FROM users WHERE user_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, userId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        user = new User();
                        user.setUser_id(resultSet.getInt("user_id"));
                        user.setUsername(resultSet.getString("username"));
                        user.setPassword(resultSet.getString("password"));
                        user.setEmail(resultSet.getString("email"));
                        user.setAvatar(resultSet.getString("avatar"));
                        user.setCreated_at(resultSet.getTimestamp("created_at"));
                        user.setLog_status(resultSet.getString("log_status"));
                        user.setLast_login_at(resultSet.getTimestamp("last_login_at"));
                        user.setLast_logout_at(resultSet.getTimestamp("last_logout_at"));
                    }
                }
            }
        }
        return user;
    }

    public User getUserByUsername(String username) throws SQLException {
        User user = null;
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        user = new User();
                        user.setUser_id(resultSet.getInt("user_id"));
                        user.setUsername(resultSet.getString("username"));
                        user.setPassword(resultSet.getString("password"));
                        user.setEmail(resultSet.getString("email"));
                        user.setAvatar(resultSet.getString("avatar"));
                        user.setCreated_at(resultSet.getTimestamp("created_at"));
                        user.setLog_status(resultSet.getString("log_status"));
                        user.setLast_login_at(resultSet.getTimestamp("last_login_at"));
                        user.setLast_logout_at(resultSet.getTimestamp("last_logout_at"));
                    }
                }
            }
        }
        return user;
    }

    public User getUserByEmail(String email) throws SQLException {
        User user = null;
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "SELECT * FROM users WHERE email = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, email);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        user = new User();
                        user.setUser_id(resultSet.getInt("user_id"));
                        user.setUsername(resultSet.getString("username"));
                        user.setPassword(resultSet.getString("password"));
                        user.setEmail(resultSet.getString("email"));
                        user.setAvatar(resultSet.getString("avatar"));
                        user.setCreated_at(resultSet.getTimestamp("created_at"));
                        user.setLog_status(resultSet.getString("log_status"));
                        user.setLast_login_at(resultSet.getTimestamp("last_login_at"));
                        user.setLast_logout_at(resultSet.getTimestamp("last_logout_at"));
                    }
                }
            }
        }
        return user;
    }

    public void addUser(User user) throws SQLException {
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getPassword());
                statement.setString(3, user.getEmail());
                statement.executeUpdate();
            }
        }
    }

    public void updateUser(User user) throws SQLException {
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "UPDATE users SET username = ?, password = ?, email = ?, avatar = ?, created_at = ?, log_status = ?, last_login_at = ?, last_logout_at = ? WHERE user_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getPassword());
                statement.setString(3, user.getEmail());
                statement.setString(4, user.getAvatar());
                statement.setTimestamp(5, user.getCreated_at());
                statement.setString(6, user.getLog_status());
                statement.setTimestamp(7, user.getLast_login_at());
                statement.setTimestamp(8, user.getLast_logout_at());
                statement.setInt(9, user.getUser_id());
                statement.executeUpdate();
            }
        }
    }

    public void deleteUser(int userId) throws SQLException {
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "DELETE FROM users WHERE user_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, userId);
                statement.executeUpdate();
            }
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "SELECT * FROM users";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setUser_id(resultSet.getInt("user_id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setEmail(resultSet.getString("email"));
                    user.setAvatar(resultSet.getString("avatar"));
                    user.setCreated_at(resultSet.getTimestamp("created_at"));
                    user.setLog_status(resultSet.getString("log_status"));
                    user.setLast_login_at(resultSet.getTimestamp("last_login_at"));
                    user.setLast_logout_at(resultSet.getTimestamp("last_logout_at"));
                    users.add(user);
                }
            }
        }
        return users;
    }
}
