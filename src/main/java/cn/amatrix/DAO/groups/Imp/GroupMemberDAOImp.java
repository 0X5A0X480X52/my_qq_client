package cn.amatrix.DAO.groups.Imp;

import cn.amatrix.model.groups.GroupMember;
import java.util.List;

public interface GroupMemberDAOImp {
    GroupMember getGroupMemberById(int groupId, int userId) throws Exception;
    void addGroupMember(GroupMember member) throws Exception;
    void updateGroupMember(GroupMember member) throws Exception;
    void deleteGroupMember(int groupId, int userId) throws Exception;
    List<GroupMember> getAllGroupMembers() throws Exception;
}
