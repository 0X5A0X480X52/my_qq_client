package cn.amatrix.service.chatMessage;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.io.IOException;

import cn.amatrix.model.groups.Group;
import cn.amatrix.model.message.Message;
import cn.amatrix.model.message.Message.MessageEndPoint;
import cn.amatrix.model.users.User;
import cn.amatrix.utils.webSocketClient.WebSocketClient;
import cn.amatrix.utils.webSocketClient.receivedWebSocketMessage.ReceivedWebSocketMessageEventQueue;

public class ChatMessageService {
    
    MessageCacheService messageCacheService = new MessageCacheService("./src/main/resources/messageCache");
    
    WebSocketClient client;
    enum ChatMessageTypes {
        PrivateMessage,
        GroupMessage
    }

    /**
     * 构造方法，检查系统事件队列是否是 ReceivedWebSocketMessageEventQueue 的实例。
     * @throws IllegalArgumentException 如果系统事件队列不是 ReceivedWebSocketMessageEventQueue 的实例
     */

    public ChatMessageService( WebSocketClient client ) throws IllegalArgumentException {
        // 检查系统事件队列是否是 ReceivedWebSocketMessageEventQueue 的实例
        EventQueue eventQueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
        if ( eventQueue instanceof ReceivedWebSocketMessageEventQueue ) {
            this.client = client;
        } else
            throw new IllegalArgumentException("EventQueue is not instance of ReceivedWebSocketMessageEventQueue");
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
        
        this.messageCacheService.addPrivateMessage( messageSender.getUser_id(), messageReceiver.getUser_id(), infoString);

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

        this.messageCacheService.addGroupMessage( messageSender.getUser_id(), messageReceiver.getGroupId(), infoString);
        
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

        int receiver_id = Integer.parseInt(message.getReceiver().getId());
        String infoString = message.getContent();
        ChatMessageTypes type = ChatMessageTypes.PrivateMessage;
        String additionalInfo = message.getStatus();

        this.messageCacheService.addPrivateMessage( Integer.parseInt(message.getSender().getId()), receiver_id, infoString);
        
        return new Status(receiver_id, infoString, type, additionalInfo);
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

        this.messageCacheService.addGroupMessage( sender_id,  group_id, infoString);
            
        return new Status(group_id, infoString, type, additionalInfo);
    }

    /**
     * Status 类用于存储消息的状态信息。
     */
    public class Status {
        int receiver_id;
        String infoString;
        ChatMessageTypes type;
        String additionalInfo;

        public Status(int receiver_id, String infoString, ChatMessageTypes type, String additionalInfo) {
            this.receiver_id = receiver_id;
            this.infoString = infoString;
            this.type = type;
            this.additionalInfo = additionalInfo;
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
