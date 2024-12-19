package cn.amatrix.service.chatMessage;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import cn.amatrix.model.groups.Group;
import cn.amatrix.model.groups.GroupMessage;
import cn.amatrix.model.message.Message;
import cn.amatrix.model.message.Message.MessageEndPoint;
import cn.amatrix.model.users.PrivateMessage;
import cn.amatrix.model.users.User;
import cn.amatrix.utils.messageHeap.GroupMessageHeap;
import cn.amatrix.utils.messageHeap.PrivateMessageHeap;
import cn.amatrix.utils.webSocketClient.WebSocketClient;
import cn.amatrix.utils.webSocketClient.receivedWebSocketMessage.ReceivedWebSocketMessageEventQueue;

public class ChatMessageService {
    
    MessageCacheService messageCacheService;
    WebSocketClient client;
    enum ChatMessageTypes {
        PrivateMessage,
        GroupMessage
    }

    /**
     * 构造方法，检查系统事件队列是否是 ReceivedWebSocketMessageEventQueue 的实例。
     * @throws IllegalArgumentException 如果系统事件队列不是 ReceivedWebSocketMessageEventQueue 的实例
     */

    public ChatMessageService( WebSocketClient client ) throws IllegalArgumentException, IOException, ClassNotFoundException {
        // 检查系统事件队列是否是 ReceivedWebSocketMessageEventQueue 的实例
        EventQueue eventQueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
        if ( eventQueue instanceof ReceivedWebSocketMessageEventQueue ) {
            this.client = client;
        } else
            throw new IllegalArgumentException("EventQueue is not instance of ReceivedWebSocketMessageEventQueue");

        this.messageCacheService = new MessageCacheService();
        // 确保 messageCacheService 已正确初始化
        if (this.messageCacheService == null) {
            throw new IllegalArgumentException("MessageCacheService is not initialized correctly.");
        }
    }

    /**
     * 发送用户消息。
     * @param infoString 消息内容
     * @param messageSender 发送者
     * @param messageReceiver 接收者
     * @throws IOException 消息写入缓存出错 
     */
    public void sendPrivateMessage( String infoString, User messageSender, User messageReceiver) throws IOException {

        MessageEndPoint receiver = new MessageEndPoint();
        receiver.setType("user");
        receiver.setId(String.valueOf(messageReceiver.getUser_id()));
        receiver.setName(messageReceiver.getUsername());
        MessageEndPoint sender = new MessageEndPoint();
        sender.setType("user");
        sender.setId( String.valueOf(messageSender.getUser_id()));
        sender.setName(messageSender.getUsername());
        Message message = new Message( receiver, sender,"PrivateMessage", infoString, "发送用户消息");
        
        this.messageCacheService.addPrivateMessage( messageSender.getUser_id(), messageReceiver.getUser_id(), infoString, new Timestamp(System.currentTimeMillis()));

        client.sendMessage(message.toJson());
    }

    /**
     * 发送用户消息。
     * @param infoString 消息内容
     * @param messageSenderId 发送者
     * @param messageReceiverId 接收者
     * @throws IOException 消息写入缓存出错 
     */
    public void sendPrivateMessage( String infoString, String messageSenderId, String messageReceiverId) throws IOException {

        MessageEndPoint receiver = new MessageEndPoint();
        receiver.setType("user");
        receiver.setId(messageReceiverId);
        MessageEndPoint sender = new MessageEndPoint();
        sender.setType("user");
        sender.setId(messageSenderId);
        Message message = new Message( receiver, sender,"PrivateMessage", infoString, "发送用户消息");
        
        this.messageCacheService.addPrivateMessage( Integer.parseInt(messageReceiverId), Integer.parseInt(messageReceiverId), infoString, new Timestamp(System.currentTimeMillis()));

        client.sendMessage(message.toJson());
    }

    /**
     * 发送群组消息。
     * @param infoString 消息内容
     * @param messageSender 发送者
     * @param messageReceiver 接收者
     * @throws IOException 消息写入缓存出错 
     */
    public void sendGroupMessage( String infoString, User messageSender, Group messageReceiver) throws IOException {

        MessageEndPoint receiver = new MessageEndPoint();
        receiver.setType("group");
        receiver.setId(String.valueOf(messageReceiver.getGroupId()));
        receiver.setName(messageReceiver.getGroupName());
        MessageEndPoint sender = new MessageEndPoint();
        sender.setType("user");
        sender.setId( String.valueOf(messageSender.getUser_id()));
        sender.setName(messageSender.getUsername());
        Message message = new Message( receiver, sender,"GroupMessage", infoString, "发送群组消息");

        this.messageCacheService.addGroupMessage( messageSender.getUser_id(), messageReceiver.getGroupId(), infoString, new Timestamp(System.currentTimeMillis()));
        
        client.sendMessage(message.toJson());
    }

