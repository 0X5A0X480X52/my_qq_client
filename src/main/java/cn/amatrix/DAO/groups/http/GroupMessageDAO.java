package cn.amatrix.DAO.groups.http;

import cn.amatrix.DAO.HttpConnector.HttpConnector;
import cn.amatrix.DAO.groups.Imp.GroupMessageDAOImp;
import cn.amatrix.model.groups.GroupMessage;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GroupMessageDAO implements GroupMessageDAOImp {
    private static final String SUB_PATH = "/group_messages";
    private final HttpConnector httpConnector;

    public GroupMessageDAO() {
        this.httpConnector = new HttpConnector();
    }

    public GroupMessage getGroupMessageById(int messageId) throws Exception {
        HttpResponse<String> response = httpConnector.sendRequest(SUB_PATH, "getById", String.valueOf(messageId));
        return GroupMessage.fromJson(response.body());
    }

    public void addGroupMessage(GroupMessage message) throws Exception {
        String param = message.toJson();
        httpConnector.sendRequest(SUB_PATH, "add", param);
    }

    public void updateGroupMessage(GroupMessage message) throws Exception {
        String param = message.toJson();
        httpConnector.sendRequest(SUB_PATH, "update", param);
    }

    public void deleteGroupMessage(int messageId) throws Exception {
        httpConnector.sendRequest(SUB_PATH, "delete", String.valueOf(messageId));
    }

    public List<GroupMessage> getAllGroupMessages() throws Exception {
        HttpResponse<String> response = httpConnector.sendRequest(SUB_PATH, "getAll", "null");
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
        HttpResponse<String> response = httpConnector.sendRequest(SUB_PATH, "getBySenderId", String.valueOf(senderId));
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
        HttpResponse<String> response = httpConnector.sendRequest(SUB_PATH, "getByGroupId", String.valueOf(groupId));
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
        HttpResponse<String> response = httpConnector.sendRequest(SUB_PATH, "getBySenderIdAndGroupId", param);
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
