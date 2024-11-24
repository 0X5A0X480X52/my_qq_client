package cn.amatrix.utils.messageCache;

import cn.amatrix.model.groups.GroupMessage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于存储群组消息的缓存。
 */
public class GroupMessageCache implements Serializable {
    private static final long serialVersionUID = 1L;

    private int groupId;
    private List<GroupMessage> messages;

    /**
     * 构造一个特定群组的 GroupMessageCache。
     *
     * @param groupId 群组的ID
     */
    public GroupMessageCache(int groupId) {
        this.groupId = groupId;
        this.messages = new ArrayList<>();
    }

    /**
     * 如果群组ID匹配，则将消息添加到缓存中。
     *
     * @param message 要添加的群组消息
     * @throws IllegalArgumentException 如果群组ID与缓存不匹配
     */
    public void addMessage(GroupMessage message) {
        if (message.getGroupId() == groupId) {
            messages.add(message);
        } else {
            throw new IllegalArgumentException("消息的群组ID与缓存不匹配。");
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
        String fileName = outputPath + File.separator + "groupMessages_" + groupId + ".ser";
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
            messages = (List<GroupMessage>) ois.readObject();
        }
    }

    /**
     * 返回缓存中的所有消息。
     *
     * @return 所有群组消息的列表
     */
    public List<GroupMessage> getAllMessages() {
        return messages;
    }

    /**
     * 返回群组的ID。
     *
     * @return 群组的ID
     */
    public int getGroupId() {
        return groupId;
    }

    /**
     * 返回序列化文件的文件名。
     *
     * @param outputPath 保存序列化文件的目录
     * @return 序列化文件的文件名
     */
    public String getSerializedFilename(String outputPath) {
        return outputPath + File.separator + "groupMessages_" + groupId + ".ser";
    }
}
