package cn.amatrix.service.users;

import cn.amatrix.DAO.users.Imp.FriendDAOImp;
import cn.amatrix.DAO.users.Imp.FriendRequestDAOImp;
import cn.amatrix.DAO.users.Imp.PrivateMessageDAOImp;
import cn.amatrix.DAO.users.Imp.UserDAOImp;

// import cn.amatrix.DAO.users.mySQL.UserDAO;
// import cn.amatrix.DAO.users.mySQL.PrivateMessageDAO;
// import cn.amatrix.DAO.users.mySQL.FriendDAO;
// import cn.amatrix.DAO.users.mySQL.FriendRequestDAO;
import cn.amatrix.DAO.users.http.*;

import cn.amatrix.model.users.User;
import cn.amatrix.model.users.PrivateMessage;
import cn.amatrix.model.users.Friend;
import cn.amatrix.model.users.FriendRequest;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * UserService 类提供对用户信息和消息的管理功能。
 */
public class UserService {
    private UserDAOImp userDAO;
    private PrivateMessageDAOImp privateMessageDAO;
    private FriendDAOImp friendDAO;
    private FriendRequestDAOImp friendRequestDAO;
    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    /**
     * 构造函数，初始化 UserDAO 和 PrivateMessageDAO。
     */
    public UserService() {
        this.userDAO = new UserDAO();
        this.privateMessageDAO = new PrivateMessageDAO();
        this.friendDAO = new FriendDAO();
        this.friendRequestDAO = new FriendRequestDAO();
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
     * 更新用户名。
     * @param userId 用户ID
     * @param newUsername 新用户名
     */
    public void updateUsername(int userId, String newUsername) {
        try {
            User user = userDAO.getUserById(userId);
            if (user != null) {
                user.setUsername(newUsername);
                userDAO.updateUser(user);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating username", e);
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

    /**
     * 根据用户ID获取好友列表。
     * @param userId 用户ID
     * @return 好友列表
     */
    public List<Friend> getFriendsByUserId(int userId) {
        try {
            return friendDAO.getFriendsByUserId(userId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting friends by user ID", e);
            return null;
        }
    }

    /**
     * 添加好友。
     * @param friend 好友信息
     */
    public void addFriend(Friend friend) {
        try {
            friendDAO.addFriend(friend);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error adding friend", e);
        }
    }

    /**
     * 删除好友。
     * @param userId 用户ID
     * @param friendId 好友ID
     */
    public void deleteFriend(int userId, int friendId) {
        try {
            friendDAO.deleteFriend(userId, friendId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting friend", e);
        }
    }

    /**
     * 根据好友请求ID获取好友请求。
     * @param requestId 好友请求ID
     * @return 好友请求
     */
    public FriendRequest getFriendRequestById(int requestId) {
        try {
            return friendRequestDAO.getFriendRequestById(requestId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting friend request by ID", e);
            return null;
        }
    }

    /**
     * 添加好友请求。
     * @param request 好友请求
     */
    public void addFriendRequest(FriendRequest request) {
        try {
            friendRequestDAO.addFriendRequest(request);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error adding friend request", e);
        }
    }

    /**
     * 添加好友请求。
     * @param senderId 发送者ID
     * @param receiverId 接收者ID
     * @param requestMessage 请求消息
     */
    public void addFriendRequest(int senderId, int receiverId, String requestMessage) {
        try {
            FriendRequest request = new FriendRequest();
            request.setSenderId(senderId);
            request.setReceiverId(receiverId);
            request.setRequestMessage(requestMessage);
            request.setRequestStatus("pending");
            friendRequestDAO.addFriendRequest(request);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error adding friend request", e);
        }
    }

    /**
     * 更新好友请求。
     * @param request 好友请求
     */
    public void updateFriendRequest(FriendRequest request) {
        try {
            friendRequestDAO.updateFriendRequest(request);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating friend request", e);
        }
    }

    /**
     * 接受好友请求。
     * @param userId 用户ID
     * @param friendId 好友ID
     */
    public void acceptFriendRequest(FriendRequest request) {
        try {
            int userId = request.getSenderId();
            int friendId = request.getReceiverId();
            Friend friend = new Friend();
            friend.setUserId(userId);
            friend.setFriendId(friendId);
            friendDAO.addFriend(friend);
            friend.setUserId(friendId);
            friend.setFriendId(userId);
            friendDAO.addFriend(friend);

            request.setRequestStatus("accepted");
            friendRequestDAO.updateFriendRequest(request);

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error accepting friend request", e);
        }
    }

    /**
     * 拒绝好友请求。
     * @param userId 用户ID
     * @param friendId 好友ID
     */
    public void rejectFriendRequest(FriendRequest request) {
        try {
            request.setRequestStatus("rejected");
            friendRequestDAO.updateFriendRequest(request);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error rejecting friend request", e);
        }
    }

    /**
     * 删除好友请求。
     * @param requestId 好友请求ID
     */
    public void deleteFriendRequest(int requestId) {
        try {
            friendRequestDAO.deleteFriendRequest(requestId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting friend request", e);
        }
    }

    /**
     * 获取所有好友请求。
     * @return 好友请求列表
     */
    public List<FriendRequest> getAllFriendRequests() {
        try {
            return friendRequestDAO.getAllFriendRequests();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting all friend requests", e);
            return null;
        }
    }

    /**
     * 根据发送者ID获取好友请求。
     * @param senderId 发送者ID
     * @return 好友请求列表
     */
    public List<FriendRequest> getFriendRequestsBySender(int senderId) {
        try {
            return friendRequestDAO.getFriendRequestsBySender(senderId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting friend requests by sender", e);
            return null;
        }
    }

    /**
     * 根据接收者ID获取好友请求。
     * @param receiverId 接收者ID
     * @return 好友请求列表
     */
    public List<FriendRequest> getFriendRequestsByReceiver(int receiverId) {
        try {
            return friendRequestDAO.getFriendRequestsByReceiver(receiverId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting friend requests by receiver", e);
            return null;
        }
    }
}
