package cn.amatrix.service.users;

import cn.amatrix.DAO.users.mySQL.UserDAO;
import cn.amatrix.DAO.users.mySQL.PrivateMessageDAO;
import cn.amatrix.model.users.User;
import cn.amatrix.model.users.PrivateMessage;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * UserService 类提供对用户信息和消息的管理功能。
 */
public class UserService {
    private UserDAO userDAO;
    private PrivateMessageDAO privateMessageDAO;
    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    /**
     * 构造函数，初始化 UserDAO 和 PrivateMessageDAO。
     */
    public UserService() {
        this.userDAO = new UserDAO();
        this.privateMessageDAO = new PrivateMessageDAO();
    }

    /**
     * 根据用户ID获取用户信息。
     * @param userId 用户ID
     * @return 用户信息
     */
    public User getUserById(int userId) {
        try {
            return userDAO.getUserById(userId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting user by ID", e);
            return null;
        }
    }

    /**
     * 根据用户名获取用户信息。
     * @param username 用户名
     * @return 用户信息
     */
    public User getUserByUsername(String username) {
        try {
            return userDAO.getUserByUsername(username);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting user by username", e);
            return null;
        }
    }

    /**
     * 根据用户邮箱获取用户信息。
     * @param email 用户邮箱
     * @return 用户信息
     */
    public User getUserByEmail(String email) {
        try {
            return userDAO.getUserByEmail(email);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting user by email", e);
            return null;
        }
    }

    /**
     * 添加新用户。
     * @param user 用户信息
     */
    public void addUser(User user) {
        try {
            userDAO.addUser(user);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error adding user", e);
        }
    }

    /**
     * 更新用户信息。
     * @param user 用户信息
     */
    public void updateUser(User user) {
        try {
            userDAO.updateUser(user);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating user", e);
        }
    }

    /**
     * 根据用户ID删除用户。
     * @param userId 用户ID
     */
    public void deleteUser(int userId) {
        try {
            userDAO.deleteUser(userId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting user", e);
        }
    }

    /**
     * 获取所有用户信息。
     * @return 用户信息列表
     */
    public List<User> getAllUsers() {
        try {
            return userDAO.getAllUsers();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting all users", e);
            return null;
        }
    }

    /**
     * 根据消息ID获取私信。
     * @param messageId 消息ID
     * @return 私信
     */
    public PrivateMessage getPrivateMessageById(int messageId) {
        try {
            return privateMessageDAO.getPrivateMessageById(messageId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting private message by ID", e);
            return null;
        }
    }

    /**
     * 添加私信。
     * @param message 私信
     */
    public void addPrivateMessage(PrivateMessage message) {
        try {
            privateMessageDAO.addPrivateMessage(message);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error adding private message", e);
        }
    }

    /**
     * 更新私信。
     * @param message 私信
     */
    public void updatePrivateMessage(PrivateMessage message) {
        try {
            privateMessageDAO.updatePrivateMessage(message);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating private message", e);
        }
    }

    /**
     * 根据消息ID删除私信。
     * @param messageId 消息ID
     */
    public void deletePrivateMessage(int messageId) {
        try {
            privateMessageDAO.deletePrivateMessage(messageId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting private message", e);
        }
    }

    /**
     * 获取所有私信。
     * @return 私信列表
     */
    public List<PrivateMessage> getAllPrivateMessages() {
        try {
            return privateMessageDAO.getAllPrivateMessages();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting all private messages", e);
            return null;
        }
    }

    /**
     * 根据发送者和接收者ID获取私信。
     * @param senderId 发送者ID
     * @param receiverId 接收者ID
     * @return 私信列表
     */
    public List<PrivateMessage> getPrivateMessagesBySenderAndReceiver(int senderId, int receiverId) {
        try {
            return privateMessageDAO.getPrivateMessagesBySenderAndReceiver(senderId, receiverId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting private messages by sender and receiver", e);
            return null;
        }
    }

    /**
     * 根据发送者ID获取私信。
     * @param senderId 发送者ID
     * @return 私信列表
     */
    public List<PrivateMessage> getPrivateMessagesBySender(int senderId) {
        try {
            return privateMessageDAO.getPrivateMessagesBySender(senderId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting private messages by sender", e);
            return null;
        }
    }

    /**
     * 根据接收者ID获取私信。
     * @param receiverId 接收者ID
     * @return 私信列表
     */
    public List<PrivateMessage> getPrivateMessagesByReceiver(int receiverId) {
        try {
            return privateMessageDAO.getPrivateMessagesByReceiver(receiverId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting private messages by receiver", e);
            return null;
        }
    }
}
