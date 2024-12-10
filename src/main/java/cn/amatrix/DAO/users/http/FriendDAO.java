package cn.amatrix.DAO.users.http;

import cn.amatrix.DAO.HttpConnector.HttpConnector;
import cn.amatrix.DAO.users.Imp.FriendDAOImp;
import cn.amatrix.model.users.Friend;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FriendDAO implements FriendDAOImp {
    private static final String SUB_PATH = "/friends";
    private final HttpConnector httpConnector;

    public FriendDAO() {
        this.httpConnector = new HttpConnector();
    }

    public Friend getFriendById(int userId, int friendId) throws Exception {
        String param = "{\"userId\":" + userId + ",\"friendId\":" + friendId + "}";
        HttpResponse<String> response = httpConnector.sendRequest(SUB_PATH, "getById", param);
        return Friend.fromJson(response.body());
    }

    public void addFriend(Friend friend) throws Exception {
        String param = friend.toJson();
        httpConnector.sendRequest(SUB_PATH, "add", param);
    }

    public void updateFriend(Friend friend) throws Exception {
        String param = friend.toJson();
        httpConnector.sendRequest(SUB_PATH, "update", param);
    }

    public void deleteFriend(int userId, int friendId) throws Exception {
        String param = "{\"userId\":" + userId + ",\"friendId\":" + friendId + "}";
        httpConnector.sendRequest(SUB_PATH, "delete", param);
    }

    public List<Friend> getAllFriends() throws Exception {
        HttpResponse<String> response = httpConnector.sendRequest(SUB_PATH, "getAll", "null");
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
        HttpResponse<String> response = httpConnector.sendRequest(SUB_PATH, "getByUserId", String.valueOf(userId));
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
