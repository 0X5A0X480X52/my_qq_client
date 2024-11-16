package cn.amatrix.DAO.groups.mySQL;

import org.junit.jupiter.api.*;

import cn.amatrix.model.groups.GroupJoinRequest;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GroupJoinRequestDAOTest {
    private GroupJoinRequestDAO dao;

    @BeforeEach
    public void setUp() {
        dao = new GroupJoinRequestDAO();
    }

    @Test
    public void testAddGroupJoinRequest() throws SQLException {
        GroupJoinRequest request = new GroupJoinRequest();
        request.setGroupId(1);
        request.setUserId(1);
        request.setRequestMessage("Please let me join");
        request.setRequestStatus("pending");
        request.setRequestedAt(new Timestamp(System.currentTimeMillis()));

        dao.addGroupJoinRequest(request);

        List<GroupJoinRequest> requests = dao.getGroupJoinRequestsByGroupId(1);
        assertFalse(requests.isEmpty());
    }

    @Test
    public void testGetGroupJoinRequestsByUserId() throws SQLException {
        GroupJoinRequest request = new GroupJoinRequest();
        request.setGroupId(1);
        request.setUserId(1);
        request.setRequestMessage("Please let me join");
        request.setRequestStatus("pending");
        request.setRequestedAt(new Timestamp(System.currentTimeMillis()));

        dao.addGroupJoinRequest(request);

        List<GroupJoinRequest> requests = dao.getGroupJoinRequestsByUserId(1);
        assertFalse(requests.isEmpty());
    }

    @Test
    public void testGetAllGroupJoinRequests() throws SQLException {
        GroupJoinRequest request1 = new GroupJoinRequest();
        request1.setGroupId(1);
        request1.setUserId(1);
        request1.setRequestMessage("Please let me join");
        request1.setRequestStatus("pending");
        request1.setRequestedAt(new Timestamp(System.currentTimeMillis()));

        GroupJoinRequest request2 = new GroupJoinRequest();
        request2.setGroupId(2);
        request2.setUserId(8);
        request2.setRequestMessage("I want to join");
        request2.setRequestStatus("pending");
        request2.setRequestedAt(new Timestamp(System.currentTimeMillis()));

        dao.addGroupJoinRequest(request1);
        dao.addGroupJoinRequest(request2);

        List<GroupJoinRequest> requests = dao.getAllGroupJoinRequests();
        assertFalse(requests.isEmpty());
        assertTrue(requests.size() >= 2);
    }

    @Test
    public void testDeleteGroupJoinRequestById() throws SQLException {
        GroupJoinRequest request = new GroupJoinRequest();
        request.setGroupId(3);
        request.setUserId(1);
        request.setRequestMessage("Please let me join");
        request.setRequestStatus("pending");
        request.setRequestedAt(new Timestamp(System.currentTimeMillis()));

        dao.addGroupJoinRequest(request);

        List<GroupJoinRequest> requests = dao.getGroupJoinRequestsByGroupId(3);
        assertFalse(requests.isEmpty());

        int requestId = requests.get(0).getRequestId();
        dao.deleteGroupJoinRequestById(requestId);

        requests = dao.getGroupJoinRequestsByGroupId(3);
        assertTrue(requests.isEmpty());
    }

    @Test
    public void testUpdateGroupJoinRequest() throws SQLException {
        GroupJoinRequest request = new GroupJoinRequest();
        request.setGroupId(1);
        request.setUserId(1);
        request.setRequestMessage("Please let me join");
        request.setRequestStatus("pending");
        request.setRequestedAt(new Timestamp(System.currentTimeMillis()));

        dao.addGroupJoinRequest(request);

        List<GroupJoinRequest> requests = dao.getGroupJoinRequestsByGroupId(1);
        assertFalse(requests.isEmpty());

        GroupJoinRequest updatedRequest = requests.get(0);
        updatedRequest.setRequestMessage("Updated message");
        updatedRequest.setRequestStatus("approved");

        dao.updateGroupJoinRequest(updatedRequest);

        requests = dao.getGroupJoinRequestsByGroupId(1);
        assertEquals("Updated message", requests.get(0).getRequestMessage());
        assertEquals("approved", requests.get(0).getRequestStatus());
    }

    // ...other test methods...
}
