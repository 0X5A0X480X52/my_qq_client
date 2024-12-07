package cn.amatrix.DAO.groups.mySQL;

import cn.amatrix.DAO.DataBaseConnector.MySQLConnector;
import cn.amatrix.DAO.groups.Imp.GroupDAOImp;
import cn.amatrix.model.groups.Group;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public class GroupDAO implements GroupDAOImp {

    public Group getGroupById(int groupId) throws SQLException {
        Group group = null;
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "SELECT * FROM user_groups WHERE group_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, groupId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        group = new Group();
                        group.setGroupId(resultSet.getInt("group_id"));
                        group.setGroupName(resultSet.getString("group_name"));
                        group.setAvatar(resultSet.getString("avatar"));
                        group.setCreatedAt(resultSet.getTimestamp("created_at"));
                    }
                }
            }
        }
        return group;
    }

    public Group getGroupByName(String groupName) throws SQLException {
        Group group = null;
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "SELECT * FROM user_groups WHERE group_name = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, groupName);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        group = new Group();
                        group.setGroupId(resultSet.getInt("group_id"));
                        group.setGroupName(resultSet.getString("group_name"));
                        group.setAvatar(resultSet.getString("avatar"));
                        group.setCreatedAt(resultSet.getTimestamp("created_at"));
                    }
                }
            }
        }
        return group;
    }

    public void addGroup(Group group) throws SQLException {
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "INSERT INTO user_groups (group_name, created_at) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, group.getGroupName());
                statement.setTimestamp(2, group.getCreatedAt());
                statement.executeUpdate();
            }
        }
    }

    public void updateGroup(Group group) throws SQLException {
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "UPDATE user_groups SET group_name = ?, created_at = ? avatar = ? WHERE group_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, group.getGroupName());
                statement.setTimestamp(2, group.getCreatedAt());
                statement.setString(3, group.getAvatar());
                statement.setInt(4, group.getGroupId());
                statement.executeUpdate();
            }
        }
    }

    public void deleteGroup(int groupId) throws SQLException {
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "DELETE FROM user_groups WHERE group_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, groupId);
                statement.executeUpdate();
            }
        }
    }

    public List<Group> getAllGroups() throws SQLException {
        List<Group> groups = new ArrayList<>();
        try (Connection connection = MySQLConnector.getConnection()) {
            String sql = "SELECT * FROM user_groups";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Group group = new Group();
                    group.setGroupId(resultSet.getInt("group_id"));
                    group.setGroupName(resultSet.getString("group_name"));
                    group.setAvatar(resultSet.getString("avatar"));
                    group.setCreatedAt(resultSet.getTimestamp("created_at"));
                    groups.add(group);
                }
            }
        }
        return groups;
    }
}
