package cn.amatrix.DAO.groups.mySQL;

import cn.amatrix.DAO.DataBaseConnector.MySQLConnector;
import cn.amatrix.model.groups.GroupMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupMessageDAO {
    public GroupMessage getGroupMessageById(int messageId) throws SQLException {
        GroupMessage message = null;
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "SELECT * FROM group_messages WHERE message_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, messageId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        message = new GroupMessage();
                        message.setMessageId(resultSet.getInt("message_id"));
                        message.setGroupId(resultSet.getInt("group_id"));
                        message.setSenderId(resultSet.getInt("sender_id"));
                        message.setMessage(resultSet.getString("message"));
                        message.setSentAt(resultSet.getTimestamp("sent_at"));
                    }
                }
            }
        }
        return message;
    }

    public void addGroupMessage(GroupMessage message) throws SQLException {
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "INSERT INTO group_messages (group_id, sender_id, message, sent_at) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, message.getGroupId());
                statement.setInt(2, message.getSenderId());
                statement.setString(3, message.getMessage());
                statement.setTimestamp(4, message.getSentAt());
                statement.executeUpdate();
            }
        }
    }

    public void updateGroupMessage(GroupMessage message) throws SQLException {
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "UPDATE group_messages SET group_id = ?, sender_id = ?, message = ?, sent_at = ? WHERE message_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, message.getGroupId());
                statement.setInt(2, message.getSenderId());
                statement.setString(3, message.getMessage());
                statement.setTimestamp(4, message.getSentAt());
                statement.setInt(5, message.getMessageId());
                statement.executeUpdate();
            }
        }
    }

    public void deleteGroupMessage(int messageId) throws SQLException {
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "DELETE FROM group_messages WHERE message_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, messageId);
                statement.executeUpdate();
            }
        }
    }

    public List<GroupMessage> getAllGroupMessages() throws SQLException {
        List<GroupMessage> messages = new ArrayList<>();
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "SELECT * FROM group_messages";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    GroupMessage message = new GroupMessage();
                    message.setMessageId(resultSet.getInt("message_id"));
                    message.setGroupId(resultSet.getInt("group_id"));
                    message.setSenderId(resultSet.getInt("sender_id"));
                    message.setMessage(resultSet.getString("message"));
                    message.setSentAt(resultSet.getTimestamp("sent_at"));
                    messages.add(message);
                }
            }
        }
        return messages;
    }

    public List<GroupMessage> getGroupMessagesBySenderId(int senderId) throws SQLException {
        List<GroupMessage> messages = new ArrayList<>();
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "SELECT * FROM group_messages WHERE sender_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, senderId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        GroupMessage message = new GroupMessage();
                        message.setMessageId(resultSet.getInt("message_id"));
                        message.setGroupId(resultSet.getInt("group_id"));
                        message.setSenderId(resultSet.getInt("sender_id"));
                        message.setMessage(resultSet.getString("message"));
                        message.setSentAt(resultSet.getTimestamp("sent_at"));
                        messages.add(message);
                    }
                }
            }
        }
        return messages;
    }

    public List<GroupMessage> getGroupMessagesByGroupId(int groupId) throws SQLException {
        List<GroupMessage> messages = new ArrayList<>();
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "SELECT * FROM group_messages WHERE group_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, groupId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        GroupMessage message = new GroupMessage();
                        message.setMessageId(resultSet.getInt("message_id"));
                        message.setGroupId(resultSet.getInt("group_id"));
                        message.setSenderId(resultSet.getInt("sender_id"));
                        message.setMessage(resultSet.getString("message"));
                        message.setSentAt(resultSet.getTimestamp("sent_at"));
                        messages.add(message);
                    }
                }
            }
        }
        return messages;
    }

    public List<GroupMessage> getGroupMessagesBySenderIdAndGroupId(int senderId, int groupId) throws SQLException {
        List<GroupMessage> messages = new ArrayList<>();
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "SELECT * FROM group_messages WHERE sender_id = ? AND group_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, senderId);
                statement.setInt(2, groupId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        GroupMessage message = new GroupMessage();
                        message.setMessageId(resultSet.getInt("message_id"));
                        message.setGroupId(resultSet.getInt("group_id"));
                        message.setSenderId(resultSet.getInt("sender_id"));
                        message.setMessage(resultSet.getString("message"));
                        message.setSentAt(resultSet.getTimestamp("sent_at"));
                        messages.add(message);
                    }
                }
            }
        }
        return messages;
    }
}
