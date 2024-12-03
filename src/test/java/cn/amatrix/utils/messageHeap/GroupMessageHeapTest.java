package cn.amatrix.utils.messageHeap;

import cn.amatrix.model.groups.GroupMessage;
import org.junit.jupiter.api.Test;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GroupMessageHeapTest {

    @Test
    public void testAddAndPollMessage() {
        GroupMessageHeap heap = GroupMessageHeap.createHeapBySentAt();

        GroupMessage message1 = new GroupMessage();
        message1.setMessageId(1);
        message1.setSentAt(Timestamp.valueOf("2023-10-01 10:00:00"));

        GroupMessage message2 = new GroupMessage();
        message2.setMessageId(2);
        message2.setSentAt(Timestamp.valueOf("2023-10-01 11:00:00"));

        heap.addMessage(message1);
        heap.addMessage(message2);

        assertEquals(2, heap.pollMessage().getMessageId());
        assertEquals(1, heap.pollMessage().getMessageId());
    }

    @Test
    public void testPollMessages() {
        GroupMessageHeap heap = GroupMessageHeap.createHeapBySentAt();

        GroupMessage message1 = new GroupMessage();
        message1.setMessageId(1);
        message1.setSentAt(Timestamp.valueOf("2023-10-01 10:00:00"));

        GroupMessage message2 = new GroupMessage();
        message2.setMessageId(2);
        message2.setSentAt(Timestamp.valueOf("2023-10-01 11:00:00"));

        GroupMessage message3 = new GroupMessage();
        message3.setMessageId(3);
        message3.setSentAt(Timestamp.valueOf("2023-10-01 12:00:00"));

        heap.addMessage(message1);
        heap.addMessage(message2);
        heap.addMessage(message3);

        List<GroupMessage> messages = heap.pollMessages(2);
        assertEquals(2, messages.size());
        assertEquals(3, messages.get(0).getMessageId());
        assertEquals(2, messages.get(1).getMessageId());
    }

    @Test
    public void testIsEmpty() {
        GroupMessageHeap heap = GroupMessageHeap.createHeapBySentAt();
        assertTrue(heap.isEmpty());

        GroupMessage message = new GroupMessage();
        message.setMessageId(1);
        message.setSentAt(Timestamp.valueOf("2023-10-01 10:00:00"));

        heap.addMessage(message);
        assertFalse(heap.isEmpty());

        heap.pollMessage();
        assertTrue(heap.isEmpty());
    }
}
