package cn.amatrix.DAO.users.mySQL;

import cn.amatrix.DAO.DataBaseConnector.MySQLConnector;
import cn.amatrix.DAO.users.Imp.FriendRequestDAOImp;
import cn.amatrix.model.users.FriendRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendRequestDAO implements FriendRequestDAOImp {

    public FriendRequest getFriendRequestById(int requestId) throws SQLException {
        FriendRequest request = null;
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "SELECT * FROM friend_requests WHERE request_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, requestId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        request = new FriendRequest();
                        request.setRequestId(resultSet.getInt("request_id"));
                        request.setSenderId(resultSet.getInt("sender_id"));
                        request.setReceiverId(resultSet.getInt("receiver_id"));
                        request.setRequestMessage(resultSet.getString("request_message"));
                        request.setRequestStatus(resultSet.getString("request_status"));
                        request.setRequestedAt(resultSet.getTimestamp("requested_at"));
                    }
                }
            }
        }
        return request;
    }

    public void addFriendRequest(FriendRequest request) throws SQLException {
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "INSERT INTO friend_requests (sender_id, receiver_id, request_message, request_status, requested_at) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, request.getSenderId());
                statement.setInt(2, request.getReceiverId());
                statement.setString(3, request.getRequestMessage());
                statement.setString(4, request.getRequestStatus());
                statement.setTimestamp(5, request.getRequestedAt());
                statement.executeUpdate();
            }
        }
    }

    public void updateFriendRequest(FriendRequest request) throws SQLException {
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "UPDATE friend_requests SET sender_id = ?, receiver_id = ?, request_message = ?, request_status = ?, requested_at = ? WHERE request_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, request.getSenderId());
                statement.setInt(2, request.getReceiverId());
                statement.setString(3, request.getRequestMessage());
                statement.setString(4, request.getRequestStatus());
                statement.setTimestamp(5, request.getRequestedAt());
                statement.setInt(6, request.getRequestId());
                statement.executeUpdate();
            }
        }
    }

    public void deleteFriendRequest(int requestId) throws SQLException {
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "DELETE FROM friend_requests WHERE request_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, requestId);
                statement.executeUpdate();
            }
        }
    }

    public List<FriendRequest> getAllFriendRequests() throws SQLException {
        List<FriendRequest> requests = new ArrayList<>();
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "SELECT * FROM friend_requests";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    FriendRequest request = new FriendRequest();
                    request.setRequestId(resultSet.getInt("request_id"));
                    request.setSenderId(resultSet.getInt("sender_id"));
                    request.setReceiverId(resultSet.getInt("receiver_id"));
                    request.setRequestMessage(resultSet.getString("request_message"));
                    request.setRequestStatus(resultSet.getString("request_status"));
                    request.setRequestedAt(resultSet.getTimestamp("requested_at"));
                    requests.add(request);
                }
            }
        }
        return requests;
    }

    public List<FriendRequest> getFriendRequestsBySender(int senderId) throws SQLException {
        List<FriendRequest> requests = new ArrayList<>();
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "SELECT * FROM friend_requests WHERE sender_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, senderId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        FriendRequest request = new FriendRequest();
                        request.setRequestId(resultSet.getInt("request_id"));
                        request.setSenderId(resultSet.getInt("sender_id"));
                        request.setReceiverId(resultSet.getInt("receiver_id"));
                        request.setRequestMessage(resultSet.getString("request_message"));
                        request.setRequestStatus(resultSet.getString("request_status"));
                        request.setRequestedAt(resultSet.getTimestamp("requested_at"));
                        requests.add(request);
                    }
                }
            }
        }
        return requests;
    }

    public List<FriendRequest> getFriendRequestsByReceiver(int receiverId) throws SQLException {
        List<FriendRequest> requests = new ArrayList<>();
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "SELECT * FROM friend_requests WHERE receiver_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, receiverId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        FriendRequest request = new FriendRequest();
                        request.setRequestId(resultSet.getInt("request_id"));
                        request.setSenderId(resultSet.getInt("sender_id"));
                        request.setReceiverId(resultSet.getInt("receiver_id"));
                        request.setRequestMessage(resultSet.getString("request_message"));
                        request.setRequestStatus(resultSet.getString("request_status"));
                        request.setRequestedAt(resultSet.getTimestamp("requested_at"));
                        requests.add(request);
                    }
                }
            }
        }
        return requests;
    }
}
