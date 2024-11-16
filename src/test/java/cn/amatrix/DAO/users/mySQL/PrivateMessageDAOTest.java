package cn.amatrix.DAO.users.mySQL;

import cn.amatrix.model.users.PrivateMessage;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class PrivateMessageDAOTest {

    @Test
    public void testGetAllPrivateMessages() throws SQLException {
        PrivateMessageDAO messageDAO = new PrivateMessageDAO();
        List<PrivateMessage> messages = messageDAO.getAllPrivateMessages();
        assertNotNull(messages);
        assertTrue(messages.size() > 0);
    }

    @Test
    public void testAddPrivateMessage() throws SQLException {
        PrivateMessageDAO messageDAO = new PrivateMessageDAO();
        PrivateMessage message = new PrivateMessage();
        message.setSenderId(1);
        message.setReceiverId(8);
        message.setMessage("Hello, this is a test message.");
        message.setSentAt(new java.sql.Timestamp(System.currentTimeMillis()));
        messageDAO.addPrivateMessage(message);

        List<PrivateMessage> retrievedMessage = messageDAO.getPrivateMessagesBySender(message.getSenderId());
        assertNotNull(retrievedMessage);
        assertTrue(retrievedMessage.size() > 0);
    }

    @Test
    public void testGetPrivateMessageById() throws SQLException {
        PrivateMessageDAO messageDAO = new PrivateMessageDAO();
        PrivateMessage message = messageDAO.getPrivateMessageById(6);
        assertNotNull(message);
        assertEquals(6, message.getMessageId());
    }

    @Test
    public void testUpdatePrivateMessage() throws SQLException {
        PrivateMessageDAO messageDAO = new PrivateMessageDAO();
        PrivateMessage message = messageDAO.getPrivateMessageById(6);
        message.setMessage("Updated message");
        messageDAO.updatePrivateMessage(message);

        PrivateMessage updatedMessage = messageDAO.getPrivateMessageById(6);
        assertEquals("Updated message", updatedMessage.getMessage());
    }

    @Test
    public void testDeletePrivateMessage() throws SQLException {
        PrivateMessageDAO messageDAO = new PrivateMessageDAO();
        messageDAO.deletePrivateMessage(7);

        PrivateMessage message = messageDAO.getPrivateMessageById(7);
        assertNull(message);
    }
}
