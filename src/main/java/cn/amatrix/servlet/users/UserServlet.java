package cn.amatrix.servlet.users;

import cn.amatrix.DAO.users.mySQL.UserDAO;
import cn.amatrix.model.users.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

@WebServlet("/users/*")
public class UserServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        this.userDAO = new UserDAO();
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
                    int userId = Integer.parseInt(param);
                    User userById = userDAO.getUserById(userId);
                    response.setContentType("application/json");
                    response.getWriter().write(userById.toJson());
                    break;
                case "getByUsername":
                    String username = param;
                    User userByUsername = userDAO.getUserByUsername(username);
                    response.setContentType("application/json");
                    response.getWriter().write(userByUsername.toJson());
                    break;
                case "getByEmail":
                    String email = param;
                    User userByEmail = userDAO.getUserByEmail(email);
                    response.setContentType("application/json");
                    response.getWriter().write(userByEmail.toJson());
                    break;
                case "getAll":
                    List<User> users = userDAO.getAllUsers();
                    StringBuilder jsonBuilder = new StringBuilder("[");
                    for (int i = 0; i < users.size(); i++) {
                        jsonBuilder.append(users.get(i).toJson());
                        if (i < users.size() - 1) {
                            jsonBuilder.append(",");
                        }
                    }
                    jsonBuilder.append("]");
                    response.setContentType("application/json");
                    response.getWriter().write(jsonBuilder.toString());
                    break;
                case "add":
                    User userToAdd = User.fromJson(param);
                    userDAO.addUser(userToAdd);
                    response.setStatus(HttpServletResponse.SC_CREATED);
                    break;
                case "update":
                    User userToUpdate = User.fromJson(param);
                    userDAO.updateUser(userToUpdate);
                    response.setStatus(HttpServletResponse.SC_OK);
                    break;
                case "delete":
                    int userIdToDelete = Integer.parseInt(param);
                    userDAO.deleteUser(userIdToDelete);
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
