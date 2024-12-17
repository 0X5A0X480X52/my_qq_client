package cn.amatrix.DAO.groups.mySQL;

import cn.amatrix.DAO.DataBaseConnector.MySQLConnector;
import cn.amatrix.DAO.groups.Imp.GroupMemberDAOImp;
import cn.amatrix.model.groups.GroupMember;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupMemberDAO implements GroupMemberDAOImp {

    public GroupMember getGroupMemberById(int groupId, int userId) throws SQLException {
        GroupMember member = null;
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "SELECT * FROM group_members WHERE group_id = ? AND user_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, groupId);
                statement.setInt(2, userId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        member = new GroupMember();
                        member.setGroupId(resultSet.getInt("group_id"));
                        member.setUserId(resultSet.getInt("user_id"));
                        member.setPower(resultSet.getString("power"));
                        member.setJoinedAt(resultSet.getTimestamp("joined_at"));
                    }
                }
            }
        }
        return member;
    }

    public void addGroupMember(GroupMember member) throws SQLException {
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "INSERT INTO group_members (group_id, user_id, power, joined_at) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, member.getGroupId());
                statement.setInt(2, member.getUserId());
                statement.setString(3, member.getPower());
                statement.setTimestamp(4, member.getJoinedAt());
                statement.executeUpdate();
            }
        }
    }

    public void updateGroupMember(GroupMember member) throws SQLException {
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "UPDATE group_members SET power = ?, joined_at = ? WHERE group_id = ? AND user_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, member.getPower());
                statement.setTimestamp(2, member.getJoinedAt());
                statement.setInt(3, member.getGroupId());
                statement.setInt(4, member.getUserId());
                statement.executeUpdate();
            }
        }
    }

    public void deleteGroupMember(int groupId, int userId) throws SQLException {
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "DELETE FROM group_members WHERE group_id = ? AND user_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, groupId);
                statement.setInt(2, userId);
                statement.executeUpdate();
            }
        }
    }

    public List<GroupMember> getAllGroupMembers() throws SQLException {
        List<GroupMember> members = new ArrayList<>();
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "SELECT * FROM group_members";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    GroupMember member = new GroupMember();
                    member.setGroupId(resultSet.getInt("group_id"));
                    member.setUserId(resultSet.getInt("user_id"));
                    member.setPower(resultSet.getString("power"));
                    member.setJoinedAt(resultSet.getTimestamp("joined_at"));
                    members.add(member);
                }
            }
        }
        return members;
    }

    public List<GroupMember> getGroupMembersByUserId(int userId) throws SQLException {
        List<GroupMember> members = new ArrayList<>();
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "SELECT * FROM group_members WHERE user_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, userId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        GroupMember member = new GroupMember();
                        member.setGroupId(resultSet.getInt("group_id"));
                        member.setUserId(resultSet.getInt("user_id"));
                        member.setPower(resultSet.getString("power"));
                        member.setJoinedAt(resultSet.getTimestamp("joined_at"));
                        members.add(member);
                    }
                }
            }
        }
        return members;
    }
}
