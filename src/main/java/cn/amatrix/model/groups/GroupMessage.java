package cn.amatrix.model.groups;

import com.alibaba.fastjson2.JSON;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Comparator;

/**
 * Represents a message sent in a group.
 */
public class GroupMessage implements Serializable {
    private static final long serialVersionUID = 1L; // 添加 serialVersionUID
    private int messageId;
    private int groupId;
    private int senderId;
    private String message;
    private Timestamp sentAt;

    public static final Comparator<GroupMessage> BY_SENT_AT = (m1, m2) -> m2.getSentAt().compareTo(m1.getSentAt());

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
     * Gets the group ID.
     * 
     * @return the group ID
     */
    public int getGroupId() {
        return groupId;
    }

    /**
     * Sets the group ID.
     * 
     * @param groupId the group ID to set
     */
    public void setGroupId(int groupId) {
        this.groupId = groupId;
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
     * @param sendAt the timestamp to set
     */
    public void setSentAt(Timestamp sendAt) {
        this.sentAt = sendAt;
    }

    /**
     * 通过 JSON 字符串创建 GroupMessage 对象。
     * @param json JSON 字符串
     * @return GroupMessage 对象
     */
    public static GroupMessage fromJson(String json) {
        return JSON.parseObject(json, GroupMessage.class);
    }

    /**
     * 将 GroupMessage 对象序列化为 JSON 字符串。
     * @return JSON 字符串
     */
    public String toJson() {
        return JSON.toJSONString(this);
    }
}
