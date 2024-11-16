package cn.amatrix.model.groups;

import com.alibaba.fastjson2.JSON;
import java.sql.Timestamp;

/**
 * Represents a member of a group.
 */
public class GroupMember {
    private int groupId;
    private int userId;
    private String power;
    private Timestamp joinedAt;

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
     * Gets the user ID.
     * 
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID.
     * 
     * @param userId the user ID to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the power level of the member.
     * 
     * @return the power level
     */
    public String getPower() {
        return power;
    }

    /**
     * Sets the power level of the member.
     * 
     * @param power the power level to set
     */
    public void setPower(String power) {
        this.power = power;
    }

    /**
     * Gets the timestamp when the member joined the group.
     * 
     * @return the timestamp when the member joined the group
     */
    public Timestamp getJoinedAt() {
        return joinedAt;
    }

    /**
     * Sets the timestamp when the member joined the group.
     * 
     * @param joinedAt the timestamp to set
     */
    public void setJoinedAt(Timestamp joinedAt) {
        this.joinedAt = joinedAt;
    }

    /**
     * 通过 JSON 字符串创建 GroupMember 对象。
     * @param json JSON 字符串
     * @return GroupMember 对象
     */
    public static GroupMember fromJson(String json) {
        return JSON.parseObject(json, GroupMember.class);
    }

    /**
     * 将 GroupMember 对象序列化为 JSON 字符串。
     * @return JSON 字符串
     */
    public String toJson() {
        return JSON.toJSONString(this);
    }
}
