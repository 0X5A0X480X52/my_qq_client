package cn.amatrix.DAO.users.http;

import cn.amatrix.DAO.users.Imp.FriendDAOImp;
import cn.amatrix.model.users.Friend;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.charset.StandardCharsets;

public class FriendDAO implements FriendDAOImp {
    private static final String BASE_URL = "http://localhost:1145/demo_webapp/friends";
    private final HttpClient httpClient;

    public FriendDAO() {
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

    public Friend getFriendById(int userId, int friendId) throws Exception {
        HttpRequest request = buildRequest("getById", "{\"userId\":" + userId + ",\"friendId\":" + friendId + "}");
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return Friend.fromJson(response.body());
    }

    public void addFriend(Friend friend) throws Exception {
        String param = friend.toJson();
        HttpRequest request = buildRequest("add", param);
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public void updateFriend(Friend friend) throws Exception {
        String param = friend.toJson();
        HttpRequest request = buildRequest("update", param);
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public void deleteFriend(int userId, int friendId) throws Exception {
        HttpRequest request = buildRequest("delete", "{\"userId\":" + userId + ",\"friendId\":" + friendId + "}");
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public List<Friend> getAllFriends() throws Exception {
        HttpRequest request = buildRequest("getAll", "\"null\"");
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        List<Friend> friends = new ArrayList<>();

        String responseBody = response.body();
        Pattern pattern = Pattern.compile("\\{[^}]+\\}");
        Matcher matcher = pattern.matcher(responseBody);

        List<String> jsonObjects = new ArrayList<>();
        while (matcher.find()) {
            String jsonObject = matcher.group();
            jsonObjects.add(jsonObject);
        }

        for (String json : jsonObjects) {
            friends.add(Friend.fromJson(json));
        }
        return friends;
    }

    public List<Friend> getFriendsByUserId(int userId) throws Exception {
        HttpRequest request = buildRequest("getByUserId", String.valueOf(userId));
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        List<Friend> friends = new ArrayList<>();

        String responseBody = response.body();
        Pattern pattern = Pattern.compile("\\{[^}]+\\}");
        Matcher matcher = pattern.matcher(responseBody);

        List<String> jsonObjects = new ArrayList<>();
        while (matcher.find()) {
            String jsonObject = matcher.group();
            jsonObjects.add(jsonObject);
        }

        for (String json : jsonObjects) {
            friends.add(Friend.fromJson(json));
        }
        return friends;
    }
}
