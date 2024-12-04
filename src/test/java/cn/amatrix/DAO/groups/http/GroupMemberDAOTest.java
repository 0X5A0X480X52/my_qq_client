package cn.amatrix.DAO.groups.http;

import cn.amatrix.model.groups.GroupMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class GroupMemberDAOTest {
    private GroupMemberDAO groupMemberDAO;

    @BeforeEach
    public void setUp() {
        groupMemberDAO = new GroupMemberDAO();
    }

    @Test
    public void testAddAndGetGroupMember() throws Exception {
        GroupMember member = new GroupMember();
        member.setGroupId(1);
        member.setUserId(1);
        member.setJoinedAt(new Timestamp(System.currentTimeMillis()));
        groupMemberDAO.addGroupMember(member);

        GroupMember retrievedMember = groupMemberDAO.getGroupMemberById(1, 1);
        assertNotNull(retrievedMember);
        assertEquals(1, retrievedMember.getGroupId());
        assertEquals(1, retrievedMember.getUserId());
    }

    @Test
    public void testUpdateGroupMember() throws Exception {
        GroupMember member = new GroupMember();
        member.setGroupId(1);
        member.setUserId(1);
        member.setJoinedAt(new Timestamp(System.currentTimeMillis()));
        groupMemberDAO.addGroupMember(member);

        GroupMember retrievedMember = groupMemberDAO.getGroupMemberById(1, 1);
        retrievedMember.setPower("Admin");
        groupMemberDAO.updateGroupMember(retrievedMember);

        GroupMember updatedMember = groupMemberDAO.getGroupMemberById(1, 1);
        assertNotNull(updatedMember);
        assertEquals("Admin", updatedMember.getPower());
    }

    @Test
    public void testDeleteGroupMember() throws Exception {
        GroupMember member = new GroupMember();
        member.setGroupId(1);
        member.setUserId(1);
        member.setJoinedAt(new Timestamp(System.currentTimeMillis()));
        groupMemberDAO.addGroupMember(member);

        // GroupMember retrievedMember = groupMemberDAO.getGroupMemberById(1, 1);
        groupMemberDAO.deleteGroupMember(1, 1);

        GroupMember deletedMember = groupMemberDAO.getGroupMemberById(1, 1);
        assertNull(deletedMember);
    }

    @Test
    public void testGetAllGroupMembers() throws Exception {
        List<GroupMember> members = groupMemberDAO.getAllGroupMembers();
        assertNotNull(members);
        assertTrue(members.size() > 0);
    }
}
