package cn.amatrix.utils.messageHeap;

import cn.amatrix.model.groups.GroupMessage;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.List;

public class GroupMessageHeap {
    private PriorityQueue<GroupMessage> messageQueue;

    public GroupMessageHeap(Comparator<GroupMessage> comparator) {
        this.messageQueue = new PriorityQueue<>(comparator);
    }

    public void addMessage(GroupMessage message) {
        messageQueue.add(message);
    }

    public GroupMessage pollMessage() {
        return messageQueue.poll();
    }

    public List<GroupMessage> pollMessages(int k) {
        List<GroupMessage> messages = new ArrayList<>();
        for (int i = 0; i < k && !messageQueue.isEmpty(); i++) {
            messages.add(messageQueue.poll());
        }
        return messages;
    }

    public List<GroupMessage> pollAllMessages() {
        List<GroupMessage> messages = new ArrayList<>();
        while (!messageQueue.isEmpty()) {
            messages.add(messageQueue.poll());
        }
        return messages;
    }

    public boolean isEmpty() {
        return messageQueue.isEmpty();
    }

    public static GroupMessageHeap createHeapBySentAt() {
        return new GroupMessageHeap(GroupMessage.BY_SENT_AT);
    }
}
