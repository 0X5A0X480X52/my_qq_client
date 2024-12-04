package cn.amatrix.DAO.users.Imp;

import cn.amatrix.model.users.User;
import java.util.List;

public interface UserDAOImp {
    User getUserById(int userId) throws Exception;
    User getUserByUsername(String username) throws Exception;
    User getUserByEmail(String email) throws Exception;
    void addUser(User user) throws Exception;
    void updateUser(User user) throws Exception;
    void deleteUser(int userId) throws Exception;
    List<User> getAllUsers() throws Exception;
}
