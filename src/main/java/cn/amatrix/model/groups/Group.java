package cn.amatrix.model.groups;

import com.alibaba.fastjson2.JSON;
import java.sql.Timestamp;

/**
 * Represents a group.
 */
public class Group {
    private int groupId;
    private String groupName;
    private Timestamp createdAt;

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
     * Gets the group name.
     * 
     * @return the group name
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Sets the group name.
     * 
     * @param groupName the group name to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * Gets the timestamp when the group was created.
     * 
     * @return the timestamp when the group was created
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the timestamp when the group was created.
     * 
     * @param createdAt the timestamp to set
     */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 通过 JSON 字符串创建 Group 对象。
     * @param json JSON 字符串
     * @return Group 对象
     */
    public static Group fromJson(String json) {
        return JSON.parseObject(json, Group.class);
    }

    /**
     * 将 Group 对象序列化为 JSON 字符串。
     * @return JSON 字符串
     */
    public String toJson() {
        return JSON.toJSONString(this);
    }
}
