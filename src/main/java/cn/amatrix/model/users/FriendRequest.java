package cn.amatrix.model.users;

import java.sql.Timestamp;

import com.alibaba.fastjson2.JSON;

/**
 * Represents a friend request between users.
 */
public class FriendRequest {
    private int requestId;
    private int senderId;
    private int receiverId;
    private String requestMessage;
    private String requestStatus;
    private Timestamp requestedAt;

    public enum RequestStatus {
        pending, approved, rejected
    }

    /**
     * Gets the request ID.
     * 
     * @return the request ID
     */
    public int getRequestId() {
        return requestId;
    }

    /**
     * Sets the request ID.
     * 
     * @param requestId the request ID to set
     */
    public void setRequestId(int requestId) {
        this.requestId = requestId;
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
     * Gets the request message.
     * 
     * @return the request message
     */
    public String getRequestMessage() {
        return requestMessage;
    }

    /**
     * Sets the request message.
     * 
     * @param requestMessage the request message to set
     */
    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }

    /**
     * Gets the request status.
     * 
     * @return the request status
     */
    public String getRequestStatus() {
        return requestStatus;
    }

    /**
     * Sets the request status.
     * 
     * @param requestStatus the request status to set
     */
    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    /**
     * Gets the timestamp when the request was made.
     * 
     * @return the timestamp when the request was made
     */
    public Timestamp getRequestedAt() {
        return requestedAt;
    }

    /**
     * Sets the timestamp when the request was made.
     * 
     * @param requestedAt the timestamp to set
     */
    public void setRequestedAt(Timestamp requestedAt) {
        this.requestedAt = requestedAt;
    }

    /**
     * 通过 JSON 字符串创建 FriendRequest 对象。
     * @param json JSON 字符串
     * @return Group 对象
     */
    public static FriendRequest fromJson(String json) {
        return JSON.parseObject(json, FriendRequest.class);
    }

    /**
     * 将 FriendRequest 对象序列化为 JSON 字符串。
     * @return JSON 字符串
     */
    public String toJson() {
        return JSON.toJSONString(this);
    }
}
