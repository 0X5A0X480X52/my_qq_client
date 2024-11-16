package cn.amatrix.DAO.users.mySQL;

import cn.amatrix.model.users.User;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {

    @Test
    public void testGetAllUsers() throws SQLException {
        UserDAO userDAO = new UserDAO();
        List<User> users = userDAO.getAllUsers();
        assertNotNull(users);
        assertTrue(users.size() > 0);
    }

    @Test
    public void testAddUser() throws SQLException {
        UserDAO userDAO = new UserDAO();
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("testuser@example.com");
        userDAO.addUser(user);

        User retrievedUser = userDAO.getUserByUsername(user.getUsername());
        assertNotNull(retrievedUser);
        assertEquals("testuser", retrievedUser.getUsername());
    }

    @Test
    public void testGetUserById() throws SQLException {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserById(1);
        assertNotNull(user);
        assertEquals(1, user.getUser_id());
    }

    @Test
    public void testUpdateUser() throws SQLException {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserById(1);
        user.setUsername("updateduser");
        userDAO.updateUser(user);

        User updatedUser = userDAO.getUserById(1);
        assertEquals("updateduser", updatedUser.getUsername());
    }

    @Test
    public void testDeleteUser() throws SQLException {
        UserDAO userDAO = new UserDAO();
        userDAO.deleteUser(1);

        User user = userDAO.getUserById(1);
        assertNull(user);
    }
}
