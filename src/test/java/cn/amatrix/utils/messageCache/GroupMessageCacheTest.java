package cn.amatrix.utils.messageCache;

import cn.amatrix.model.groups.GroupMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GroupMessageCacheTest {
    private GroupMessageCache cache;
    private GroupMessage message1;
    private GroupMessage message2;

    @BeforeEach
    public void setUp() {
        cache = new GroupMessageCache(1);
        message1 = new GroupMessage();
        message1.setGroupId(1);
        message1.setSenderId(101);
        message1.setMessage("Hello Group!");
        message1.setSentAt(new Timestamp(System.currentTimeMillis()));

        message2 = new GroupMessage();
        message2.setGroupId(1);
        message2.setSenderId(102);
        message2.setMessage("Hi everyone!");
        message2.setSentAt(new Timestamp(System.currentTimeMillis()));
    }

    @Test
    public void testAddMessage() {
        cache.addMessage(message1);
        cache.addMessage(message2);
        List<GroupMessage> messages = cache.getAllMessages();
        assertEquals(2, messages.size());
        assertEquals("Hello Group!", messages.get(0).getMessage());
        assertEquals("Hi everyone!", messages.get(1).getMessage());
    }

    @Test
    public void testAddMessageWithInvalidGroupId() {
        GroupMessage invalidMessage = new GroupMessage();
        invalidMessage.setGroupId(2);
        invalidMessage.setSenderId(103);
        invalidMessage.setMessage("Invalid message");
        invalidMessage.setSentAt(new Timestamp(System.currentTimeMillis()));

        assertThrows(IllegalArgumentException.class, () -> cache.addMessage(invalidMessage));
    }

    @Test
    public void testSerializeAndDeserializeMessages() throws IOException, ClassNotFoundException {
        cache.addMessage(message1);
        cache.addMessage(message2);

        String filePath = "C:\\UserFiles\\CS\\Java\\my_qq_client\\test_groupMessages2.ser";
        cache.serializeMessages(filePath);

        GroupMessageCache newCache = new GroupMessageCache(1);
        newCache.deserializeMessages(filePath);

        List<GroupMessage> messages = newCache.getAllMessages();
        assertEquals(2, messages.size());
        assertEquals("Hello Group!", messages.get(0).getMessage());
        assertEquals("Hi everyone!", messages.get(1).getMessage());

        // Clean up the test file
        new File(filePath).delete();
    }
}
