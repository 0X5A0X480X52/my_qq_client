package cn.amatrix.servlet.users;

import cn.amatrix.DAO.users.mySQL.PrivateMessageDAO;
import cn.amatrix.model.users.PrivateMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

@WebServlet("/private_messages/*")
public class PrivateMessageServlet extends HttpServlet {
    private PrivateMessageDAO privateMessageDAO;

    @Override
    public void init() throws ServletException {
        this.privateMessageDAO = new PrivateMessageDAO();
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
                    PrivateMessage messageById = privateMessageDAO.getPrivateMessageById(messageId);
                    response.setContentType("application/json");
                    response.getWriter().write(messageById.toJson());
                    break;
                case "getBySenderAndReceiver":
                    JSONObject ids = JSON.parseObject(param);
                    int senderId = ids.getIntValue("senderId");
                    int receiverId = ids.getIntValue("receiverId");
                    List<PrivateMessage> messagesBySenderAndReceiver = privateMessageDAO.getPrivateMessagesBySenderAndReceiver(senderId, receiverId);
                    StringBuilder jsonBuilder = new StringBuilder("[");
                    for (int i = 0; i < messagesBySenderAndReceiver.size(); i++) {
                        jsonBuilder.append(messagesBySenderAndReceiver.get(i).toJson());
                        if (i < messagesBySenderAndReceiver.size() - 1) {
                            jsonBuilder.append(",");
                        }
                    }
                    jsonBuilder.append("]");
                    response.setContentType("application/json");
                    response.getWriter().write(jsonBuilder.toString());
                    break;
                case "getBySender":
                    senderId = Integer.parseInt(param);
                    List<PrivateMessage> messagesBySender = privateMessageDAO.getPrivateMessagesBySender(senderId);
                    jsonBuilder = new StringBuilder("[");
                    for (int i = 0; i < messagesBySender.size(); i++) {
                        jsonBuilder.append(messagesBySender.get(i).toJson());
                        if (i < messagesBySender.size() - 1) {
                            jsonBuilder.append(",");
                        }
                    }
                    jsonBuilder.append("]");
                    response.setContentType("application/json");
                    response.getWriter().write(jsonBuilder.toString());
                    break;
                case "getByReceiver":
                    receiverId = Integer.parseInt(param);
                    List<PrivateMessage> messagesByReceiver = privateMessageDAO.getPrivateMessagesByReceiver(receiverId);
                    jsonBuilder = new StringBuilder("[");
                    for (int i = 0; i < messagesByReceiver.size(); i++) {
                        jsonBuilder.append(messagesByReceiver.get(i).toJson());
                        if (i < messagesByReceiver.size() - 1) {
                            jsonBuilder.append(",");
                        }
                    }
                    jsonBuilder.append("]");
                    response.setContentType("application/json");
                    response.getWriter().write(jsonBuilder.toString());
                    break;
                case "getAll":
                    List<PrivateMessage> messages = privateMessageDAO.getAllPrivateMessages();
                    jsonBuilder = new StringBuilder("[");
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
                    PrivateMessage messageToAdd = PrivateMessage.fromJson(param);
                    privateMessageDAO.addPrivateMessage(messageToAdd);
                    response.setStatus(HttpServletResponse.SC_CREATED);
                    break;
                case "update":
                    PrivateMessage messageToUpdate = PrivateMessage.fromJson(param);
                    privateMessageDAO.updatePrivateMessage(messageToUpdate);
                    response.setStatus(HttpServletResponse.SC_OK);
                    break;
                case "delete":
                    int messageIdToDelete = Integer.parseInt(param);
                    privateMessageDAO.deletePrivateMessage(messageIdToDelete);
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
