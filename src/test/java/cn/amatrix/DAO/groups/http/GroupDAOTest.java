package cn.amatrix.DAO.groups.http;

import cn.amatrix.model.groups.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class GroupDAOTest {
    private GroupDAO groupDAO;

    @BeforeEach
    public void setUp() {
        groupDAO = new GroupDAO();
    }

    @Test
    public void testAddAndGetGroup() throws Exception {
        Group group = new Group();
        group.setGroupName("hello");
        group.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        groupDAO.addGroup(group);

        Group retrievedGroup = groupDAO.getGroupByName("hello");
        assertNotNull(retrievedGroup);
        System.out.println(retrievedGroup.toJson());
        assertEquals("hello", retrievedGroup.getGroupName());
    }

    @Test
    public void testUpdateGroup() throws Exception {
        Group group = new Group();
        group.setGroupName("Update Group");
        group.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        groupDAO.addGroup(group);

        Group retrievedGroup = groupDAO.getGroupByName("Update Group");
        retrievedGroup.setGroupName("Updated Group");
        groupDAO.updateGroup(retrievedGroup);

        Group updatedGroup = groupDAO.getGroupByName("Updated Group");
        assertNotNull(updatedGroup);
        assertEquals("Updated Group", updatedGroup.getGroupName());
    }

    @Test
    public void testDeleteGroup() throws Exception {
        Group group = new Group();
        group.setGroupName("DeleteGroup");
        group.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        groupDAO.addGroup(group);

        Group retrievedGroup = groupDAO.getGroupByName("DeleteGroup");
        groupDAO.deleteGroup(retrievedGroup.getGroupId());

        Group deletedGroup = groupDAO.getGroupById(retrievedGroup.getGroupId());
        assertNull(deletedGroup);
    }

    @Test
    public void testGetAllGroups() throws Exception {
        List<Group> groups = groupDAO.getAllGroups();
        assertNotNull(groups);
        assertTrue(groups.size() > 0);
    }

    @Test
    public void testGetGroupById() throws Exception {
        Group group = groupDAO.getGroupById(1);
        assertNotNull(group);
        assertEquals(1, group.getGroupId());
    }

}
