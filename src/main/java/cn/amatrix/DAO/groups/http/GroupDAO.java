package cn.amatrix.DAO.groups.http;

import cn.amatrix.DAO.groups.Imp.GroupDAOImp;
import cn.amatrix.model.groups.Group;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;

import java.net.URI;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

public class GroupDAO implements GroupDAOImp {
    private static final String BASE_URL = "http://localhost:1145/demo_webapp/groups";
    private final HttpClient httpClient;

    public GroupDAO() {
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

    /**
     * 根据群组 ID 获取群组信息
     * @param groupId 群组 ID
     * @return 群组对象
     * @throws Exception 异常
     */
    public Group getGroupById(int groupId) throws Exception {
        HttpRequest request = buildRequest("getById", String.valueOf(groupId));
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return Group.fromJson(response.body());
    }

    /**
     * 根据群组名称获取群组信息
     * @param groupName 群组名称
     * @return 群组对象
     * @throws Exception 异常
     */
    public Group getGroupByName(String groupName) throws Exception {
        HttpRequest request = buildRequest("getByName", "\"" + groupName + "\"");
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return Group.fromJson(response.body());
    }

    /**
     * 添加新的群组
     * @param group 群组对象
     * @throws Exception 异常
     */
    public void addGroup(Group group) throws Exception {
        String param = group.toJson();
        HttpRequest request = buildRequest("add", param);
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * 更新群组信息
     * @param group 群组对象
     * @throws Exception 异常
     */
    public void updateGroup(Group group) throws Exception {
        String param = group.toJson();
        HttpRequest request = buildRequest("update", param);
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * 删除群组
     * @param groupId 群组 ID
     * @throws Exception 异常
     */
    public void deleteGroup(int groupId) throws Exception {
        HttpRequest request = buildRequest("delete", String.valueOf(groupId));
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * 获取所有群组信息
     * @return 群组列表
     * @throws Exception 异常
     */
    public List<Group> getAllGroups() throws Exception {
        HttpRequest request = buildRequest("getAll", "\"null\"");
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        List<Group> groups = new ArrayList<Group>();

        String responseBody = response.body();

        System.out.println(responseBody);

        Pattern pattern = Pattern.compile("\\{[^}]+\\}");
        Matcher matcher = pattern.matcher(responseBody);

        // 存储 JSON 对象字符串的 List
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
            // groupDAO.addGroup(group);
            // Group retrievedGroup = groupDAO.getGroupByName("hello1");
            // System.out.println(retrievedGroup.toJson());
            // System.out.println(retrievedGroup.getGroupName());
            
            List<Group> groups = groupDAO.getAllGroups();
            for (Group g : groups) {
                System.out.println(g.toJson());
            }

            // // Group group = new Group();
            // group.setGroupName("DeleteGroup");
            // group.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            // // groupDAO.addGroup(group);

            // Group retrievedGroup = groupDAO.getGroupByName("DeleteGroup");
            // System.out.println(retrievedGroup.toJson());
            // groupDAO.deleteGroup(retrievedGroup.getGroupId());


        } catch (Exception e) {
            e.printStackTrace();
        }

        
        
    }
}
