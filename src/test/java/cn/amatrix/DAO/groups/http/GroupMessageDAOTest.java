package cn.amatrix.DAO.groups.http;

import cn.amatrix.model.groups.GroupMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class GroupMessageDAOTest {
    private GroupMessageDAO groupMessageDAO;

    @BeforeEach
    public void setUp() {
        groupMessageDAO = new GroupMessageDAO();
    }

    @Test
    public void testAddAndGetGroupMessage() throws Exception {
        GroupMessage message = new GroupMessage();
        message.setMessage("Hello World");
        message.setSentAt(new Timestamp(System.currentTimeMillis()));
        groupMessageDAO.addGroupMessage(message);

        GroupMessage retrievedMessage = groupMessageDAO.getGroupMessageById(message.getMessageId());
        assertNotNull(retrievedMessage);
        assertEquals("Hello World", retrievedMessage.getMessage());
    }

    @Test
    public void testUpdateGroupMessage() throws Exception {
        GroupMessage message = new GroupMessage();
        message.setMessage("Update Message");
        message.setSentAt(new Timestamp(System.currentTimeMillis()));
        groupMessageDAO.addGroupMessage(message);

        GroupMessage retrievedMessage = groupMessageDAO.getGroupMessageById(message.getMessageId());
        retrievedMessage.setMessage("Updated Message");
        groupMessageDAO.updateGroupMessage(retrievedMessage);

        GroupMessage updatedMessage = groupMessageDAO.getGroupMessageById(retrievedMessage.getMessageId());
        assertNotNull(updatedMessage);
        assertEquals("Updated Message", updatedMessage.getMessage());
    }

    @Test
    public void testDeleteGroupMessage() throws Exception {
        GroupMessage message = new GroupMessage();
        message.setMessage("Delete Message");
        message.setSentAt(new Timestamp(System.currentTimeMillis()));
        groupMessageDAO.addGroupMessage(message);

        GroupMessage retrievedMessage = groupMessageDAO.getGroupMessageById(message.getMessageId());
        groupMessageDAO.deleteGroupMessage(retrievedMessage.getMessageId());

        GroupMessage deletedMessage = groupMessageDAO.getGroupMessageById(retrievedMessage.getMessageId());
        assertNull(deletedMessage);
    }

    @Test
    public void testGetAllGroupMessages() throws Exception {
        List<GroupMessage> messages = groupMessageDAO.getAllGroupMessages();
        assertNotNull(messages);
        assertTrue(messages.size() > 0);
    }
}
