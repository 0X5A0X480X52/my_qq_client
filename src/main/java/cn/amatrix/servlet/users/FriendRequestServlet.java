package cn.amatrix.servlet.users;

import cn.amatrix.DAO.users.mySQL.FriendRequestDAO;
import cn.amatrix.model.users.FriendRequest;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

@WebServlet("/friend_requests/*")
public class FriendRequestServlet extends HttpServlet {
    private FriendRequestDAO friendRequestDAO;

    @Override
    public void init() throws ServletException {
        this.friendRequestDAO = new FriendRequestDAO();
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
                    FriendRequest requestById = friendRequestDAO.getFriendRequestById(requestId);
                    response.setContentType("application/json");
                    response.getWriter().write(requestById.toJson());
                    break;
                case "getBySender":
                    int senderId = Integer.parseInt(param);
                    List<FriendRequest> requestsBySender = friendRequestDAO.getFriendRequestsBySender(senderId);
                    StringBuilder jsonBuilder = new StringBuilder("[");
                    for (int i = 0; i < requestsBySender.size(); i++) {
                        jsonBuilder.append(requestsBySender.get(i).toJson());
                        if (i < requestsBySender.size() - 1) {
                            jsonBuilder.append(",");
                        }
                    }
                    jsonBuilder.append("]");
                    response.setContentType("application/json");
                    response.getWriter().write(jsonBuilder.toString());
                    break;
                case "getByReceiver":
                    int receiverId = Integer.parseInt(param);
                    List<FriendRequest> requestsByReceiver = friendRequestDAO.getFriendRequestsByReceiver(receiverId);
                    jsonBuilder = new StringBuilder("[");
                    for (int i = 0; i < requestsByReceiver.size(); i++) {
                        jsonBuilder.append(requestsByReceiver.get(i).toJson());
                        if (i < requestsByReceiver.size() - 1) {
                            jsonBuilder.append(",");
                        }
                    }
                    jsonBuilder.append("]");
                    response.setContentType("application/json");
                    response.getWriter().write(jsonBuilder.toString());
                    break;
                case "getAll":
                    List<FriendRequest> requests = friendRequestDAO.getAllFriendRequests();
                    jsonBuilder = new StringBuilder("[");
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
                    FriendRequest requestToAdd = FriendRequest.fromJson(param);
                    friendRequestDAO.addFriendRequest(requestToAdd);
                    response.setStatus(HttpServletResponse.SC_CREATED);
                    break;
                case "update":
                    FriendRequest requestToUpdate = FriendRequest.fromJson(param);
                    friendRequestDAO.updateFriendRequest(requestToUpdate);
                    response.setStatus(HttpServletResponse.SC_OK);
                    break;
                case "delete":
                    int requestIdToDelete = Integer.parseInt(param);
                    friendRequestDAO.deleteFriendRequest(requestIdToDelete);
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
