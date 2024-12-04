package cn.amatrix.servlet.groups;

import cn.amatrix.DAO.groups.mySQL.GroupMessageDAO;
import cn.amatrix.model.groups.GroupMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

@WebServlet("/groupMessages/*")
public class GroupMessageServlet extends HttpServlet {
    private GroupMessageDAO groupMessageDAO;

    @Override
    public void init() throws ServletException {
        this.groupMessageDAO = new GroupMessageDAO();
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
                    int messageId = Integer.parseInt(param);
                    GroupMessage messageById = groupMessageDAO.getGroupMessageById(messageId);
                    response.setContentType("application/json");
                    response.getWriter().write(messageById.toJson());
                    break;
                case "getAll":
                    List<GroupMessage> messages = groupMessageDAO.getAllGroupMessages();
                    StringBuilder jsonBuilder = new StringBuilder("[");
                    for (int i = 0; i < messages.size(); i++) {
                        jsonBuilder.append(messages.get(i).toJson());
                        if (i < messages.size() - 1) {
                            jsonBuilder.append(",");
                        }
                    }
                    jsonBuilder.append("]");
                    response.setContentType("application/json");
                    response.getWriter().write(jsonBuilder.toString());
                    break;
                case "add":
                    GroupMessage messageToAdd = GroupMessage.fromJson(param);
                    groupMessageDAO.addGroupMessage(messageToAdd);
                    response.setStatus(HttpServletResponse.SC_CREATED);
                    break;
                case "update":
                    GroupMessage messageToUpdate = GroupMessage.fromJson(param);
                    groupMessageDAO.updateGroupMessage(messageToUpdate);
                    response.setStatus(HttpServletResponse.SC_OK);
                    break;
                case "delete":
                    int messageIdToDelete = Integer.parseInt(param);
                    groupMessageDAO.deleteGroupMessage(messageIdToDelete);
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
