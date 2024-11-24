package cn.amatrix.utils.messageCache;

import cn.amatrix.model.users.PrivateMessage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于存储发送者和接收者之间私信的缓存。
 */
public class PrivateMessageCache implements Serializable {
    private static final long serialVersionUID = 1L;

    private int senderId;
    private int receiverId;
    private List<PrivateMessage> messages;

    /**
     * 构造一个特定发送者和接收者的 PrivateMessageCache。
     *
     * @param senderId 发送者的ID
     * @param receiverId 接收者的ID
     */
    public PrivateMessageCache(int senderId, int receiverId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.messages = new ArrayList<>();
    }

    /**
     * 如果发送者和接收者ID匹配，则将消息添加到缓存中。
     *
     * @param message 要添加的私信
     * @throws IllegalArgumentException 如果发送者或接收者ID与缓存不匹配
     */
    public void addMessage(PrivateMessage message) {
        if (message.getSenderId() == senderId && message.getReceiverId() == receiverId) {
            messages.add(message);
        } else {
            throw new IllegalArgumentException("消息的发送者或接收者ID与缓存不匹配。");
        }
    }

    /**
     * 将消息序列化到文件中。
     *
     * @param filePath 序列化消息的文件路径
     * @throws IOException 如果发生I/O错误
     */
    public void serializeMessages(String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(messages);
        }
    }

    /**
     * 将消息序列化到具有生成文件名的文件中。
     *
     * @param outputPath 保存序列化文件的目录
     * @return 生成的文件名
     * @throws IOException 如果发生I/O错误
     */
    public String serializeMessagesWithGeneratedFilename(String outputPath) throws IOException {
        String fileName = outputPath + File.separator + "privateMessages_" + senderId + "_" + receiverId + ".ser";
        serializeMessages(fileName);
        return fileName;
    }

    /**
     * 从文件中反序列化消息。
     *
     * @param filePath 反序列化消息的文件路径
     * @throws IOException 如果发生I/O错误
     * @throws ClassNotFoundException 如果找不到序列化对象的类
     */
    @SuppressWarnings("unchecked")
    public void deserializeMessages(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            messages = (List<PrivateMessage>) ois.readObject();
        }
    }

    /**
     * 返回缓存中的所有消息。
     *
     * @return 所有私信的列表
     */
    public List<PrivateMessage> getAllMessages() {
        return messages;
    }

    /**
     * 返回发送者的ID。
     *
     * @return 发送者的ID
     */
    public int getSenderId() {
        return senderId;
    }

    /**
     * 返回接收者的ID。
     *
     * @return 接收者的ID
     */
    public int getReceiverId() {
        return receiverId;
    }

    /**
     * 返回序列化文件的文件名。
     *
     * @param outputPath 保存序列化文件的目录
     * @return 序列化文件的文件名
     */
    public String getSerializedFilename(String outputPath) {
        return outputPath + File.separator + "privateMessages_" + senderId + "_" + receiverId + ".ser";
    }

}
