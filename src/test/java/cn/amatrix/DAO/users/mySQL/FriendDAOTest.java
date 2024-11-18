package cn.amatrix.DAO.users.mySQL;

import cn.amatrix.model.users.Friend;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FriendDAOTest {

    @Test
    public void testGetAllFriends() throws SQLException {
        FriendDAO friendDAO = new FriendDAO();
        List<Friend> friends = friendDAO.getAllFriends();
        assertNotNull(friends);
        assertTrue(friends.size() > 0);
    }

    @Test
    public void testAddFriend() throws SQLException {
        FriendDAO friendDAO = new FriendDAO();
        Friend friend = new Friend();
        friend.setUserId(1);
        friend.setFriendId(2);
        friend.setAddedAt(new Timestamp(System.currentTimeMillis()));
        friendDAO.addFriend(friend);

        List<Friend> retrievedFriends = friendDAO.getFriendsByUserId(friend.getUserId());
        assertNotNull(retrievedFriends);
        assertTrue(retrievedFriends.size() > 0);
    }

    @Test
    public void testGetFriendById() throws SQLException {
        FriendDAO friendDAO = new FriendDAO();
        Friend friend = friendDAO.getFriendById(1, 2);
        assertNotNull(friend);
        assertEquals(1, friend.getUserId());
        assertEquals(2, friend.getFriendId());
    }

    @Test
    public void testUpdateFriend() throws SQLException {
        FriendDAO friendDAO = new FriendDAO();
        Friend friend = friendDAO.getFriendById(1, 2);
        friend.setAddedAt(new Timestamp(System.currentTimeMillis()));
        friendDAO.updateFriend(friend);

        Friend updatedFriend = friendDAO.getFriendById(1, 2);
        assertEquals(friend.getAddedAt(), updatedFriend.getAddedAt());
    }

    @Test
    public void testDeleteFriend() throws SQLException {
        FriendDAO friendDAO = new FriendDAO();
        friendDAO.deleteFriend(1, 2);

        Friend friend = friendDAO.getFriendById(1, 2);
        assertNull(friend);
    }
}
