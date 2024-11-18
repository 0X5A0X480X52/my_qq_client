package cn.amatrix.DAO.users.mySQL;

import cn.amatrix.model.users.FriendRequest;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FriendRequestDAOTest {

    @Test
    public void testGetAllFriendRequests() throws SQLException {
        FriendRequestDAO requestDAO = new FriendRequestDAO();
        List<FriendRequest> requests = requestDAO.getAllFriendRequests();
        assertNotNull(requests);
        assertTrue(requests.size() > 0);
    }

    @Test
    public void testAddFriendRequest() throws SQLException {
        FriendRequestDAO requestDAO = new FriendRequestDAO();
        FriendRequest request = new FriendRequest();
        request.setSenderId(1);
        request.setReceiverId(3);
        request.setRequestMessage("Please add me as a friend.");
        request.setRequestedAt(new Timestamp(System.currentTimeMillis()));
        request.setRequestStatus("Pending");
        requestDAO.addFriendRequest(request);

        List<FriendRequest> retrievedRequests = requestDAO.getFriendRequestsBySender(request.getSenderId());
        assertNotNull(retrievedRequests);
        assertTrue(retrievedRequests.size() > 0);
    }

    @Test
    public void testGetFriendRequestById() throws SQLException {
        FriendRequestDAO requestDAO = new FriendRequestDAO();
        FriendRequest request = requestDAO.getFriendRequestById(5);
        assertNotNull(request);
        assertEquals(5, request.getRequestId());
    }

    @Test
    public void testUpdateFriendRequest() throws SQLException {
        FriendRequestDAO requestDAO = new FriendRequestDAO();
        FriendRequest request = requestDAO.getFriendRequestById(5);
        request.setRequestMessage("Updated request message");
        request.setRequestStatus("Accepted");
        requestDAO.updateFriendRequest(request);

        FriendRequest updatedRequest = requestDAO.getFriendRequestById(5);
        assertEquals("Updated request message", updatedRequest.getRequestMessage());
        assertEquals("Accepted", updatedRequest.getRequestStatus());
    }

    @Test
    public void testDeleteFriendRequest() throws SQLException {
        FriendRequestDAO requestDAO = new FriendRequestDAO();
        requestDAO.deleteFriendRequest(6);

        FriendRequest request = requestDAO.getFriendRequestById(6);
        assertNull(request);
    }
}
