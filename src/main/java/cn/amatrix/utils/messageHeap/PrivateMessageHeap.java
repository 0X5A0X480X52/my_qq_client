package cn.amatrix.utils.messageHeap;

import cn.amatrix.model.users.PrivateMessage;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.List;

public class PrivateMessageHeap {
    private PriorityQueue<PrivateMessage> messageQueue;

    public PrivateMessageHeap(Comparator<PrivateMessage> comparator) {
        this.messageQueue = new PriorityQueue<>(comparator);
    }

    public void addMessage(PrivateMessage message) {
        messageQueue.add(message);
    }

    public PrivateMessage pollMessage() {
        return messageQueue.poll();
    }

    public List<PrivateMessage> pollMessages(int k) {
        List<PrivateMessage> messages = new ArrayList<>();
        for (int i = 0; i < k && !messageQueue.isEmpty(); i++) {
            messages.add(messageQueue.poll());
        }
        return messages;
    }

    public List<PrivateMessage> pollAllMessages() {
        List<PrivateMessage> messages = new ArrayList<>();
        while (!messageQueue.isEmpty()) {
            messages.add(messageQueue.poll());
        }
        return messages;
    }

    public boolean isEmpty() {
        return messageQueue.isEmpty();
    }

    public static PrivateMessageHeap createHeapBySentAt() {
        return new PrivateMessageHeap(PrivateMessage.BY_SENT_AT);
    }
}
