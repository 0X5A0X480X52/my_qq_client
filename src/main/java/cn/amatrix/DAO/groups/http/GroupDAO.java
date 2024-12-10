package cn.amatrix.DAO.groups.http;

import cn.amatrix.DAO.HttpConnector.HttpConnector;
import cn.amatrix.DAO.groups.Imp.GroupDAOImp;
import cn.amatrix.model.groups.Group;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

public class GroupDAO implements GroupDAOImp {
    private static final String SUB_PATH = "/groups";
    private final HttpConnector httpConnector;

    public GroupDAO() {
        this.httpConnector = new HttpConnector();
    }

    public Group getGroupById(int groupId) throws Exception {
        HttpResponse<String> response = httpConnector.sendRequest(SUB_PATH, "getById", String.valueOf(groupId));
        return Group.fromJson(response.body());
    }

    public Group getGroupByName(String groupName) throws Exception {
        HttpResponse<String> response = httpConnector.sendRequest(SUB_PATH, "getByName", groupName);
        return Group.fromJson(response.body());
    }

    public void addGroup(Group group) throws Exception {
        String param = group.toJson();
        httpConnector.sendRequest(SUB_PATH, "add", param);
    }

    public void updateGroup(Group group) throws Exception {
        String param = group.toJson();
        httpConnector.sendRequest(SUB_PATH, "update", param);
    }

    public void deleteGroup(int groupId) throws Exception {
        httpConnector.sendRequest(SUB_PATH, "delete", String.valueOf(groupId));
    }

    public List<Group> getAllGroups() throws Exception {
        HttpResponse<String> response = httpConnector.sendRequest(SUB_PATH, "getAll", "null");
        List<Group> groups = new ArrayList<Group>();

        String responseBody = response.body();

        System.out.println(responseBody);

        Pattern pattern = Pattern.compile("\\{[^}]+\\}");
        Matcher matcher = pattern.matcher(responseBody);

        List<String> jsonObjects = new ArrayList<>();

        while (matcher.find()) {
            String jsonObject = matcher.group();
            jsonObjects.add(jsonObject);
        }

        for (String json : jsonObjects) {
            groups.add(Group.fromJson(json));
        }
        return groups;
    }

    public static void main(String[] args) {
        GroupDAO groupDAO = new GroupDAO();
        Group group = new Group();
        group.setGroupName("hello1");
        group.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        
        try {
            List<Group> groups = groupDAO.getAllGroups();
            for (Group g : groups) {
                System.out.println(g.toJson());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
