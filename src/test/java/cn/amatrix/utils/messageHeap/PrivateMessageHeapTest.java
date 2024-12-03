package cn.amatrix.utils.messageHeap;

import cn.amatrix.model.users.PrivateMessage;
import org.junit.jupiter.api.Test;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PrivateMessageHeapTest {

    @Test
    public void testAddAndPollMessage() {
        PrivateMessageHeap heap = PrivateMessageHeap.createHeapBySentAt();

        PrivateMessage message1 = new PrivateMessage();
        message1.setMessageId(1);
        message1.setSentAt(Timestamp.valueOf("2023-10-01 10:00:00"));

        PrivateMessage message2 = new PrivateMessage();
        message2.setMessageId(2);
        message2.setSentAt(Timestamp.valueOf("2023-10-01 11:00:00"));

        heap.addMessage(message1);
        heap.addMessage(message2);

        assertEquals(2, heap.pollMessage().getMessageId());
        assertEquals(1, heap.pollMessage().getMessageId());
    }

    @Test
    public void testPollMessages() {
        PrivateMessageHeap heap = PrivateMessageHeap.createHeapBySentAt();

        PrivateMessage message1 = new PrivateMessage();
        message1.setMessageId(1);
        message1.setSentAt(Timestamp.valueOf("2023-10-01 10:00:00"));

        PrivateMessage message2 = new PrivateMessage();
        message2.setMessageId(2);
        message2.setSentAt(Timestamp.valueOf("2023-10-01 11:00:00"));

        PrivateMessage message3 = new PrivateMessage();
        message3.setMessageId(3);
        message3.setSentAt(Timestamp.valueOf("2023-10-01 12:00:00"));

        heap.addMessage(message1);
        heap.addMessage(message2);
        heap.addMessage(message3);

        List<PrivateMessage> messages = heap.pollMessages(2);
        assertEquals(2, messages.size());
        assertEquals(3, messages.get(0).getMessageId());
        assertEquals(2, messages.get(1).getMessageId());
    }

    @Test
    public void testIsEmpty() {
        PrivateMessageHeap heap = PrivateMessageHeap.createHeapBySentAt();
        assertTrue(heap.isEmpty());

        PrivateMessage message = new PrivateMessage();
        message.setMessageId(1);
        message.setSentAt(Timestamp.valueOf("2023-10-01 10:00:00"));

        heap.addMessage(message);
        assertFalse(heap.isEmpty());

        heap.pollMessage();
        assertTrue(heap.isEmpty());
    }
}
