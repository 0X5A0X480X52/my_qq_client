package cn.amatrix.service.chatMessage;

import cn.amatrix.model.users.PrivateMessage;
import cn.amatrix.model.groups.GroupMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MessageCacheServiceTest {
    private MessageCacheService service;

    @BeforeEach
    public void setUp() throws ClassNotFoundException, IOException {
        service = new MessageCacheService("C:\\UserFiles\\CS\\Java\\my_qq_client\\src\\main\\resources\\messageCache");
    }

    @Test
    public void testAddAndRetrievePrivateMessage() throws IOException {
        PrivateMessage message = new PrivateMessage();
        message.setSenderId(1);
        message.setReceiverId(2);
        message.setMessage("Hello");
        service.addPrivateMessage(message);

        List<PrivateMessage> messages = service.getPrivateMessages(1, 2);
        assertEquals(1, messages.size());
        assertEquals("Hello", messages.get(0).getMessage());
    }

    @Test
    public void testAddAndRetrieveGroupMessage() throws IOException {
        GroupMessage message = new GroupMessage();
        message.setGroupId(1);
        message.setMessage("Hello Group");
        service.addGroupMessage(message);

        List<GroupMessage> messages = service.getGroupMessages(1);
        assertEquals(1, messages.size());
        assertEquals("Hello Group", messages.get(0).getMessage());
    }

    @Test
    public void testRetrieveEmptyPrivateMessages() {
        List<PrivateMessage> messages = service.getPrivateMessages(1, 2);
        assertTrue(messages.isEmpty());
    }

    @Test
    public void testRetrieveEmptyGroupMessages() {
        List<GroupMessage> messages = service.getGroupMessages(1);
        assertTrue(messages.isEmpty());
    }
}
