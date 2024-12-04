package cn.amatrix.model.users;

import com.alibaba.fastjson2.JSON;
import java.sql.Timestamp;

/**
 * Represents a friendship between users.
 */
public class Friend {
    private int userId;
    private int friendId;
    private Timestamp addedAt;

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
     * Gets the friend ID.
     * 
     * @return the friend ID
     */
    public int getFriendId() {
        return friendId;
    }

    /**
     * Sets the friend ID.
     * 
     * @param friendId the friend ID to set
     */
    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    /**
     * Gets the timestamp when the friendship was added.
     * 
     * @return the timestamp when the friendship was added
     */
    public Timestamp getAddedAt() {
        return addedAt;
    }

    /**
     * Sets the timestamp when the friendship was added.
     * 
     * @param addedAt the timestamp to set
     */
    public void setAddedAt(Timestamp addedAt) {
        this.addedAt = addedAt;
    }

    /**
     * 通过 JSON 字符串创建 Friend 对象。
     * @param json JSON 字符串
     * @return Group 对象
     */
    public static Friend fromJson(String json) {
        return JSON.parseObject(json, Friend.class);
    }

    /**
     * 将 Friend 对象序列化为 JSON 字符串。
     * @return JSON 字符串
     */
    public String toJson() {
        return JSON.toJSONString(this);
    }

}
