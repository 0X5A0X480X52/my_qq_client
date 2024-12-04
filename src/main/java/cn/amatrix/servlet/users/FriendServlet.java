package cn.amatrix.servlet.users;

import cn.amatrix.DAO.users.mySQL.FriendDAO;
import cn.amatrix.model.users.Friend;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

@WebServlet("/friends/*")
public class FriendServlet extends HttpServlet {
    private FriendDAO friendDAO;

    @Override
    public void init() throws ServletException {
        this.friendDAO = new FriendDAO();
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
                    JSONObject ids = JSON.parseObject(param);
                    int userId = ids.getIntValue("userId");
                    int friendId = ids.getIntValue("friendId");
                    Friend friendById = friendDAO.getFriendById(userId, friendId);
                    response.setContentType("application/json");
                    response.getWriter().write(friendById.toJson());
                    break;
                case "getByUserId":
                    userId = Integer.parseInt(param);
                    List<Friend> friendsByUserId = friendDAO.getFriendsByUserId(userId);
                    StringBuilder jsonBuilder = new StringBuilder("[");
                    for (int i = 0; i < friendsByUserId.size(); i++) {
                        jsonBuilder.append(friendsByUserId.get(i).toJson());
                        if (i < friendsByUserId.size() - 1) {
                            jsonBuilder.append(",");
                        }
                    }
                    jsonBuilder.append("]");
                    response.setContentType("application/json");
                    response.getWriter().write(jsonBuilder.toString());
                    break;
                case "getAll":
                    List<Friend> friends = friendDAO.getAllFriends();
                    jsonBuilder = new StringBuilder("[");
                    for (int i = 0; i < friends.size(); i++) {
                        jsonBuilder.append(friends.get(i).toJson());
                        if (i < friends.size() - 1) {
                            jsonBuilder.append(",");
                        }
                    }
                    jsonBuilder.append("]");
                    response.setContentType("application/json");
                    response.getWriter().write(jsonBuilder.toString());
                    break;
                case "add":
                    Friend friendToAdd = Friend.fromJson(param);
                    friendDAO.addFriend(friendToAdd);
                    response.setStatus(HttpServletResponse.SC_CREATED);
                    break;
                case "update":
                    Friend friendToUpdate = Friend.fromJson(param);
                    friendDAO.updateFriend(friendToUpdate);
                    response.setStatus(HttpServletResponse.SC_OK);
                    break;
                case "delete":
                    ids = JSON.parseObject(param);
                    userId = ids.getIntValue("userId");
                    friendId = ids.getIntValue("friendId");
                    friendDAO.deleteFriend(userId, friendId);
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
