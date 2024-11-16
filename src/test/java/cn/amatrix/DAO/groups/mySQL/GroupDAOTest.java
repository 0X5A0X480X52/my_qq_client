package cn.amatrix.DAO.groups.mySQL;

import cn.amatrix.model.groups.Group;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GroupDAOTest {

    private GroupDAO groupDAO;
    private Group testGroup;

    @BeforeAll
    public void setUp() {
        groupDAO = new GroupDAO();
        testGroup = new Group();
        testGroup.setGroupName("Test Group");
        testGroup.setCreatedAt(new Timestamp(System.currentTimeMillis()));
    }

    @Test
    public void testAddGroup() throws SQLException {
        groupDAO.addGroup(testGroup);
        Group retrievedGroup = groupDAO.getGroupByName(testGroup.getGroupName());
        assertNotNull(retrievedGroup);
        assertEquals(testGroup.getGroupName(), retrievedGroup.getGroupName());
    }

    @Test
    public void testGetGroupById() throws SQLException {
        testGroup.setGroupId(groupDAO.getGroupByName(testGroup.getGroupName()).getGroupId());
        Group retrievedGroup = groupDAO.getGroupById(testGroup.getGroupId());
        assertNotNull(retrievedGroup);
        assertEquals(testGroup.getGroupName(), retrievedGroup.getGroupName());
    }

    @Test
    public void testGetGroupByName() throws SQLException {
        Group retrievedGroup = groupDAO.getGroupByName(testGroup.getGroupName());
        assertNotNull(retrievedGroup);
        assertEquals(testGroup.getGroupName(), retrievedGroup.getGroupName());
    }

    @Test
    public void testUpdateGroup() throws SQLException {
        testGroup.setGroupId(groupDAO.getGroupByName(testGroup.getGroupName()).getGroupId());
        testGroup.setGroupName("Updated Group");
        groupDAO.updateGroup(testGroup);
        Group retrievedGroup = groupDAO.getGroupByName(testGroup.getGroupName());
        assertEquals("Updated Group", retrievedGroup.getGroupName());
    }

    @Test
    public void testDeleteGroup() throws SQLException {
        groupDAO.deleteGroup(testGroup.getGroupId());
        Group retrievedGroup = groupDAO.getGroupById(testGroup.getGroupId());
        assertNull(retrievedGroup);
    }

    @Test
    public void testGetAllGroups() throws SQLException {
        List<Group> groups = groupDAO.getAllGroups();
        assertNotNull(groups);
        assertTrue(groups.size() > 0);
    }
}
