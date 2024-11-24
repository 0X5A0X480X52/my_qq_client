package cn.amatrix.utils.messageCache;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.io.File;

public class MessageCacheManager {
    private Map<String, PrivateMessageCache> privateMessageCaches;
    private Map<Integer, GroupMessageCache> groupMessageCaches;
    private Map<String, String> privateMessageCacheFiles;
    private Map<Integer, String> groupMessageCacheFiles;
    private String defaultOutputPath;
    private String privateMessageCacheDir = "privateMessage";
    private String groupMessageCacheDir = "groupMessage";

    public MessageCacheManager(String defaultOutputPath) {
        this.defaultOutputPath = defaultOutputPath;
        privateMessageCaches = new HashMap<>();
        groupMessageCaches = new HashMap<>();
        privateMessageCacheFiles = new HashMap<>();
        groupMessageCacheFiles = new HashMap<>();
    }

    /**
     * 添加私信缓存并序列化到文件中。
     *
     * @param cache 私信缓存
     * @throws IOException 如果发生I/O错误
     */
    public void addPrivateMessageCache(PrivateMessageCache cache) throws IOException {
        String outputPath = defaultOutputPath + File.separator + this.privateMessageCacheDir;
        String key = cache.getSenderId() + "_" + cache.getReceiverId();
        privateMessageCaches.put(key, cache);
        String filename = cache.getSerializedFilename(outputPath);
        cache.serializeMessages(filename);
        privateMessageCacheFiles.put(key, filename);
    }

    /**
     * 获取特定发送者和接收者的私信缓存。
     *
     * @param senderId 发送者的ID
     * @param receiverId 接收者的ID
     * @return 私信缓存
     */
    public PrivateMessageCache getPrivateMessageCache(int senderId, int receiverId) {
        String key = senderId + "_" + receiverId;
        return privateMessageCaches.get(key);
    }

    /**
     * 添加群组消息缓存并序列化到文件中。
     *
     * @param cache 群组消息缓存
     * @throws IOException 如果发生I/O错误
     */
    public void addGroupMessageCache(GroupMessageCache cache) throws IOException {
        String outputPath = defaultOutputPath + File.separator + this.groupMessageCacheDir;
        groupMessageCaches.put(cache.getGroupId(), cache);
        String filename = cache.getSerializedFilename(outputPath);
        cache.serializeMessages(filename);
        groupMessageCacheFiles.put(cache.getGroupId(), filename);
    }

    /**
     * 获取特定群组的群组消息缓存。
     *
     * @param groupId 群组的ID
     * @return 群组消息缓存
     */
    public GroupMessageCache getGroupMessageCache(int groupId) {
        return groupMessageCaches.get(groupId);
    }

    /**
     * 获取特定发送者和接收者的私信缓存文件路径。
     *
     * @param senderId 发送者的ID
     * @param receiverId 接收者的ID
     * @return 私信缓存文件路径
     */
    public String getPrivateMessageCacheFile(int senderId, int receiverId) {
        String key = senderId + "_" + receiverId;
        return privateMessageCacheFiles.get(key);
    }

    /**
     * 获取特定群组的群组消息缓存文件路径。
     *
     * @param groupId 群组的ID
     * @return 群组消息缓存文件路径
     */
    public String getGroupMessageCacheFile(int groupId) {
        return groupMessageCacheFiles.get(groupId);
    }

    /**
     * 序列化缓存管理器到文件中。
     *
     * @param filePath 序列化文件路径
     * @throws IOException 如果发生I/O错误
     */
    public void serializeCacheManager(String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(privateMessageCacheFiles);
            oos.writeObject(groupMessageCacheFiles);
        }
    }

    /**
     * 从文件中反序列化缓存管理器。
     *
     * @param filePath 反序列化文件路径
     * @throws IOException 如果发生I/O错误
     * @throws ClassNotFoundException 如果找不到序列化对象的类
     */
    @SuppressWarnings("unchecked")
    public void deserializeCacheManager(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            privateMessageCacheFiles = (Map<String, String>) ois.readObject();
            groupMessageCacheFiles = (Map<Integer, String>) ois.readObject();
            privateMessageCaches = new HashMap<>();
            groupMessageCaches = new HashMap<>();
            for (Map.Entry<String, String> entry : privateMessageCacheFiles.entrySet()) {
                PrivateMessageCache cache = new PrivateMessageCache(
                    Integer.parseInt(entry.getKey().split("_")[0]),
                    Integer.parseInt(entry.getKey().split("_")[1])
                );
                cache.deserializeMessages(entry.getValue());
                privateMessageCaches.put(entry.getKey(), cache);
            }
            for (Map.Entry<Integer, String> entry : groupMessageCacheFiles.entrySet()) {
                GroupMessageCache cache = new GroupMessageCache(entry.getKey());
                cache.deserializeMessages(entry.getValue());
                groupMessageCaches.put(entry.getKey(), cache);
            }
        }
    }
}