    /**
     * 发送群组消息。
     * @param infoString 消息内容
     * @param messageSenderId 发送者
     * @param messageReceiverId 接收者
     * @throws IOException 消息写入缓存出错 
     */
    public void sendGroupMessage( String infoString, String messageSenderId, String messageReceiverId) throws IOException {

        MessageEndPoint receiver = new MessageEndPoint();
        receiver.setType("group");
        receiver.setId(messageReceiverId);
        MessageEndPoint sender = new MessageEndPoint();
        sender.setType("user");
        sender.setId(messageSenderId);
        Message message = new Message( receiver, sender,"GroupMessage", infoString, "发送群组消息");

        this.messageCacheService.addGroupMessage( Integer.parseInt(messageReceiverId) , Integer.parseInt(messageReceiverId), infoString, new Timestamp(System.currentTimeMillis()));
        
        client.sendMessage(message.toJson());
    }

    /**
     * 处理接收到的用户消息。
     * @param message 服务器返回的消息
     * @return 一个 Status 对象
     * @see Status 包含 发送者 ID、消息内容、消息类型和附加信息
     * @throws IllegalArgumentException 如果消息类型不是 PrivateMessage
     * @throws IOException 消息写入缓存出错 
     */
    public Status handleReceivedPrivateMessage(Message message) throws IllegalArgumentException, IOException {

        if ( !message.getType().equals("PrivateMessage") ) {
            throw new IllegalArgumentException("Message type is not PrivateMessage.");
        }

        int sender_id = Integer.parseInt(message.getSender().getId());
        int receiver_id = Integer.parseInt(message.getReceiver().getId());
        String infoString = message.getContent();
        ChatMessageTypes type = ChatMessageTypes.PrivateMessage;
        String additionalInfo = message.getStatus();
        Timestamp sendAt = Timestamp.from(message.getTimestamp().toInstant());

        this.messageCacheService.addPrivateMessage( Integer.parseInt(message.getSender().getId()), receiver_id, infoString, sendAt );
        
        return new Status( sender_id, receiver_id, infoString, type, additionalInfo);
    }

    /**
     * 处理接收到的群组消息。
     * @param message 服务器返回的消息
     * @return 一个 Status 对象
     * @see Status 包含 发送者 ID、消息内容、消息类型和附加信息
     * @throws IllegalArgumentException 如果消息类型不是 GroupMessage
     * @throws IOException 消息写入缓存出错 
     */
    public Status handleReceivedGroupMessage(Message message) throws IllegalArgumentException, IOException {
            
        if ( !message.getType().equals("GroupMessage") ) {
            throw new IllegalArgumentException("Message type is not GroupMessage.");
        }
    
        int sender_id = Integer.parseInt(message.getSender().getId());
        int group_id = Integer.parseInt(message.getReceiver().getId());
        String infoString = message.getContent();
        ChatMessageTypes type = ChatMessageTypes.GroupMessage;
        String additionalInfo = message.getStatus();
        Timestamp sendAt = Timestamp.from(message.getTimestamp().toInstant());

        this.messageCacheService.addGroupMessage( sender_id, group_id, infoString, sendAt );
            
        return new Status( sender_id, group_id, infoString, type, additionalInfo);
    }

    /**
     * 获取特定发送者和接收者的所有私信。
     * @param senderId 发送者的ID
     * @param receiverId 接收者的ID
     * @return 私信列表
     */
    public List<PrivateMessage> getPrivateMessageCache(int senderId, int receiverId) {
        List<PrivateMessage> messages1 = messageCacheService.getPrivateMessages(senderId, receiverId);
        List<PrivateMessage> messages2 = messageCacheService.getPrivateMessages(receiverId, senderId);
        PrivateMessageHeap messageHeap = PrivateMessageHeap.createHeapBySentAt();
        for (PrivateMessage message : messages1) {
            messageHeap.addMessage(message);
        }
        for (PrivateMessage message : messages2) {
            messageHeap.addMessage(message);
        }

        List<PrivateMessage> result = messageHeap.pollAllMessages();
        Collections.reverse(result);
        return result;
    }

