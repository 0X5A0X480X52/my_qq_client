package cn.amatrix.service.chatMessage;

import cn.amatrix.utils.messageCache.MessageCacheManager;
import cn.amatrix.utils.messageCache.PrivateMessageCache;
import cn.amatrix.utils.messageCache.GroupMessageCache;
import cn.amatrix.model.users.PrivateMessage;
import cn.amatrix.model.groups.GroupMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessageCacheService {
    private MessageCacheManager cacheManager;

    public MessageCacheService(String defaultOutputPath) throws IOException, ClassNotFoundException {
        this.cacheManager = new MessageCacheManager(defaultOutputPath);
        this.cacheManager.deserializeCacheManager();
    }

    /**
     * 添加私信到缓存中。
     *
     * @param message 私信
     * @throws IOException 如果发生I/O错误
     */
    public void addPrivateMessage(PrivateMessage message) throws IOException {
        int senderId = message.getSenderId();
        int receiverId = message.getReceiverId();
        PrivateMessageCache cache = cacheManager.getPrivateMessageCache(senderId, receiverId);
        if (cache == null) {
            cache = new PrivateMessageCache(senderId, receiverId);
            cacheManager.addPrivateMessageCache(cache);
            this.cacheManager.serializeCacheManager();
        }
        cache.addMessage(message);
        cacheManager.updatePrivateMessageCache(cache);
        System.out.println("add private message");
    }

    /**
     * 添加私信到缓存中。
     *
     * @param senderId 发送者的ID
     * @param receiverId 接收者的ID
     * @param info 私信内容
     * @throws IOException 如果发生I/O错误
     */
    public void addPrivateMessage(int senderId, int receiverId, String info) throws IOException {
        PrivateMessage message = new PrivateMessage();
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setMessage(info);
        addPrivateMessage(message);
    }

    /**
     * 获取特定发送者和接收者的所有私信。
     *
     * @param senderId 发送者的ID
     * @param receiverId 接收者的ID
     * @return 私信列表
     */
    public List<PrivateMessage> getPrivateMessages(int senderId, int receiverId) {
        PrivateMessageCache cache = cacheManager.getPrivateMessageCache(senderId, receiverId);
        if (cache == null) {
            System.out.println("cache is null");
        }
        return cache != null ? cache.getAllMessages() : new ArrayList<>();
    }

    /**
     * 添加群组消息到缓存中。
     *
     * @param message 群组消息
     * @throws IOException 如果发生I/O错误
     */
    public void addGroupMessage(GroupMessage message) throws IOException {
        int groupId = message.getGroupId();
        GroupMessageCache cache = cacheManager.getGroupMessageCache(groupId);
        if (cache == null) {
            cache = new GroupMessageCache(groupId);
            cacheManager.addGroupMessageCache(cache);
            this.cacheManager.serializeCacheManager();
        }
        cache.addMessage(message);
        cacheManager.updateGroupMessageCache(cache);
        System.out.println("add group message");
    }

    /**
     * 添加群组消息到缓存中。
     *
     * @param groupId 群组的ID
     * @param info 群组消息内容
     * @throws IOException 如果发生I/O错误
     */
    public void addGroupMessage( int senderId, int groupId, String info) throws IOException {
        GroupMessage message = new GroupMessage();
        message.setSenderId(senderId);
        message.setGroupId(groupId);
        message.setMessage(info);
        addGroupMessage(message);
    }

    /**
     * 获取特定群组的所有消息。
     *
     * @param groupId 群组的ID
     * @return 群组消息列表
     */
    public List<GroupMessage> getGroupMessages(int groupId) {
        GroupMessageCache cache = cacheManager.getGroupMessageCache(groupId);
        if (cache == null) {
            System.out.println("cache is null");
        }
        return cache != null ? cache.getAllMessages() : new ArrayList<>();
    }
}
