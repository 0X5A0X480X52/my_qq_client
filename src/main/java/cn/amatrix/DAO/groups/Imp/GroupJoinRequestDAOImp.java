package cn.amatrix.DAO.groups.Imp;

import cn.amatrix.model.groups.GroupJoinRequest;
import java.util.List;

public interface GroupJoinRequestDAOImp {
    void addGroupJoinRequest(GroupJoinRequest request) throws Exception;
    List<GroupJoinRequest> getGroupJoinRequestsByGroupId(int groupId) throws Exception;
    List<GroupJoinRequest> getGroupJoinRequestsByUserId(int userId) throws Exception;
    List<GroupJoinRequest> getAllGroupJoinRequests() throws Exception;
    void deleteGroupJoinRequestById(int requestId) throws Exception;
    void updateGroupJoinRequest(GroupJoinRequest request) throws Exception;
    GroupJoinRequest getGroupJoinRequestById(int requestId) throws Exception;
}