    /**
     * 获取特定发送者和接收者的所有私信。
     * @param senderId 发送者的ID
     * @param receiverId 接收者的ID
     * @return 私信列表
     */
    public List<PrivateMessage> getPrivateMessageCache(int senderId, int receiverId, int k) {
        List<PrivateMessage> messages1 = messageCacheService.getPrivateMessages(senderId, receiverId);
        List<PrivateMessage> messages2 = messageCacheService.getPrivateMessages(receiverId, senderId);
        PrivateMessageHeap messageHeap = PrivateMessageHeap.createHeapBySentAt();
        for (PrivateMessage message : messages1) {
            messageHeap.addMessage(message);
        }
        for (PrivateMessage message : messages2) {
            messageHeap.addMessage(message);
        }

        List<PrivateMessage> result = messageHeap.pollMessages(k);
        Collections.reverse(result);
        return result;
    }

    /**
     * 获取特定群组的所有消息。
     * @param groupId 群组的ID
     * @return 群组消息列表
     */
    public List<GroupMessage> getGroupMessageCache(int groupId) {
        List<GroupMessage> messages = messageCacheService.getGroupMessages(groupId);
        GroupMessageHeap messageHeap = GroupMessageHeap.createHeapBySentAt();
        for (GroupMessage message : messages) {
            messageHeap.addMessage(message);
        }
        List<GroupMessage> result = messageHeap.pollAllMessages();
        Collections.reverse(result);
        return result;
    }

    /**
     * 获取特定群组的所有消息。
     * @param groupId 群组的ID
     * @return 群组消息列表
     */
    public List<GroupMessage> getGroupMessageCache(int groupId, int k) {
        List<GroupMessage> messages = messageCacheService.getGroupMessages(groupId);
        GroupMessageHeap messageHeap = GroupMessageHeap.createHeapBySentAt();
        for (GroupMessage message : messages) {
            messageHeap.addMessage(message);
        }
        List<GroupMessage> result = messageHeap.pollMessages(k);
        Collections.reverse(result);
        return result;
    }

    /**
     * Status 类用于存储消息的状态信息。
     */
    public class Status {
        int sender_id;
        int receiver_id;
        String infoString;
        ChatMessageTypes type;
        String additionalInfo;

        public Status( int sender_id, int receiver_id, String infoString, ChatMessageTypes type, String additionalInfo) {
            this.sender_id = sender_id;
            this.receiver_id = receiver_id;
            this.infoString = infoString;
            this.type = type;
            this.additionalInfo = additionalInfo;
        }

        public int getSender_id() {
            return sender_id;
        }

        public int getReceiver_id() {
            return receiver_id;
        }

        public String getInfoString() {
            return infoString;
        }

        public ChatMessageTypes getType() {
            return type;
        }

        public String getAdditionalInfo() {
            return additionalInfo;
        }
        
    }

    // 生成测试数据
    public static void main(String[] args) {

        User UserSender = new User();
        UserSender.setUser_id(1);
        UserSender.setUsername("SenderUser");

        User privateReceiver = new User();
        privateReceiver.setUser_id(2);
        privateReceiver.setUsername("PrivateReceiverUser");

        Group groupReceiver = new Group();
        groupReceiver.setGroupId(1);
        groupReceiver.setGroupName("GroupReceiver");

        // for private message
        MessageEndPoint receiver = new MessageEndPoint();
        receiver.setType("user");
        receiver.setId(String.valueOf(privateReceiver.getUser_id()));
        receiver.setName(privateReceiver.getUsername());
        MessageEndPoint sender = new MessageEndPoint();
        sender.setType("user");
        sender.setId( String.valueOf(UserSender.getUser_id()));
        sender.setName(UserSender.getUsername());
        Message message = new Message( receiver, sender,"PrivateMessage", "infoString : PrivateMessage", "发送用户消息");
        System.out.println(message.toJson());
        
        // for group message
        receiver.setType("group");
        receiver.setId(String.valueOf(groupReceiver.getGroupId()));
        receiver.setName(groupReceiver.getGroupName());
        sender.setType("user");
        sender.setId( String.valueOf(UserSender.getUser_id()));
        sender.setName(UserSender.getUsername());
        message = new Message( receiver, sender,"GroupMessage", "infoString : GroupMessage", "发送群组消息");
        System.out.println(message.toJson());

    }
}
