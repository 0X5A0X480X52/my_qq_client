package cn.amatrix.model.message;

import java.time.ZonedDateTime;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONException;
import java.util.HashMap;
import java.util.Map;

/**
 * Message 类表示一个消息对象，包含发送者、接收者、内容等信息。
 */
public class Message {
    private MessageEndPoint receiver;
    private MessageEndPoint sender;
    private String messageId;
    private String type;
    private String content;
    private ZonedDateTime timestamp;
    private String status;
    private Map<String, Object> extraFields;

    static int messageCnt = 0;

    /**
     * MessageEndPoint 类表示消息的发送者或接收者。
     */
    public static class MessageEndPoint {
        private String id;
        private String type;
        private String name;

        // Getters and Setters
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * 默认构造方法，初始化消息对象。
     */
    public Message() {
        this.timestamp = ZonedDateTime.now();
        this.messageId = "msg" + messageCnt++;
        this.extraFields = new HashMap<>();
    }

    /**
     * 构造方法，初始化消息对象。
     *
     * @param type 消息类型
     * @param content 消息内容
     * @param status 消息状态
     */
    public Message(String type, String content, String status) {
        this.type = type;
        this.content = content;
        this.messageId = "msg" + messageCnt++;
        this.timestamp = ZonedDateTime.now();
        this.status = status;
        this.extraFields = new HashMap<>();
    }

    /**
     * 构造方法，初始化消息对象。
     *
     * @param receiver 消息接收者
     * @param sender 消息发送者
     * @param type 消息类型
     * @param content 消息内容
     * @param status 消息状态
     */
    public Message(MessageEndPoint receiver, MessageEndPoint sender, String type, String content, String status) {
        this.receiver = receiver;
        this.sender = sender;
        this.type = type;
        this.content = content;
        this.messageId = "msg" + messageCnt++;
        this.timestamp = ZonedDateTime.now();
        this.status = status;
        this.extraFields = new HashMap<>();
    }

    public void setSender(MessageEndPoint Sender) {
        this.sender = Sender;
    }

    public void setReceiver(MessageEndPoint Receiver) {
        this.receiver = Receiver;
    }

    public void setMessage(MessageEndPoint receiver, MessageEndPoint sender, String type, String content, String status) {
        this.receiver = receiver;
        this.sender = sender;
        this.type = type;
        this.content = content;
        this.messageId = "msg" + messageCnt++;
        this.status = status;
    }

    /**
     * 将消息对象序列化为 JSON 字符串。
     *
     * @return JSON 字符串
     */
    public String toJson() {
        Map<String, Object> message = new HashMap<>();
        Map<String, Object> sender = new HashMap<>();
        Map<String, Object> receiver = new HashMap<>();

        sender.put("name", this.sender.getName());
        sender.put("id", this.sender.getId());
        sender.put("type", this.sender.getType());
        receiver.put("id", this.receiver.getId());
        receiver.put("type", this.receiver.getType());
        receiver.put("name", this.receiver.getName());

        message.put("type", this.type);
        message.put("sender", sender);
        message.put("receiver", receiver);
        message.put("content", this.content);
        message.put("timestamp", this.timestamp.toString());
        message.put("messageId", this.messageId);
        message.put("status", this.status);

        return JSON.toJSONString(message);
    }

    /**
     * 从 JSON 字符串解析消息对象。
     *
     * @param jsonString JSON 字符串
     * @return 解析后的消息对象
     * @throws JSONException 如果解析失败
     */
    public static Message fromJson(String jsonString) throws JSONException {
        JSONObject jsonObject = JSON.parseObject(jsonString);
        Message message = new Message();

        try {
            message.receiver = jsonObject.getObject("receiver", MessageEndPoint.class);
            message.sender = jsonObject.getObject("sender", MessageEndPoint.class);
            message.messageId = jsonObject.getString("messageId");
            message.type = jsonObject.getString("type");
            message.content = jsonObject.getString("content");
            message.timestamp = jsonObject.getObject("timestamp", ZonedDateTime.class);
            message.status = jsonObject.getString("status");

            for (String key : jsonObject.keySet()) {
                if (!key.equals("receiver") &&
                        !key.equals("sender") &&
                        !key.equals("messageId") &&
                        !key.equals("type") &&
                        !key.equals("content") &&
                        !key.equals("timestamp") &&
                        !key.equals("status")) {
                    message.extraFields.put(key, jsonObject.get(key));
                }
            }

            if (!message.extraFields.isEmpty()) {
                throw new JSONException("Unrecognized fields: " + message.extraFields);
            }
        } catch (JSONException e) {
            throw new JSONException("Error parsing JSON to Message: " + e.getMessage(), e);
        }

        return message;
    }

    public static void main(String[] args) {
        // 创建发送者信息
        MessageEndPoint sender = new MessageEndPoint();
        sender.setId("user123");
        sender.setName("John Doe");
        sender.setType("user");

        // 创建接收者信息
        MessageEndPoint receiver = new MessageEndPoint();
        receiver.setId("group456");
        receiver.setType("group");
        receiver.setName("Group Chat");

        // 创建消息内容
        Message message = new Message(receiver, sender, "text", "Hello, everyone!", "sent");

        // 序列化为 JSON 字符串
        String jsonString = message.toJson();

        // 输出 JSON 字符串
        System.out.println(jsonString);

        Message message2 = Message.fromJson(jsonString);
        System.out.println(message2.toJson());
    }

    // Getters and Setters
    public MessageEndPoint getReceiver() {
        return receiver;
    }

    public MessageEndPoint getSender() {
        return sender;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public String getStatus() {
        return status;
    }

    public Map<String, Object> getExtraFields() {
        return extraFields;
    }

    public static int getMessageCnt() {
        return messageCnt;
    }

    public String toString() {
        return this.toJson();
    }
}
