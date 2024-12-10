package cn.amatrix.DAO.groups.http;

import cn.amatrix.DAO.HttpConnector.HttpConnector;
import cn.amatrix.DAO.groups.Imp.GroupJoinRequestDAOImp;
import cn.amatrix.model.groups.GroupJoinRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GroupJoinRequestDAO implements GroupJoinRequestDAOImp {
    private static final String SUB_PATH = "/groupJoinRequests";
    private final HttpConnector httpConnector;

    public GroupJoinRequestDAO() {
        this.httpConnector = new HttpConnector();
    }

    public void addGroupJoinRequest(GroupJoinRequest request) throws Exception {
        String param = request.toJson();
        httpConnector.sendRequest(SUB_PATH, "add", param);
    }

    public List<GroupJoinRequest> getGroupJoinRequestsByGroupId(int groupId) throws Exception {
        HttpResponse<String> response = httpConnector.sendRequest(SUB_PATH, "getByGroupId", String.valueOf(groupId));
        List<GroupJoinRequest> requests = new ArrayList<>();

        String responseBody = response.body();
        Pattern pattern = Pattern.compile("\\{[^}]+\\}");
        Matcher matcher = pattern.matcher(responseBody);

        while (matcher.find()) {
            String jsonObject = matcher.group();
            requests.add(GroupJoinRequest.fromJson(jsonObject));
        }
        return requests;
    }

    public List<GroupJoinRequest> getGroupJoinRequestsByUserId(int userId) throws Exception {
        HttpResponse<String> response = httpConnector.sendRequest(SUB_PATH, "getByUserId", String.valueOf(userId));
        List<GroupJoinRequest> requests = new ArrayList<>();

        String responseBody = response.body();
        Pattern pattern = Pattern.compile("\\{[^}]+\\}");
        Matcher matcher = pattern.matcher(responseBody);

        while (matcher.find()) {
            String jsonObject = matcher.group();
            requests.add(GroupJoinRequest.fromJson(jsonObject));
        }
        return requests;
    }

    public List<GroupJoinRequest> getAllGroupJoinRequests() throws Exception {
        HttpResponse<String> response = httpConnector.sendRequest(SUB_PATH, "getAll", "null");
        List<GroupJoinRequest> requests = new ArrayList<>();

        String responseBody = response.body();
        Pattern pattern = Pattern.compile("\\{[^}]+\\}");
        Matcher matcher = pattern.matcher(responseBody);

        while (matcher.find()) {
            String jsonObject = matcher.group();
            requests.add(GroupJoinRequest.fromJson(jsonObject));
        }
        return requests;
    }

    public void deleteGroupJoinRequestById(int requestId) throws Exception {
        httpConnector.sendRequest(SUB_PATH, "delete", String.valueOf(requestId));
    }

    public void updateGroupJoinRequest(GroupJoinRequest request) throws Exception {
        String param = request.toJson();
        httpConnector.sendRequest(SUB_PATH, "update", param);
    }

    public GroupJoinRequest getGroupJoinRequestById(int requestId) throws Exception {
        HttpResponse<String> response = httpConnector.sendRequest(SUB_PATH, "getById", String.valueOf(requestId));
        return GroupJoinRequest.fromJson(response.body());
    }
}
