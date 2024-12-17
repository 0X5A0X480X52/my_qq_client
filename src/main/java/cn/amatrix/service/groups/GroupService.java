package cn.amatrix.service.groups;

import cn.amatrix.DAO.groups.Imp.GroupDAOImp;
import cn.amatrix.DAO.groups.Imp.GroupJoinRequestDAOImp;
import cn.amatrix.DAO.groups.Imp.GroupMemberDAOImp;
import cn.amatrix.DAO.groups.Imp.GroupMessageDAOImp;
// import cn.amatrix.DAO.groups.mySQL.GroupDAO;
// import cn.amatrix.DAO.groups.mySQL.GroupMemberDAO;
// import cn.amatrix.DAO.groups.mySQL.GroupJoinRequestDAO;
// import cn.amatrix.DAO.groups.mySQL.GroupMessageDAO;
import cn.amatrix.DAO.groups.http.*;
import cn.amatrix.model.groups.Group;
import cn.amatrix.model.groups.GroupMember;
import cn.amatrix.model.groups.GroupJoinRequest;
import cn.amatrix.model.groups.GroupMessage;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * GroupService 类提供了对群组、群成员、入群申请和群消息的管理功能。
 */
public class GroupService {
    private GroupDAOImp groupDAO;
    private GroupMemberDAOImp groupMemberDAO;
    private GroupJoinRequestDAOImp groupJoinRequestDAO;
    private GroupMessageDAOImp groupMessageDAO;
    private static final Logger logger = Logger.getLogger(GroupService.class.getName());

    public GroupService() {
        this.groupDAO = new GroupDAO();
        this.groupMemberDAO = new GroupMemberDAO();
        this.groupJoinRequestDAO = new GroupJoinRequestDAO();
        this.groupMessageDAO = new GroupMessageDAO();
    }

