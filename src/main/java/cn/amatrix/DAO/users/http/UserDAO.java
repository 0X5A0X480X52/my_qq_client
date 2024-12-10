package cn.amatrix.DAO.users.http;

import cn.amatrix.DAO.HttpConnector.HttpConnector;
import cn.amatrix.DAO.users.Imp.UserDAOImp;
import cn.amatrix.model.users.User;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDAO implements UserDAOImp {
    private static final String SUB_PATH = "/users";
    private final HttpConnector httpConnector;

    public UserDAO() {
        this.httpConnector = new HttpConnector();
    }

    public User getUserById(int userId) throws Exception {
        HttpResponse<String> response = httpConnector.sendRequest(SUB_PATH, "getById", String.valueOf(userId));
        return User.fromJson(response.body());
    }

    public User getUserByUsername(String username) throws Exception {
        HttpResponse<String> response = httpConnector.sendRequest(SUB_PATH, "getByUsername", username);
        return User.fromJson(response.body());
    }

    public User getUserByEmail(String email) throws Exception {
        HttpResponse<String> response = httpConnector.sendRequest(SUB_PATH, "getByEmail", email);
        return User.fromJson(response.body());
    }

    public void addUser(User user) throws Exception {
        String param = user.toJson();
        httpConnector.sendRequest(SUB_PATH, "add", param);
    }

    public void updateUser(User user) throws Exception {
        String param = user.toJson();
        httpConnector.sendRequest(SUB_PATH, "update", param);
    }

    public void deleteUser(int userId) throws Exception {
        httpConnector.sendRequest(SUB_PATH, "delete", String.valueOf(userId));
    }

    public List<User> getAllUsers() throws Exception {
        HttpResponse<String> response = httpConnector.sendRequest(SUB_PATH, "getAll", "null");
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
