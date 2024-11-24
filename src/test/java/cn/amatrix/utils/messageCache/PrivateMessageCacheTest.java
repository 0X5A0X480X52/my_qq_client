package cn.amatrix.utils.messageCache;

import cn.amatrix.model.users.PrivateMessage;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class PrivateMessageCacheTest {

    @Test
    public void testPrivateMessageCache() {
        int senderId = 1;
        int receiverId = 2;
        PrivateMessageCache cache = new PrivateMessageCache(senderId, receiverId);

        // 添加测试消息
        PrivateMessage message1 = new PrivateMessage();
        PrivateMessage message2 = new PrivateMessage();
        message1.setMessageId(1);
        message1.setSenderId(senderId);
        message1.setReceiverId(receiverId);
        message1.setMessage("Hello, how are you?");
        message1.setSentAt(new java.sql.Timestamp(System.currentTimeMillis()));
        message2.setMessageId(2);
        message2.setSenderId(senderId);
        message2.setReceiverId(receiverId);
        message2.setMessage("I'm fine, thank you.");
        message2.setSentAt(new java.sql.Timestamp(System.currentTimeMillis()));

        cache.addMessage(message1);
        cache.addMessage(message2);

        // 序列化消息
        String outputPath = "C:/UserFiles/CS/Java/my_qq_client/";
        try {
            cache.serializeMessagesWithGeneratedFilename(outputPath);
            System.out.println("Messages serialized successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Serialization failed");
        }

        // 反序列化消息
        PrivateMessageCache deserializedCache = new PrivateMessageCache(senderId, receiverId);
        try {
            deserializedCache.deserializeMessages(outputPath + "privateMessages_1_2.ser");
            System.out.println("Messages deserialized successfully.");
            List<PrivateMessage> messages = deserializedCache.getAllMessages();
            assertEquals(2, messages.size());
            assertEquals("Hello, how are you?", messages.get(0).getMessage());
            assertEquals("I'm fine, thank you.", messages.get(1).getMessage());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            fail("Deserialization failed");
        }
    }
}
