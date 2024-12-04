package cn.amatrix.DAO.groups.http;

import cn.amatrix.DAO.groups.Imp.GroupMemberDAOImp;
import cn.amatrix.model.groups.GroupMember;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GroupMemberDAO implements GroupMemberDAOImp {
    private static final String BASE_URL = "http://localhost:1145/demo_webapp/group_members";
    private final HttpClient httpClient;

    public GroupMemberDAO() {
        this.httpClient = HttpClient.newHttpClient();
    }

    private HttpRequest buildRequest(String type, String param) throws Exception {
        String requestBody = "{\"type\":\"" + type + "\",\"param\":" + param + "}";
        return HttpRequest.newBuilder()
                .uri(new URI(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
    }

    public GroupMember getGroupMemberById(int groupId, int userId) throws Exception {
        String param = "{\"groupId\":" + groupId + ",\"userId\":" + userId + "}";
        HttpRequest request = buildRequest("getById", param);
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return GroupMember.fromJson(response.body());
    }

    public void addGroupMember(GroupMember member) throws Exception {
        String param = member.toJson();
        HttpRequest request = buildRequest("add", param);
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public void updateGroupMember(GroupMember member) throws Exception {
        String param = member.toJson();
        HttpRequest request = buildRequest("update", param);
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public void deleteGroupMember(int groupId, int userId) throws Exception {
        String param = "{\"groupId\":" + groupId + ",\"userId\":" + userId + "}";
        HttpRequest request = buildRequest("delete", param);
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public List<GroupMember> getAllGroupMembers() throws Exception {
        HttpRequest request = buildRequest("getAll", "\"null\"");
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
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