    /**
     * 根据群组 ID 获取群组信息。
     *
     * @param groupId 群组 ID
     * @return 群组信息
     */
    public Group getGroupById(int groupId) {
        try {
            return groupDAO.getGroupById(groupId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting group by ID", e);
            return null;
        }
    }

    /**
     * 根据群组名称获取群组信息。
     *
     * @param groupName 群组名称
     * @return 群组信息
     */
    public Group getGroupByName(String groupName) {
        try {
            return groupDAO.getGroupByName(groupName);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting group by name", e);
            return null;
        }
    }

    /**
     * 添加新群组，并将创建者添加为群主。
     *
     * @param group 群组信息
     * @param creatorUserId 创建者用户 ID
     */
    public void addGroup(Group group, int creatorUserId) {
        try {
            groupDAO.addGroup(group);
            GroupMember member = new GroupMember();
            member.setGroupId(group.getGroupId());
            member.setUserId(creatorUserId);
            member.setPower("owner");
            member.setJoinedAt(new Timestamp(System.currentTimeMillis()));
            groupMemberDAO.addGroupMember(member);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error adding group", e);
        }
    }

    /**
     * 添加新群组，并将创建者添加为群主。
     *
     * @param group 群组信息
     * @param creatorUserId 创建者用户 ID
     */
    public Group createGroup(int creatorUserId) {
        try {
            Group group = new Group();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String groupName = "New Group" + timestamp.hashCode();
            group.setGroupName(groupName);
            group.setAvatar("\"null\"");
            group.setCreatedAt(timestamp);
            addGroup(group, creatorUserId);

            List<Group> groups = getAllGroups();
            for (Group g : groups) {
                if (g.getGroupName().equals(groupName)) {
                    GroupMember newMember = new GroupMember();
                    newMember.setGroupId(g.getGroupId());
                    newMember.setUserId(creatorUserId);
                    newMember.setPower("owner");
                    newMember.setJoinedAt(timestamp);
                    groupMemberDAO.addGroupMember(newMember);
                    return g;
                }
            }

            return null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error adding group", e);
            return null;
        }
    }

    /**
     * 更新群组信息。
     *
     * @param group 群组信息
     * @param userId 用户 ID
     */
    public void updateGroup(Group group, int userId) {
        try {
            GroupMember member = groupMemberDAO.getGroupMemberById(group.getGroupId(), userId);
            if (member != null && "owner".equals(member.getPower())) {
                groupDAO.updateGroup(group);
            } else {
                logger.log(Level.WARNING, "User does not have owner permissions to update group");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating group", e);
        }
    }

    /**
     * 删除群组。
     *
     * @param groupId 群组 ID
     * @param userId 用户 ID
     */
    public void deleteGroup(int groupId, int userId) {
        try {
            GroupMember member = groupMemberDAO.getGroupMemberById(groupId, userId);
            if (member != null && "owner".equals(member.getPower())) {
                groupDAO.deleteGroup(groupId);
            } else {
                logger.log(Level.WARNING, "User does not have owner permissions to delete group");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting group", e);
        }
    }

    /**
     * 获取所有群组信息。
     *
     * @return 所有群组信息
     */
    public List<Group> getAllGroups() {
        try {
            return groupDAO.getAllGroups();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting all groups", e);
            return null;
        }
    }

    /**
     * 根据群组 ID 和用户 ID 获取群成员信息。
     *
     * @param groupId 群组 ID
     * @param userId 用户 ID
     * @return 群成员信息
     */
    public GroupMember getGroupMemberById(int groupId, int userId) {
        try {
            return groupMemberDAO.getGroupMemberById(groupId, userId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting group member by ID", e);
            return null;
        }
    }

    /**
     * 更新群成员信息。
     *
     * @param member 群成员信息
     */
    public void updateGroupMember(GroupMember member) {
        try {
            groupMemberDAO.updateGroupMember(member);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating group member", e);
        }
    }

    /**
     * 删除群成员。
     *
     * @param groupId 群组 ID
     * @param userId 用户 ID
     */
    public void deleteGroupMember(int groupId, int userId) {
        try {
            groupMemberDAO.deleteGroupMember(groupId, userId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting group member", e);
        }
    }

    /**
     * 获取所有群成员信息。
     *
     * @return 所有群成员信息
     */
    public List<GroupMember> getAllGroupMembers() {
        try {
            return groupMemberDAO.getAllGroupMembers();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting all group members", e);
            return null;
        }
    }

    public List<GroupMember> getGroupMembersByUserId(int userId) {
        try {
            return groupMemberDAO.getGroupMembersByUserId(userId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting group members by user ID", e);
            return null;
        }
    }

    public List<GroupMember> getGroupMembersByGroupId(int groupId) {
        try {
            return groupMemberDAO.getGroupMembersByGroupId(groupId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting group members by group ID", e);
            return null;
        }
    }

    /**
     * 添加入群申请。
     *
     * @param groupId 群组 ID
     * @param userId 用户 ID
     * @param requestMessage 申请信息
     */
    public void addGroupJoinRequest(int groupId, int userId, String requestMessage) {
        try {
            GroupJoinRequest request = new GroupJoinRequest();
            request.setGroupId(groupId);
            request.setUserId(userId);
            request.setRequestMessage(requestMessage);
            request.setRequestStatus("pending");
            request.setRequestedAt(new Timestamp(System.currentTimeMillis()));
            groupJoinRequestDAO.addGroupJoinRequest(request);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error adding group join request", e);
        }
    }

    /**
     * 根据群组 ID 获取入群申请列表。
     *
     * @param groupId 群组 ID
     * @return 入群申请列表
     */
    public List<GroupJoinRequest> getGroupJoinRequestsByGroupId(int groupId) {
        try {
            return groupJoinRequestDAO.getGroupJoinRequestsByGroupId(groupId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting group join requests by group ID", e);
            return null;
        }
    }

    /**
     * 根据当前用户 ID 获取本用户可处理的入群申请列表。
     *
     * @param groupId 群组 ID
     * @return 入群申请列表
     */
    public List<GroupJoinRequest> getGroupJoinRequestsByUserId_toHandle(int currentUserId) {
        try {
            List<GroupJoinRequest> outputList = new ArrayList<>();
            List<GroupMember> memberList = groupMemberDAO.getGroupMembersByUserId(currentUserId);
            List<Integer> groupIds = new ArrayList<>();
            for (GroupMember member : memberList) {
                if ("owner".equals(member.getPower()) || "admin".equals(member.getPower())) {
                    groupIds.add(member.getGroupId());
                }
            }
            for (int groupId : groupIds) {
                List<GroupJoinRequest> requests = groupJoinRequestDAO.getGroupJoinRequestsByGroupId(groupId);
                for (GroupJoinRequest request : requests) {
                    outputList.add(request);
                }
            }
            return outputList;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting group join requests by group ID", e);
            return null;
        }
    }

    /**
     * 根据用户 ID 获取本用户发送的入群申请列表。
     *
     * @param userId 用户 ID
     * @return 入群申请列表
     */
    public List<GroupJoinRequest> getGroupJoinRequestsByUserId_send(int userId) {
        try {
            return groupJoinRequestDAO.getGroupJoinRequestsByUserId(userId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting group join requests by user ID", e);
            return null;
        }
    }

    /**
     * 处理入群申请。
     *
     * @param requestId 申请 ID
     * @param userId 用户 ID
     * @param isApproved 是否批准
     */
    public void handleGroupJoinRequest(int requestId, int userId, boolean isApproved) {
        try {
            GroupJoinRequest request = groupJoinRequestDAO.getGroupJoinRequestById(requestId);
            GroupMember handler = groupMemberDAO.getGroupMemberById(request.getGroupId(), userId);
            if (handler != null && ("owner".equals(handler.getPower()) || "admin".equals(handler.getPower()))) {
                if (request != null) {
                    if (isApproved) {
                        GroupMember member = new GroupMember();
                        member.setGroupId(request.getGroupId());
                        member.setUserId(request.getUserId());
                        member.setPower("member");
                        member.setJoinedAt(new Timestamp(System.currentTimeMillis()));
                        groupMemberDAO.addGroupMember(member);
                        request.setRequestStatus("approved");
                    } else {
                        request.setRequestStatus("rejected");
                    }
                    groupJoinRequestDAO.updateGroupJoinRequest(request);
                }
            } else {
                logger.log(Level.WARNING, "User does not have owner permissions to handle group join request");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error handling group join request", e);
        }
    }

    /**
     * 添加群消息。
     *
     * @param groupId 群组 ID
     * @param senderId 发送者 ID
     * @param messageContent 消息内容
     */
    public void addGroupMessage(int groupId, int senderId, String messageContent) {
        try {
            GroupMessage message = new GroupMessage();
            message.setGroupId(groupId);
            message.setSenderId(senderId);
            message.setMessage(messageContent);
            message.setSentAt(new Timestamp(System.currentTimeMillis()));
            groupMessageDAO.addGroupMessage(message);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error adding group message", e);
        }
    }

    /**
     * 添加群消息。
     *
     * @param groupId 群组 ID
     * @param senderId 发送者 ID
     * @param messageContent 消息内容
     */
    public void addGroupMessageThrowsException(int groupId, int senderId, String messageContent) throws Exception {
        try {
            GroupMessage message = new GroupMessage();
            message.setGroupId(groupId);
            message.setSenderId(senderId);
            message.setMessage(messageContent);
            message.setSentAt(new Timestamp(System.currentTimeMillis()));
            groupMessageDAO.addGroupMessage(message);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error adding group message", e);
            throw e;
        }
    }

    /**
     * 根据消息 ID 获取群消息。
     *
     * @param messageId 消息 ID
     * @return 群消息
     */
    public GroupMessage getGroupMessageById(int messageId) {
        try {
            return groupMessageDAO.getGroupMessageById(messageId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting group message by ID", e);
            return null;
        }
    }

    /**
     * 更新群消息。
     *
     * @param message 群消息
     */
    public void updateGroupMessage(GroupMessage message) {
        try {
            groupMessageDAO.updateGroupMessage(message);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating group message", e);
        }
    }

    /**
     * 删除群消息。
     *
     * @param messageId 消息 ID
     */
    public void deleteGroupMessage(int messageId) {
        try {
            groupMessageDAO.deleteGroupMessage(messageId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting group message", e);
        }
    }

    /**
     * 获取所有群消息。
     *
     * @return 所有群消息
     */
    public List<GroupMessage> getAllGroupMessages() {
        try {
            return groupMessageDAO.getAllGroupMessages();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting all group messages", e);
            return null;
        }
    }

    /**
     * 根据群组 ID 获取群消息。
     *
     * @param groupId 群组 ID
     * @return 群消息列表
     */
    public List<GroupMessage> getGroupMessagesByGroupId(int groupId) {
        try {
            return groupMessageDAO.getGroupMessagesByGroupId(groupId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting group messages by group ID", e);
            return null;
        }
    }

    /**
     * 根据发送者 ID 获取群消息。
     *
     * @param senderId 发送者 ID
     * @return 群消息列表
     */
    public List<GroupMessage> getGroupMessagesBySenderId(int senderId) {
        try {
            return groupMessageDAO.getGroupMessagesBySenderId(senderId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting group messages by sender ID", e);
            return null;
        }
    }

    /**
     * 根据发送者 ID 和群组 ID 获取群消息。
     *
     * @param senderId 发送者 ID
     * @param groupId 群组 ID
     * @return 群消息列表
     */
    public List<GroupMessage> getGroupMessagesBySenderIdAndGroupId(int senderId, int groupId) {
        try {
            return groupMessageDAO.getGroupMessagesBySenderIdAndGroupId(senderId, groupId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting group messages by sender ID and group ID", e);
            return null;
        }
    }
}
