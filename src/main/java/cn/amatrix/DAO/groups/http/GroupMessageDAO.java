package cn.amatrix.DAO.groups.http;

import cn.amatrix.DAO.groups.Imp.GroupMessageDAOImp;
import cn.amatrix.model.groups.GroupMessage;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.charset.StandardCharsets;

public class GroupMessageDAO implements GroupMessageDAOImp {
    private static final String BASE_URL = "http://localhost:1145/demo_webapp/group_messages";
    private final HttpClient httpClient;

    public GroupMessageDAO() {
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

    public GroupMessage getGroupMessageById(int messageId) throws Exception {
        HttpRequest request = buildRequest("getById", String.valueOf(messageId));
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return GroupMessage.fromJson(response.body());
    }

    public void addGroupMessage(GroupMessage message) throws Exception {
        String param = message.toJson();
        HttpRequest request = buildRequest("add", param);
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public void updateGroupMessage(GroupMessage message) throws Exception {
        String param = message.toJson();
        HttpRequest request = buildRequest("update", param);
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public void deleteGroupMessage(int messageId) throws Exception {
        HttpRequest request = buildRequest("delete", String.valueOf(messageId));
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public List<GroupMessage> getAllGroupMessages() throws Exception {
        HttpRequest request = buildRequest("getAll", "\"null\"");
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        List<GroupMessage> messages = new ArrayList<>();

        String responseBody = response.body();
        Pattern pattern = Pattern.compile("\\{[^}]+\\}");
        Matcher matcher = pattern.matcher(responseBody);

        while (matcher.find()) {
            String jsonObject = matcher.group();
            messages.add(GroupMessage.fromJson(jsonObject));
        }
        return messages;
    }

    public List<GroupMessage> getGroupMessagesBySenderId(int senderId) throws Exception {
        HttpRequest request = buildRequest("getBySenderId", String.valueOf(senderId));
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        List<GroupMessage> messages = new ArrayList<>();

        String responseBody = response.body();
        Pattern pattern = Pattern.compile("\\{[^}]+\\}");
        Matcher matcher = pattern.matcher(responseBody);

        while (matcher.find()) {
            String jsonObject = matcher.group();
            messages.add(GroupMessage.fromJson(jsonObject));
        }
        return messages;
    }

    public List<GroupMessage> getGroupMessagesByGroupId(int groupId) throws Exception {
        HttpRequest request = buildRequest("getByGroupId", String.valueOf(groupId));
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        List<GroupMessage> messages = new ArrayList<>();

        String responseBody = response.body();
        Pattern pattern = Pattern.compile("\\{[^}]+\\}");
        Matcher matcher = pattern.matcher(responseBody);

        while (matcher.find()) {
            String jsonObject = matcher.group();
            messages.add(GroupMessage.fromJson(jsonObject));
        }
        return messages;
    }

    public List<GroupMessage> getGroupMessagesBySenderIdAndGroupId(int senderId, int groupId) throws Exception {
        String param = "{\"senderId\":" + senderId + ",\"groupId\":" + groupId + "}";
        HttpRequest request = buildRequest("getBySenderIdAndGroupId", param);
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        List<GroupMessage> messages = new ArrayList<>();

        String responseBody = response.body();
        Pattern pattern = Pattern.compile("\\{[^}]+\\}");
        Matcher matcher = pattern.matcher(responseBody);

        while (matcher.find()) {
            String jsonObject = matcher.group();
            messages.add(GroupMessage.fromJson(jsonObject));
        }
        return messages;
    }
}
