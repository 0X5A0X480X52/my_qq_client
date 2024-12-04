package cn.amatrix.DAO.groups.Imp;

import cn.amatrix.model.groups.Group;
import java.util.List;

public interface GroupDAOImp {
    Group getGroupById(int groupId) throws Exception;
    Group getGroupByName(String groupName) throws Exception;
    void addGroup(Group group) throws Exception;
    void updateGroup(Group group) throws Exception;
    void deleteGroup(int groupId) throws Exception;
    List<Group> getAllGroups() throws Exception;
}
