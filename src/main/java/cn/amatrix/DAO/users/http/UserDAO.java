package cn.amatrix.DAO.users.http;

import cn.amatrix.DAO.users.Imp.UserDAOImp;
import cn.amatrix.model.users.User;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.charset.StandardCharsets;

public class UserDAO implements UserDAOImp {
    private static final String BASE_URL = "http://localhost:1145/demo_webapp/users";
    private final HttpClient httpClient;

    public UserDAO() {
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

    public User getUserById(int userId) throws Exception {
        HttpRequest request = buildRequest("getById", String.valueOf(userId));
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return User.fromJson(response.body());
    }

    public User getUserByUsername(String username) throws Exception {
        HttpRequest request = buildRequest("getByUsername", "\"" + username + "\"");
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return User.fromJson(response.body());
    }

    public User getUserByEmail(String email) throws Exception {
        HttpRequest request = buildRequest("getByEmail", "\"" + email + "\"");
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return User.fromJson(response.body());
    }

    public void addUser(User user) throws Exception {
        String param = user.toJson();
        HttpRequest request = buildRequest("add", param);
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public void updateUser(User user) throws Exception {
        String param = user.toJson();
        HttpRequest request = buildRequest("update", param);
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public void deleteUser(int userId) throws Exception {
        HttpRequest request = buildRequest("delete", String.valueOf(userId));
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public List<User> getAllUsers() throws Exception {
        HttpRequest request = buildRequest("getAll", "\"null\"");
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        List<User> users = new ArrayList<>();

        String responseBody = response.body();
        Pattern pattern = Pattern.compile("\\{[^}]+\\}");
        Matcher matcher = pattern.matcher(responseBody);

        List<String> jsonObjects = new ArrayList<>();
        while (matcher.find()) {
            String jsonObject = matcher.group();
            jsonObjects.add(jsonObject);
        }

        for (String json : jsonObjects) {
            users.add(User.fromJson(json));
        }
        return users;
    }
}
