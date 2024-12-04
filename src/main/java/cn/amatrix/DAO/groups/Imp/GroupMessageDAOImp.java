package cn.amatrix.DAO.groups.Imp;

import cn.amatrix.model.groups.GroupMessage;
import java.util.List;

public interface GroupMessageDAOImp {
    GroupMessage getGroupMessageById(int messageId) throws Exception;
    void addGroupMessage(GroupMessage message) throws Exception;
    void updateGroupMessage(GroupMessage message) throws Exception;
    void deleteGroupMessage(int messageId) throws Exception;
    List<GroupMessage> getAllGroupMessages() throws Exception;
    List<GroupMessage> getGroupMessagesBySenderId(int senderId) throws Exception;
    List<GroupMessage> getGroupMessagesByGroupId(int groupId) throws Exception;
    List<GroupMessage> getGroupMessagesBySenderIdAndGroupId(int senderId, int groupId) throws Exception;
}
