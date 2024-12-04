package cn.amatrix.servlet.groups;

import cn.amatrix.DAO.groups.mySQL.GroupMemberDAO;
import cn.amatrix.model.groups.GroupMember;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

@WebServlet("/groupMembers/*")
public class GroupMemberServlet extends HttpServlet {
    private GroupMemberDAO groupMemberDAO;

    @Override
    public void init() throws ServletException {
        this.groupMemberDAO = new GroupMemberDAO();
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
                    JSONObject idParams = JSON.parseObject(param);
                    int groupId = idParams.getIntValue("groupId");
                    int userId = idParams.getIntValue("userId");
                    GroupMember memberById = groupMemberDAO.getGroupMemberById(groupId, userId);
                    response.setContentType("application/json");
                    response.getWriter().write(memberById.toJson());
                    break;
                case "getAll":
                    List<GroupMember> members = groupMemberDAO.getAllGroupMembers();
                    StringBuilder jsonBuilder = new StringBuilder("[");
                    for (int i = 0; i < members.size(); i++) {
                        jsonBuilder.append(members.get(i).toJson());
                        if (i < members.size() - 1) {
                            jsonBuilder.append(",");
                        }
                    }
                    jsonBuilder.append("]");
                    response.setContentType("application/json");
                    response.getWriter().write(jsonBuilder.toString());
                    break;
                case "add":
                    GroupMember memberToAdd = GroupMember.fromJson(param);
                    groupMemberDAO.addGroupMember(memberToAdd);
                    response.setStatus(HttpServletResponse.SC_CREATED);
                    break;
                case "update":
                    GroupMember memberToUpdate = GroupMember.fromJson(param);
                    groupMemberDAO.updateGroupMember(memberToUpdate);
                    response.setStatus(HttpServletResponse.SC_OK);
                    break;
                case "delete":
                    JSONObject deleteParams = JSON.parseObject(param);
                    int groupIdToDelete = deleteParams.getIntValue("groupId");
                    int userIdToDelete = deleteParams.getIntValue("userId");
                    groupMemberDAO.deleteGroupMember(groupIdToDelete, userIdToDelete);
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    break;
                default:
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
