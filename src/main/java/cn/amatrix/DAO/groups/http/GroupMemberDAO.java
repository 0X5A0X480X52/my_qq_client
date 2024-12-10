package cn.amatrix.DAO.groups.http;

import cn.amatrix.DAO.HttpConnector.HttpConnector;
import cn.amatrix.DAO.groups.Imp.GroupMemberDAOImp;
import cn.amatrix.model.groups.GroupMember;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GroupMemberDAO implements GroupMemberDAOImp {
    private static final String SUB_PATH = "/groupMembers";
    private final HttpConnector httpConnector;

    public GroupMemberDAO() {
        this.httpConnector = new HttpConnector();
    }

    public GroupMember getGroupMemberById(int groupId, int userId) throws Exception {
        String param = "{\"groupId\":" + groupId + ",\"userId\":\"" + userId + "\"}";
        HttpResponse<String> response = httpConnector.sendRequest(SUB_PATH, "getById", param);
        return GroupMember.fromJson(response.body());
    }

    public void addGroupMember(GroupMember member) throws Exception {
        String param = member.toJson();
        httpConnector.sendRequest(SUB_PATH, "add", param);
    }

    public void updateGroupMember(GroupMember member) throws Exception {
        String param = member.toJson();
        httpConnector.sendRequest(SUB_PATH, "update", param);
    }

    public void deleteGroupMember(int groupId, int userId) throws Exception {
        String param = "{\"groupId\":" + groupId + ",\"userId\":" + userId + "}";
        httpConnector.sendRequest(SUB_PATH, "delete", param);
    }

    public List<GroupMember> getAllGroupMembers() throws Exception {
        HttpResponse<String> response = httpConnector.sendRequest(SUB_PATH, "getAll", "null");
        List<GroupMember> members = new ArrayList<>();

        String responseBody = response.body();
        Pattern pattern = Pattern.compile("\\{[^}]+\\}");
        Matcher matcher = pattern.matcher(responseBody);

        while (matcher.find()) {
            String jsonObject = matcher.group();
            members.add(GroupMember.fromJson(jsonObject));
        }
        return members;
    }
}
