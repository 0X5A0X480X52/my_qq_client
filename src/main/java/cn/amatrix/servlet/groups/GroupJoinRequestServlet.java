package cn.amatrix.servlet.groups;

import cn.amatrix.DAO.groups.mySQL.GroupJoinRequestDAO;
import cn.amatrix.model.groups.GroupJoinRequest;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

@WebServlet("/groupJoinRequests/*")
public class GroupJoinRequestServlet extends HttpServlet {
    private GroupJoinRequestDAO groupJoinRequestDAO;

    @Override
    public void init() throws ServletException {
        this.groupJoinRequestDAO = new GroupJoinRequestDAO();
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
                    int requestId = Integer.parseInt(param);
                    GroupJoinRequest requestById = groupJoinRequestDAO.getGroupJoinRequestById(requestId);
                    response.setContentType("application/json");
                    response.getWriter().write(requestById.toJson());
                    break;
                case "getAll":
                    List<GroupJoinRequest> requests = groupJoinRequestDAO.getAllGroupJoinRequests();
                    StringBuilder jsonBuilder = new StringBuilder("[");
                    for (int i = 0; i < requests.size(); i++) {
                        jsonBuilder.append(requests.get(i).toJson());
                        if (i < requests.size() - 1) {
                            jsonBuilder.append(",");
                        }
                    }
                    jsonBuilder.append("]");
                    response.setContentType("application/json");
                    response.getWriter().write(jsonBuilder.toString());
                    break;
                case "add":
                    GroupJoinRequest requestToAdd = GroupJoinRequest.fromJson(param);
                    groupJoinRequestDAO.addGroupJoinRequest(requestToAdd);
                    response.setStatus(HttpServletResponse.SC_CREATED);
                    break;
                case "update":
                    GroupJoinRequest requestToUpdate = GroupJoinRequest.fromJson(param);
                    groupJoinRequestDAO.updateGroupJoinRequest(requestToUpdate);
                    response.setStatus(HttpServletResponse.SC_OK);
                    break;
                case "delete":
                    int requestIdToDelete = Integer.parseInt(param);
                    groupJoinRequestDAO.deleteGroupJoinRequestById(requestIdToDelete);
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
