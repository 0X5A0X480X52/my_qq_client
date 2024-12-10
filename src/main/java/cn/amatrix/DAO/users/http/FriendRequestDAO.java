package cn.amatrix.DAO.users.http;

import cn.amatrix.DAO.HttpConnector.HttpConnector;
import cn.amatrix.DAO.users.Imp.FriendRequestDAOImp;
import cn.amatrix.model.users.FriendRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FriendRequestDAO implements FriendRequestDAOImp {
    private static final String SUB_PATH = "/friend_requests";
    private final HttpConnector httpConnector;

    public FriendRequestDAO() {
        this.httpConnector = new HttpConnector();
    }

    public FriendRequest getFriendRequestById(int requestId) throws Exception {
        HttpResponse<String> response = httpConnector.sendRequest(SUB_PATH, "getById", String.valueOf(requestId));
        return FriendRequest.fromJson(response.body());
    }

    public void addFriendRequest(FriendRequest request) throws Exception {
        String param = request.toJson();
        httpConnector.sendRequest(SUB_PATH, "add", param);
    }

    public void updateFriendRequest(FriendRequest request) throws Exception {
        String param = request.toJson();
        httpConnector.sendRequest(SUB_PATH, "update", param);
    }

    public void deleteFriendRequest(int requestId) throws Exception {
        httpConnector.sendRequest(SUB_PATH, "delete", String.valueOf(requestId));
    }

    public List<FriendRequest> getAllFriendRequests() throws Exception {
        HttpResponse<String> response = httpConnector.sendRequest(SUB_PATH, "getAll", "null");
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
        HttpResponse<String> response = httpConnector.sendRequest(SUB_PATH, "getBySender", String.valueOf(senderId));
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
        HttpResponse<String> response = httpConnector.sendRequest(SUB_PATH, "getByReceiver", String.valueOf(receiverId));
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
