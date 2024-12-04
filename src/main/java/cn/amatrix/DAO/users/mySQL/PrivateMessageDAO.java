package cn.amatrix.DAO.users.mySQL;

import cn.amatrix.DAO.DataBaseConnector.MySQLConnector;
import cn.amatrix.DAO.users.Imp.PrivateMessageDAOImp;
import cn.amatrix.model.users.PrivateMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrivateMessageDAO implements PrivateMessageDAOImp {

    public PrivateMessage getPrivateMessageById(int messageId) throws SQLException {
        PrivateMessage message = null;
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "SELECT * FROM private_messages WHERE message_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, messageId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        message = new PrivateMessage();
                        message.setMessageId(resultSet.getInt("message_id"));
                        message.setSenderId(resultSet.getInt("sender_id"));
                        message.setReceiverId(resultSet.getInt("receiver_id"));
                        message.setMessage(resultSet.getString("message"));
                        message.setSentAt(resultSet.getTimestamp("sent_at"));
                    }
                }
            }
        }
        return message;
    }

    public void addPrivateMessage(PrivateMessage message) throws SQLException {
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "INSERT INTO private_messages (sender_id, receiver_id, message, sent_at) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, message.getSenderId());
                statement.setInt(2, message.getReceiverId());
                statement.setString(3, message.getMessage());
                statement.setTimestamp(4, message.getSentAt());
                statement.executeUpdate();
            }
        }
    }

    public void updatePrivateMessage(PrivateMessage message) throws SQLException {
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "UPDATE private_messages SET sender_id = ?, receiver_id = ?, message = ?, sent_at = ? WHERE message_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, message.getSenderId());
                statement.setInt(2, message.getReceiverId());
                statement.setString(3, message.getMessage());
                statement.setTimestamp(4, message.getSentAt());
                statement.setInt(5, message.getMessageId());
                statement.executeUpdate();
            }
        }
    }

    public void deletePrivateMessage(int messageId) throws SQLException {
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "DELETE FROM private_messages WHERE message_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, messageId);
                statement.executeUpdate();
            }
        }
    }

    public List<PrivateMessage> getAllPrivateMessages() throws SQLException {
        List<PrivateMessage> messages = new ArrayList<>();
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "SELECT * FROM private_messages";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    PrivateMessage message = new PrivateMessage();
                    message.setMessageId(resultSet.getInt("message_id"));
                    message.setSenderId(resultSet.getInt("sender_id"));
                    message.setReceiverId(resultSet.getInt("receiver_id"));
                    message.setMessage(resultSet.getString("message"));
                    message.setSentAt(resultSet.getTimestamp("sent_at"));
                    messages.add(message);
                }
            }
        }
        return messages;
    }

    public List<PrivateMessage> getPrivateMessagesBySenderAndReceiver(int senderId, int receiverId) throws SQLException {
        List<PrivateMessage> messages = new ArrayList<>();
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "SELECT * FROM private_messages WHERE sender_id = ? AND receiver_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, senderId);
                statement.setInt(2, receiverId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        PrivateMessage message = new PrivateMessage();
                        message.setMessageId(resultSet.getInt("message_id"));
                        message.setSenderId(resultSet.getInt("sender_id"));
                        message.setReceiverId(resultSet.getInt("receiver_id"));
                        message.setMessage(resultSet.getString("message"));
                        message.setSentAt(resultSet.getTimestamp("sent_at"));
                        messages.add(message);
                    }
                }
            }
        }
        return messages;
    }

    public List<PrivateMessage> getPrivateMessagesBySender(int senderId) throws SQLException {
        List<PrivateMessage> messages = new ArrayList<>();
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "SELECT * FROM private_messages WHERE sender_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, senderId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        PrivateMessage message = new PrivateMessage();
                        message.setMessageId(resultSet.getInt("message_id"));
                        message.setSenderId(resultSet.getInt("sender_id"));
                        message.setReceiverId(resultSet.getInt("receiver_id"));
                        message.setMessage(resultSet.getString("message"));
                        message.setSentAt(resultSet.getTimestamp("sent_at"));
                        messages.add(message);
                    }
                }
            }
        }
        return messages;
    }

    public List<PrivateMessage> getPrivateMessagesByReceiver(int receiverId) throws SQLException {
        List<PrivateMessage> messages = new ArrayList<>();
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "SELECT * FROM private_messages WHERE receiver_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, receiverId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        PrivateMessage message = new PrivateMessage();
                        message.setMessageId(resultSet.getInt("message_id"));
                        message.setSenderId(resultSet.getInt("sender_id"));
                        message.setReceiverId(resultSet.getInt("receiver_id"));
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
