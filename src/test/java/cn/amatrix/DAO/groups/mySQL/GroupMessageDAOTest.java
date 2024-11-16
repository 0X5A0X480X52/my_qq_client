package cn.amatrix.DAO.groups.mySQL;

import cn.amatrix.model.groups.GroupMessage;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class GroupMessageDAOTest {

    @Test
    public void testGetAllGroupMessages() throws SQLException {
        GroupMessageDAO messageDAO = new GroupMessageDAO();
        List<GroupMessage> messages = messageDAO.getAllGroupMessages();
        assertNotNull(messages);
        assertTrue(messages.size() > 0);
    }

    @Test
    public void testAddGroupMessage() throws SQLException  {
        GroupMessageDAO messageDAO = new GroupMessageDAO();
        GroupMessage message = new GroupMessage();
        message.setGroupId(1);
        message.setSenderId(1);
        message.setMessage("Hello, this is a test group message.");
        message.setSentAt(new java.sql.Timestamp(System.currentTimeMillis()));
        messageDAO.addGroupMessage(message);

        List<GroupMessage> retrievedMessage = messageDAO.getGroupMessagesBySenderIdAndGroupId(1, 1);
        assertNotNull(retrievedMessage);
        assertTrue(retrievedMessage.size() > 0);
    }

    @Test
    public void testGetGroupMessageById() throws SQLException  {
        GroupMessageDAO messageDAO = new GroupMessageDAO();
        GroupMessage message = messageDAO.getGroupMessageById(2);
        assertNotNull(message);
        assertEquals(2, message.getMessageId());
    }

    @Test
    public void testUpdateGroupMessage() throws SQLException {
        GroupMessageDAO messageDAO = new GroupMessageDAO();
        GroupMessage message = messageDAO.getGroupMessageById(2);
        message.setMessage("Updated group message");
        messageDAO.updateGroupMessage(message);

        GroupMessage updatedMessage = messageDAO.getGroupMessageById(2);
        assertEquals("Updated group message", updatedMessage.getMessage());
    }

    @Test
    public void testDeleteGroupMessage() throws SQLException  {
        GroupMessageDAO messageDAO = new GroupMessageDAO();
        messageDAO.deleteGroupMessage(1);

        GroupMessage message = messageDAO.getGroupMessageById(1);
        assertNull(message);
    }

    @Test
    public void testGetGroupMessagesBySenderId()  throws SQLException  {
        GroupMessageDAO messageDAO = new GroupMessageDAO();
        List<GroupMessage> messages = messageDAO.getGroupMessagesBySenderId(1);
        assertNotNull(messages);
        assertTrue(messages.size() > 0);
    }

    @Test
    public void testGetGroupMessagesByGroupId() throws SQLException  {
        GroupMessageDAO messageDAO = new GroupMessageDAO();
        List<GroupMessage> messages = messageDAO.getGroupMessagesByGroupId(1);
        assertNotNull(messages);
        assertTrue(messages.size() > 0);
    }

    @Test
    public void testGetGroupMessagesBySenderIdAndGroupId() throws SQLException  {
        GroupMessageDAO messageDAO = new GroupMessageDAO();
        List<GroupMessage> messages = messageDAO.getGroupMessagesBySenderIdAndGroupId(1, 1);
        assertNotNull(messages);
        assertTrue(messages.size() > 0);
    }
}
