package cn.amatrix.DAO.groups.mySQL;

import cn.amatrix.model.groups.GroupMember;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class GroupMemberDAOTest {

    @Test
    public void testGetAllGroupMembers() throws SQLException {
        GroupMemberDAO memberDAO = new GroupMemberDAO();
        List<GroupMember> members = memberDAO.getAllGroupMembers();
        assertNotNull(members);
        assertTrue(members.size() > 0);
    }

    @Test
    public void testAddGroupMember() throws SQLException {
        GroupMemberDAO memberDAO = new GroupMemberDAO();
        GroupMember member = new GroupMember();
        member.setGroupId(1);
        member.setUserId(1);
        member.setPower("admin");
        member.setJoinedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        memberDAO.addGroupMember(member);

        GroupMember retrievedMember = memberDAO.getGroupMemberById(member.getGroupId(), member.getUserId());
        assertNotNull(retrievedMember);
        assertEquals("admin", retrievedMember.getPower());
    }

    @Test
    public void testGetGroupMemberById() throws SQLException {
        GroupMemberDAO memberDAO = new GroupMemberDAO();
        GroupMember member = memberDAO.getGroupMemberById(1, 1);
        assertNotNull(member);
        assertEquals(1, member.getGroupId());
        assertEquals(1, member.getUserId());
    }

    @Test
    public void testUpdateGroupMember() throws SQLException {
        GroupMemberDAO memberDAO = new GroupMemberDAO();
        GroupMember member = memberDAO.getGroupMemberById(1, 1);
        member.setPower("member");
        memberDAO.updateGroupMember(member);

        GroupMember updatedMember = memberDAO.getGroupMemberById(1, 1);
        assertEquals("member", updatedMember.getPower());
    }

    @Test
    public void testDeleteGroupMember() throws SQLException {
        GroupMemberDAO memberDAO = new GroupMemberDAO();
        memberDAO.deleteGroupMember(1, 1);

        GroupMember member = memberDAO.getGroupMemberById(1, 1);
        assertNull(member);
    }

    @Test
    public void testGetGroupMembersByGroupId() throws SQLException {
        GroupMemberDAO memberDAO = new GroupMemberDAO();
        List<GroupMember> members = memberDAO.getGroupMembersByGroupId(35);
        assertNotNull(members);
        assertTrue(members.size() > 0);
    }

    @Test
    public void testGetGroupMembersByUserId() throws SQLException {
        GroupMemberDAO memberDAO = new GroupMemberDAO();
        List<GroupMember> members = memberDAO.getGroupMembersByUserId(1);
        assertNotNull(members);
        assertTrue(members.size() > 0);
    }
}
