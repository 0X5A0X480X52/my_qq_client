package cn.amatrix.DAO.users.http;

import cn.amatrix.DAO.HttpConnector.HttpConnector;
import cn.amatrix.DAO.users.Imp.PrivateMessageDAOImp;
import cn.amatrix.model.users.PrivateMessage;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrivateMessageDAO implements PrivateMessageDAOImp {
    private static final String SUB_PATH = "/private_messages";
    private final HttpConnector httpConnector;

    public PrivateMessageDAO() {
        this.httpConnector = new HttpConnector();
    }

    public PrivateMessage getPrivateMessageById(int messageId) throws Exception {
        HttpResponse<String> response = httpConnector.sendRequest(SUB_PATH, "getById", String.valueOf(messageId));
        return PrivateMessage.fromJson(response.body());
    }

    public void addPrivateMessage(PrivateMessage message) throws Exception {
        String param = message.toJson();
        httpConnector.sendRequest(SUB_PATH, "add", param);
    }

    public void updatePrivateMessage(PrivateMessage message) throws Exception {
        String param = message.toJson();
        httpConnector.sendRequest(SUB_PATH, "update", param);
    }

    public void deletePrivateMessage(int messageId) throws Exception {
        httpConnector.sendRequest(SUB_PATH, "delete", String.valueOf(messageId));
    }

    public List<PrivateMessage> getAllPrivateMessages() throws Exception {
        HttpResponse<String> response = httpConnector.sendRequest(SUB_PATH, "getAll", "null");
        List<PrivateMessage> messages = new ArrayList<>();

        String responseBody = response.body();
        Pattern pattern = Pattern.compile("\\{[^}]+\\}");
        Matcher matcher = pattern.matcher(responseBody);

        List<String> jsonObjects = new ArrayList<>();
        while (matcher.find()) {
            String jsonObject = matcher.group();
            jsonObjects.add(jsonObject);
        }

        for (String json : jsonObjects) {
            messages.add(PrivateMessage.fromJson(json));
        }
        return messages;
    }

    public List<PrivateMessage> getPrivateMessagesBySenderAndReceiver(int senderId, int receiverId) throws Exception {
        String param = "{\"senderId\":" + senderId + ",\"receiverId\":" + receiverId + "}";
        HttpResponse<String> response = httpConnector.sendRequest(SUB_PATH, "getBySenderAndReceiver", param);
        List<PrivateMessage> messages = new ArrayList<>();

        String responseBody = response.body();
        Pattern pattern = Pattern.compile("\\{[^}]+\\}");
        Matcher matcher = pattern.matcher(responseBody);

        List<String> jsonObjects = new ArrayList<>();
        while (matcher.find()) {
            String jsonObject = matcher.group();
            jsonObjects.add(jsonObject);
        }

        for (String json : jsonObjects) {
            messages.add(PrivateMessage.fromJson(json));
        }
        return messages;
    }

    public List<PrivateMessage> getPrivateMessagesBySender(int senderId) throws Exception {
        HttpResponse<String> response = httpConnector.sendRequest(SUB_PATH, "getBySender", String.valueOf(senderId));
        List<PrivateMessage> messages = new ArrayList<>();

        String responseBody = response.body();
        Pattern pattern = Pattern.compile("\\{[^}]+\\}");
        Matcher matcher = pattern.matcher(responseBody);

        List<String> jsonObjects = new ArrayList<>();
        while (matcher.find()) {
            String jsonObject = matcher.group();
            jsonObjects.add(jsonObject);
        }

        for (String json : jsonObjects) {
            messages.add(PrivateMessage.fromJson(json));
        }
        return messages;
    }

    public List<PrivateMessage> getPrivateMessagesByReceiver(int receiverId) throws Exception {
        HttpResponse<String> response = httpConnector.sendRequest(SUB_PATH, "getByReceiver", String.valueOf(receiverId));
        List<PrivateMessage> messages = new ArrayList<>();

        String responseBody = response.body();
        Pattern pattern = Pattern.compile("\\{[^}]+\\}");
        Matcher matcher = pattern.matcher(responseBody);

        List<String> jsonObjects = new ArrayList<>();
        while (matcher.find()) {
            String jsonObject = matcher.group();
            jsonObjects.add(jsonObject);
        }

        for (String json : jsonObjects) {
            messages.add(PrivateMessage.fromJson(json));
        }
        return messages;
    }
}
