package cn.amatrix.servlet;

import cn.amatrix.DAO.groups.http.GroupDAO;
import cn.amatrix.model.groups.Group;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/groups/*")
public class GroupServlet extends HttpServlet {
    private GroupDAO groupDAO;

    @Override
    public void init() throws ServletException {
        this.groupDAO = new GroupDAO();
    }

    /**
     * 处理 GET 请求，获取群组信息
     * @param request HTTP 请求
     * @param response HTTP 响应
     * @throws ServletException 异常
     * @throws IOException 异常
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                List<Group> groups = groupDAO.getAllGroups();
                response.setContentType("application/json");
                response.getWriter().write(groups.toString());
            } else {
                String[] pathParts = pathInfo.split("/");
                if (pathParts.length == 2) {
                    int groupId = Integer.parseInt(pathParts[1]);
                    Group group = groupDAO.getGroupById(groupId);
                    response.setContentType("application/json");
                    response.getWriter().write(group.toJson());
                } else if (pathParts.length == 3 && pathParts[1].equals("name")) {
                    String groupName = pathParts[2];
                    Group group = groupDAO.getGroupByName(groupName);
                    response.setContentType("application/json");
                    response.getWriter().write(group.toJson());
                }
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 处理 POST 请求，添加新的群组
     * @param request HTTP 请求
     * @param response HTTP 响应
     * @throws ServletException 异常
     * @throws IOException 异常
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Group group = Group.fromJson(request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual));
            groupDAO.addGroup(group);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 处理 PUT 请求，更新群组信息
     * @param request HTTP 请求
     * @param response HTTP 响应
     * @throws ServletException 异常
     * @throws IOException 异常
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Group group = Group.fromJson(request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual));
            groupDAO.updateGroup(group);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 处理 DELETE 请求，删除群组
     * @param request HTTP 请求
     * @param response HTTP 响应
     * @throws ServletException 异常
     * @throws IOException 异常
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        try {
            if (pathInfo != null && pathInfo.split("/").length == 2) {
                int groupId = Integer.parseInt(pathInfo.split("/")[1]);
                groupDAO.deleteGroup(groupId);
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid group ID");
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
