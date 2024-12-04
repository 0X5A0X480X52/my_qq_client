package cn.amatrix.servlet.groups;

import cn.amatrix.DAO.groups.mySQL.GroupDAO;
import cn.amatrix.model.groups.Group;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

@WebServlet("/groups/*")
public class GroupServlet extends HttpServlet {
    private GroupDAO groupDAO;

    @Override
    public void init() throws ServletException {
        this.groupDAO = new GroupDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String requestBody = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
            JSONObject jsonObject = JSON.parseObject(requestBody);

            String type = jsonObject.getString("type");
            String param = jsonObject.get("param").toString();

            switch (type) {
                case "getById":
                    // 根据群组 ID 获取群组信息
                    int groupId = Integer.parseInt(param);
                    Group groupById = groupDAO.getGroupById(groupId);
                    response.setContentType("application/json");
                    response.getWriter().write(groupById.toJson());
                    break;
                case "getByName":
                    // 根据群组名称获取群组信息
                    String groupName = param;
                    Group groupByName = groupDAO.getGroupByName(groupName);
                    response.setContentType("application/json");
                    response.getWriter().write(groupByName.toJson());
                    break;
                case "getAll":
                    // 获取所有群组信息
                    List<Group> groups = groupDAO.getAllGroups();
                    StringBuilder jsonBuilder = new StringBuilder("[");
                    for (int i = 0; i < groups.size(); i++) {
                        jsonBuilder.append(groups.get(i).toJson());
                        if (i < groups.size() - 1) {
                            jsonBuilder.append(",");
                        }
                    }
                    jsonBuilder.append("]");
                    response.setContentType("application/json");
                    response.getWriter().write(jsonBuilder.toString());
                    break;
                case "add":
                    // 添加新的群组
                    Group groupToAdd = Group.fromJson(param);
                    groupDAO.addGroup(groupToAdd);
                    response.setStatus(HttpServletResponse.SC_CREATED);
                    break;
                case "update":
                    // 更新群组信息
                    Group groupToUpdate = Group.fromJson(param);
                    groupDAO.updateGroup(groupToUpdate);
                    response.setStatus(HttpServletResponse.SC_OK);
                    break;
                case "delete":
                    // 删除群组
                    int groupIdToDelete = Integer.parseInt(param);
                    groupDAO.deleteGroup(groupIdToDelete);
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    break;
                default:
                    // 无效的请求类型
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request type");
                    break;
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Empty implementation
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Empty implementation
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Empty implementation
    }
}
