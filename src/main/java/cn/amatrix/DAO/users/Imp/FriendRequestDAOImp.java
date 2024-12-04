package cn.amatrix.DAO.users.Imp;

import cn.amatrix.model.users.FriendRequest;
import java.util.List;

public interface FriendRequestDAOImp {
    FriendRequest getFriendRequestById(int requestId) throws Exception;
    void addFriendRequest(FriendRequest request) throws Exception;
    void updateFriendRequest(FriendRequest request) throws Exception;
    void deleteFriendRequest(int requestId) throws Exception;
    List<FriendRequest> getAllFriendRequests() throws Exception;
    List<FriendRequest> getFriendRequestsBySender(int senderId) throws Exception;
    List<FriendRequest> getFriendRequestsByReceiver(int receiverId) throws Exception;
}
