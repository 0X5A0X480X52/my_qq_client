package cn.amatrix.DAO.groups.http;

import cn.amatrix.model.groups.GroupJoinRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class GroupJoinRequestDAOTest {
    private GroupJoinRequestDAO groupJoinRequestDAO;

    @BeforeEach
    public void setUp() {
        groupJoinRequestDAO = new GroupJoinRequestDAO();
    }

    @Test
    public void testAddAndGetGroupJoinRequest() throws Exception {
        GroupJoinRequest request = new GroupJoinRequest();
        request.setGroupId(1);
        request.setUserId(1);
        request.setRequestedAt(new Timestamp(System.currentTimeMillis()));
        groupJoinRequestDAO.addGroupJoinRequest(request);

        GroupJoinRequest retrievedRequest = groupJoinRequestDAO.getGroupJoinRequestById(request.getRequestId());
        assertNotNull(retrievedRequest);
        assertEquals(1, retrievedRequest.getGroupId());
        assertEquals(1, retrievedRequest.getUserId());
    }

    @Test
    public void testUpdateGroupJoinRequest() throws Exception {
        GroupJoinRequest request = new GroupJoinRequest();
        request.setGroupId(1);
        request.setUserId(1);
        request.setRequestedAt(new Timestamp(System.currentTimeMillis()));
        groupJoinRequestDAO.addGroupJoinRequest(request);

        GroupJoinRequest retrievedRequest = groupJoinRequestDAO.getGroupJoinRequestById(request.getRequestId());
        retrievedRequest.setRequestStatus("Approved");
        groupJoinRequestDAO.updateGroupJoinRequest(retrievedRequest);

        GroupJoinRequest updatedRequest = groupJoinRequestDAO.getGroupJoinRequestById(retrievedRequest.getRequestId());
        assertNotNull(updatedRequest);
        assertEquals("Approved", updatedRequest.getRequestStatus());
    }

    @Test
    public void testDeleteGroupJoinRequest() throws Exception {
        GroupJoinRequest request = new GroupJoinRequest();
        request.setGroupId(1);
        request.setUserId(1);
        request.setRequestedAt(new Timestamp(System.currentTimeMillis()));
        groupJoinRequestDAO.addGroupJoinRequest(request);

        GroupJoinRequest retrievedRequest = groupJoinRequestDAO.getGroupJoinRequestById(request.getRequestId());
        groupJoinRequestDAO.deleteGroupJoinRequestById(retrievedRequest.getRequestId());

        GroupJoinRequest deletedRequest = groupJoinRequestDAO.getGroupJoinRequestById(retrievedRequest.getRequestId());
        assertNull(deletedRequest);
    }

    @Test
    public void testGetAllGroupJoinRequests() throws Exception {
        List<GroupJoinRequest> requests = groupJoinRequestDAO.getAllGroupJoinRequests();
        assertNotNull(requests);
        assertTrue(requests.size() > 0);
    }
}
