package cn.amatrix.model.users;

import com.alibaba.fastjson2.JSON;
import java.sql.Timestamp;

/**
 * 用户类，表示用户的基本信息。
 */
public class User {
    private int user_id;
    private String username;
    private String password;
    private String email;
    private String avatar;
    private Timestamp created_at;
    private String log_status;
    private Timestamp last_login_at;
    private Timestamp last_logout_at;

    /**
     * 获取用户ID。
     * @return 用户ID
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     * 设置用户ID。
     * @param user_id 用户ID
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    /**
     * 获取用户名。
     * @return 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名。
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取密码。
     * @return 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码。
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取电子邮件。
     * @return 电子邮件
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置电子邮件。
     * @param email 电子邮件
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取头像。
     * @return 头像
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 设置头像。
     * @param avatar ��像
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * 获取创建时间。
     * @return 创建时间
     */
    public Timestamp getCreated_at() {
        return created_at;
    }

    /**
     * 设置创建时间。
     * @param created_at 创建时间
     */
    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    /**
     * 获取登录状态。
     * @return 登录状态
     */
    public String getLog_status() {
        return log_status;
    }

    /**
     * 设置登录状态。
     * @param log_status 登录状态
     */
    public void setLog_status(String log_status) {
        this.log_status = log_status;
    }

    /**
     * 获取最后登录时间。
     * @return 最后登录时间
     */
    public Timestamp getLast_login_at() {
        return last_login_at;
    }

    /**
     * 设置最后登录时间。
     * @param last_login_at 最后登录时间
     */
    public void setLast_login_at(Timestamp last_login_at) {
        this.last_login_at = last_login_at;
    }

    /**
     * 获取最后登出时间。
     * @return 最后登出时间
     */
    public Timestamp getLast_logout_at() {
        return last_logout_at;
    }

    /**
     * 设置最后登出时间。
     * @param last_logout_at 最后登出时间
     */
    public void setLast_logout_at(Timestamp last_logout_at) {
        this.last_logout_at = last_logout_at;
    }

    /**
     * 通过 JSON 字符串创建 User 对象。
     * @param json JSON 字符串
     * @return User 对象
     */
    public static User fromJson(String json) {
        return JSON.parseObject(json, User.class);
    }

    /**
     * 将 User 对象序列化为 JSON 字符串。
     * @return JSON 字符串
     */
    public String toJson() {
        return JSON.toJSONString(this);
    }
     
}
