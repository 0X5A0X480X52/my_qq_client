package cn.amatrix.DAO.users.Imp;

import cn.amatrix.model.users.Friend;
import java.util.List;

public interface FriendDAOImp {
    Friend getFriendById(int userId, int friendId) throws Exception;
    void addFriend(Friend friend) throws Exception;
    void updateFriend(Friend friend) throws Exception;
    void deleteFriend(int userId, int friendId) throws Exception;
    List<Friend> getAllFriends() throws Exception;
    List<Friend> getFriendsByUserId(int userId) throws Exception;
}
