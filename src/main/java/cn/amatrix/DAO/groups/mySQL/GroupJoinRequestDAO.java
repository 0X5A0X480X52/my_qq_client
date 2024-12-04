package cn.amatrix.DAO.groups.mySQL;

import cn.amatrix.DAO.DataBaseConnector.MySQLConnector;
import cn.amatrix.DAO.groups.Imp.GroupJoinRequestDAOImp;
import cn.amatrix.model.groups.GroupJoinRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupJoinRequestDAO implements GroupJoinRequestDAOImp {

    public void addGroupJoinRequest(GroupJoinRequest request) throws SQLException {
        String sql = "INSERT INTO group_join_requests (group_id, user_id, request_message, request_status, requested_at) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, request.getGroupId());
            stmt.setInt(2, request.getUserId());
            stmt.setString(3, request.getRequestMessage());
            stmt.setString(4, request.getRequestStatus());
            stmt.setTimestamp(5, request.getRequestedAt());
            stmt.executeUpdate();
        }
    }

    public List<GroupJoinRequest> getGroupJoinRequestsByGroupId(int groupId) throws SQLException {
        String sql = "SELECT * FROM group_join_requests WHERE group_id = ?";
        List<GroupJoinRequest> requests = new ArrayList<>();
        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, groupId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                GroupJoinRequest request = new GroupJoinRequest();
                request.setRequestId(rs.getInt("request_id"));
                request.setGroupId(rs.getInt("group_id"));
                request.setUserId(rs.getInt("user_id"));
                request.setRequestMessage(rs.getString("request_message"));
                request.setRequestStatus(rs.getString("request_status"));
                request.setRequestedAt(rs.getTimestamp("requested_at"));
                requests.add(request);
            }
        }
        return requests;
    }

    public List<GroupJoinRequest> getGroupJoinRequestsByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM group_join_requests WHERE user_id = ?";
        List<GroupJoinRequest> requests = new ArrayList<>();
        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                GroupJoinRequest request = new GroupJoinRequest();
                request.setRequestId(rs.getInt("request_id"));
                request.setGroupId(rs.getInt("group_id"));
                request.setUserId(rs.getInt("user_id"));
                request.setRequestMessage(rs.getString("request_message"));
                request.setRequestStatus(rs.getString("request_status"));
                request.setRequestedAt(rs.getTimestamp("requested_at"));
                requests.add(request);
            }
        }
        return requests;
    }

    public List<GroupJoinRequest> getAllGroupJoinRequests() throws SQLException {
        String sql = "SELECT * FROM group_join_requests";
        List<GroupJoinRequest> requests = new ArrayList<>();
        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                GroupJoinRequest request = new GroupJoinRequest();
                request.setRequestId(rs.getInt("request_id"));
                request.setGroupId(rs.getInt("group_id"));
                request.setUserId(rs.getInt("user_id"));
                request.setRequestMessage(rs.getString("request_message"));
                request.setRequestStatus(rs.getString("request_status"));
                request.setRequestedAt(rs.getTimestamp("requested_at"));
                requests.add(request);
            }
        }
        return requests;
    }

    public void deleteGroupJoinRequestById(int requestId) throws SQLException {
        String sql = "DELETE FROM group_join_requests WHERE request_id = ?";
        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, requestId);
            stmt.executeUpdate();
        }
    }

    public void updateGroupJoinRequest(GroupJoinRequest request) throws SQLException {
        String sql = "UPDATE group_join_requests SET group_id = ?, user_id = ?, request_message = ?, request_status = ?, requested_at = ? WHERE request_id = ?";
        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, request.getGroupId());
            stmt.setInt(2, request.getUserId());
            stmt.setString(3, request.getRequestMessage());
            stmt.setString(4, request.getRequestStatus());
            stmt.setTimestamp(5, request.getRequestedAt());
            stmt.setInt(6, request.getRequestId());
            stmt.executeUpdate();
        }
    }

    public GroupJoinRequest getGroupJoinRequestById(int requestId) throws SQLException {
        GroupJoinRequest request = null;
        String sql = "SELECT * FROM group_join_requests WHERE request_id = ?";
        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, requestId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    request = new GroupJoinRequest();
                    request.setRequestId(rs.getInt("request_id"));
                    request.setGroupId(rs.getInt("group_id"));
                    request.setUserId(rs.getInt("user_id"));
                    request.setRequestMessage(rs.getString("request_message"));
                    request.setRequestStatus(rs.getString("request_status"));
                    request.setRequestedAt(rs.getTimestamp("requested_at"));
                }
            }
        }
        return request;
    }

}
