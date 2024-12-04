package cn.amatrix.DAO.users.mySQL;

import cn.amatrix.DAO.DataBaseConnector.MySQLConnector;
import cn.amatrix.DAO.users.Imp.FriendDAOImp;
import cn.amatrix.model.users.Friend;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendDAO implements FriendDAOImp {

    public Friend getFriendById(int userId, int friendId) throws SQLException {
        Friend friend = null;
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "SELECT * FROM friends WHERE user_id = ? AND friend_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, userId);
                statement.setInt(2, friendId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        friend = new Friend();
                        friend.setUserId(resultSet.getInt("user_id"));
                        friend.setFriendId(resultSet.getInt("friend_id"));
                        friend.setAddedAt(resultSet.getTimestamp("added_at"));
                    }
                }
            }
        }
        return friend;
    }

    public void addFriend(Friend friend) throws SQLException {
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "INSERT INTO friends (user_id, friend_id, added_at) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, friend.getUserId());
                statement.setInt(2, friend.getFriendId());
                statement.setTimestamp(3, friend.getAddedAt());
                statement.executeUpdate();
            }
        }
    }

    public void updateFriend(Friend friend) throws SQLException {
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "UPDATE friends SET added_at = ? WHERE user_id = ? AND friend_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setTimestamp(1, friend.getAddedAt());
                statement.setInt(2, friend.getUserId());
                statement.setInt(3, friend.getFriendId());
                statement.executeUpdate();
            }
        }
    }

    public void deleteFriend(int userId, int friendId) throws SQLException {
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, userId);
                statement.setInt(2, friendId);
                statement.executeUpdate();
            }
        }
    }

    public List<Friend> getAllFriends() throws SQLException {
        List<Friend> friends = new ArrayList<>();
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "SELECT * FROM friends";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Friend friend = new Friend();
                    friend.setUserId(resultSet.getInt("user_id"));
                    friend.setFriendId(resultSet.getInt("friend_id"));
                    friend.setAddedAt(resultSet.getTimestamp("added_at"));
                    friends.add(friend);
                }
            }
        }
        return friends;
    }

    public List<Friend> getFriendsByUserId(int userId) throws SQLException {
        List<Friend> friends = new ArrayList<>();
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "SELECT * FROM friends WHERE user_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, userId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Friend friend = new Friend();
                        friend.setUserId(resultSet.getInt("user_id"));
                        friend.setFriendId(resultSet.getInt("friend_id"));
                        friend.setAddedAt(resultSet.getTimestamp("added_at"));
                        friends.add(friend);
                    }
                }
            }
        }
        return friends;
    }
}
