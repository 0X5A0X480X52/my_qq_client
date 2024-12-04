package cn.amatrix.DAO.users.http;

import cn.amatrix.DAO.users.Imp.FriendRequestDAOImp;
import cn.amatrix.model.users.FriendRequest;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FriendRequestDAO implements FriendRequestDAOImp {
    private static final String BASE_URL = "http://localhost:1145/demo_webapp/friend_requests";
    private final HttpClient httpClient;

    public FriendRequestDAO() {
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

    public FriendRequest getFriendRequestById(int requestId) throws Exception {
        HttpRequest request = buildRequest("getById", String.valueOf(requestId));
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return FriendRequest.fromJson(response.body());
    }

    public void addFriendRequest(FriendRequest request) throws Exception {
        String param = request.toJson();
        HttpRequest requestHttp = buildRequest("add", param);
        httpClient.send(requestHttp, HttpResponse.BodyHandlers.ofString());
    }

    public void updateFriendRequest(FriendRequest request) throws Exception {
        String param = request.toJson();
        HttpRequest requestHttp = buildRequest("update", param);
        httpClient.send(requestHttp, HttpResponse.BodyHandlers.ofString());
    }

    public void deleteFriendRequest(int requestId) throws Exception {
        HttpRequest request = buildRequest("delete", String.valueOf(requestId));
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public List<FriendRequest> getAllFriendRequests() throws Exception {
        HttpRequest request = buildRequest("getAll", "\"null\"");
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        List<FriendRequest> requests = new ArrayList<>();

        String responseBody = response.body();
        Pattern pattern = Pattern.compile("\\{[^}]+\\}");
        Matcher matcher = pattern.matcher(responseBody);

        List<String> jsonObjects = new ArrayList<>();
        while (matcher.find()) {
            String jsonObject = matcher.group();
            jsonObjects.add(jsonObject);
        }

        for (String json : jsonObjects) {
            requests.add(FriendRequest.fromJson(json));
        }
        return requests;
    }

    public List<FriendRequest> getFriendRequestsBySender(int senderId) throws Exception {
        HttpRequest request = buildRequest("getBySender", String.valueOf(senderId));
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        List<FriendRequest> requests = new ArrayList<>();

        String responseBody = response.body();
        Pattern pattern = Pattern.compile("\\{[^}]+\\}");
        Matcher matcher = pattern.matcher(responseBody);

        List<String> jsonObjects = new ArrayList<>();
        while (matcher.find()) {
            String jsonObject = matcher.group();
            jsonObjects.add(jsonObject);
        }

        for (String json : jsonObjects) {
            requests.add(FriendRequest.fromJson(json));
        }
        return requests;
    }

    public List<FriendRequest> getFriendRequestsByReceiver(int receiverId) throws Exception {
        HttpRequest request = buildRequest("getByReceiver", String.valueOf(receiverId));
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        List<FriendRequest> requests = new ArrayList<>();

        String responseBody = response.body();
        Pattern pattern = Pattern.compile("\\{[^}]+\\}");
        Matcher matcher = pattern.matcher(responseBody);

        List<String> jsonObjects = new ArrayList<>();
        while (matcher.find()) {
            String jsonObject = matcher.group();
            jsonObjects.add(jsonObject);
        }

        for (String json : jsonObjects) {
            requests.add(FriendRequest.fromJson(json));
        }
        return requests;
    }
}
