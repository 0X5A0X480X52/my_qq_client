package cn.amatrix.DAO.users.Imp;

import cn.amatrix.model.users.PrivateMessage;
import java.util.List;

public interface PrivateMessageDAOImp {
    PrivateMessage getPrivateMessageById(int messageId) throws Exception;
    void addPrivateMessage(PrivateMessage message) throws Exception;
    void updatePrivateMessage(PrivateMessage message) throws Exception;
    void deletePrivateMessage(int messageId) throws Exception;
    List<PrivateMessage> getAllPrivateMessages() throws Exception;
    List<PrivateMessage> getPrivateMessagesBySenderAndReceiver(int senderId, int receiverId) throws Exception;
    List<PrivateMessage> getPrivateMessagesBySender(int senderId) throws Exception;
    List<PrivateMessage> getPrivateMessagesByReceiver(int receiverId) throws Exception;
}
