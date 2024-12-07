package cn.amatrix.DAO.groups.http;

import cn.amatrix.DAO.groups.Imp.GroupJoinRequestDAOImp;
import cn.amatrix.model.groups.GroupJoinRequest;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.charset.StandardCharsets;

public class GroupJoinRequestDAO implements GroupJoinRequestDAOImp {
    private static final String BASE_URL = "http://localhost:1145/demo_webapp/groupJoinRequests";
    private final HttpClient httpClient;

    public GroupJoinRequestDAO() {
        this.httpClient = HttpClient.newHttpClient();
    }

    private HttpRequest buildRequest(String type, String param) throws Exception {
        String requestBody = "{\"type\":\"" + type + "\",\"param\":" + param + "}";
        return HttpRequest.newBuilder()
                .uri(new URI(BASE_URL))
                .header("Content-Type", "application/json; charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .build();
    }

    public void addGroupJoinRequest(GroupJoinRequest request) throws Exception {
        String param = request.toJson();
        HttpRequest httpRequest = buildRequest("add", param);
        httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }

    public List<GroupJoinRequest> getGroupJoinRequestsByGroupId(int groupId) throws Exception {
        HttpRequest request = buildRequest("getByGroupId", "\"" + String.valueOf(groupId) + "\"");
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
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
        HttpRequest request = buildRequest("getByUserId", "\"" + String.valueOf(userId) + "\"");
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
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
        HttpRequest request = buildRequest("getAll", "\"null\"");
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
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
        HttpRequest request = buildRequest("delete", String.valueOf(requestId));
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public void updateGroupJoinRequest(GroupJoinRequest request) throws Exception {
        String param = request.toJson();
        HttpRequest httpRequest = buildRequest("update", param);
        httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }

    public GroupJoinRequest getGroupJoinRequestById(int requestId) throws Exception {
        HttpRequest request = buildRequest("getById", String.valueOf(requestId));
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return GroupJoinRequest.fromJson(response.body());
    }
}
