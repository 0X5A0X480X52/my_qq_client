package cn.amatrix.DAO.users.http;

import cn.amatrix.DAO.users.Imp.PrivateMessageDAOImp;
import cn.amatrix.model.users.PrivateMessage;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrivateMessageDAO implements PrivateMessageDAOImp {
    private static final String BASE_URL = "http://localhost:1145/demo_webapp/private_messages";
    private final HttpClient httpClient;

    public PrivateMessageDAO() {
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

    public PrivateMessage getPrivateMessageById(int messageId) throws Exception {
        HttpRequest request = buildRequest("getById", String.valueOf(messageId));
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return PrivateMessage.fromJson(response.body());
    }

    public void addPrivateMessage(PrivateMessage message) throws Exception {
        String param = message.toJson();
        HttpRequest request = buildRequest("add", param);
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public void updatePrivateMessage(PrivateMessage message) throws Exception {
        String param = message.toJson();
        HttpRequest request = buildRequest("update", param);
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public void deletePrivateMessage(int messageId) throws Exception {
        HttpRequest request = buildRequest("delete", String.valueOf(messageId));
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public List<PrivateMessage> getAllPrivateMessages() throws Exception {
        HttpRequest request = buildRequest("getAll", "\"null\"");
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
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
        HttpRequest request = buildRequest("getBySenderAndReceiver", "{\"senderId\":" + senderId + ",\"receiverId\":" + receiverId + "}");
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
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
        HttpRequest request = buildRequest("getBySender", String.valueOf(senderId));
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
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
        HttpRequest request = buildRequest("getByReceiver", String.valueOf(receiverId));
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
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
