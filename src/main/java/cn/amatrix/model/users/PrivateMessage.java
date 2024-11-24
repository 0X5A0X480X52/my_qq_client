package cn.amatrix.model.users;

import com.alibaba.fastjson2.JSON;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Represents a private message between users.
 */
public class PrivateMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private int messageId;
    private int senderId;
    private int receiverId;
    private String message;
    private Timestamp sentAt;

    /**
     * Gets the message ID.
     * 
     * @return the message ID
     */
    public int getMessageId() {
        return messageId;
    }

    /**
     * Sets the message ID.
     * 
     * @param messageId the message ID to set
     */
    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    /**
     * Gets the sender ID.
     * 
     * @return the sender ID
     */
    public int getSenderId() {
        return senderId;
    }

    /**
     * Sets the sender ID.
     * 
     * @param senderId the sender ID to set
     */
    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    /**
     * Gets the receiver ID.
     * 
     * @return the receiver ID
     */
    public int getReceiverId() {
        return receiverId;
    }

    /**
     * Sets the receiver ID.
     * 
     * @param receiverId the receiver ID to set
     */
    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    /**
     * Gets the message content.
     * 
     * @return the message content
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message content.
     * 
     * @param message the message content to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the timestamp when the message was sent.
     * 
     * @return the timestamp when the message was sent
     */
    public Timestamp getSentAt() {
        return sentAt;
    }

    /**
     * Sets the timestamp when the message was sent.
     * 
     * @param sentAt the timestamp to set
     */
    public void setSentAt(Timestamp sentAt) {
        this.sentAt = sentAt;
    }

    /**
     * 通过 JSON 字符串创建 PrivateMessage 对象。
     * @param json JSON 字符串
     * @return PrivateMessage 对象
     */
    public static PrivateMessage fromJson(String json) {
        return JSON.parseObject(json, PrivateMessage.class);
    }

    /**
     * 将 PrivateMessage 对象序列化为 JSON 字符串。
     * @return JSON 字符串
     */
    public String toJson() {
        return JSON.toJSONString(this);
    }
}
