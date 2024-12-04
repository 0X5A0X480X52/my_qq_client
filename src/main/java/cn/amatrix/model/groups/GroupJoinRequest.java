package cn.amatrix.model.groups;

import com.alibaba.fastjson2.JSON;
import java.sql.Timestamp;

public class GroupJoinRequest {
    private int requestId;
    private int groupId;
    private int userId;
    private String requestMessage;
    private String requestStatus;
    private Timestamp requestedAt;
    
    public int getRequestId() {
        return requestId;
    }
    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }
    public int getGroupId() {
        return groupId;
    }
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getRequestMessage() {
        return requestMessage;
    }
    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }
    public String getRequestStatus() {
        return requestStatus;
    }
    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }
    public Timestamp getRequestedAt() {
        return requestedAt;
    }
    public void setRequestedAt(Timestamp requestedAt) {
        this.requestedAt = requestedAt;
    }

    /**
     * 通过 JSON 字符串创建 GroupJoinRequest 对象。
     * @param json JSON 字符串
     * @return Group 对象
     */
    public static GroupJoinRequest fromJson(String json) {
        return JSON.parseObject(json, GroupJoinRequest.class);
    }

    /**
     * 将 GroupJoinRequest 对象序列化为 JSON 字符串。
     * @return JSON 字符串
     */
    public String toJson() {
        return JSON.toJSONString(this);
    }
    
}
