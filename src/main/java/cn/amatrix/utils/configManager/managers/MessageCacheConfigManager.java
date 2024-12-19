package cn.amatrix.utils.configManager.managers;

import cn.amatrix.utils.configManager.ConfigManager;

public class MessageCacheConfigManager {

    private static final String DEFALT_MESSAGECACHE_DIR = "/messageCache";

    public static String getDefaultOutputPathDir() {
        String messageCacheDir = ConfigManager.getProperty("messageCache.defaultOutputPathDir");
        if (messageCacheDir == null) {
            System.out.println("messageCache.defaultOutputPathDir is null");
            messageCacheDir = DEFALT_MESSAGECACHE_DIR;
            System.out.println("messageCache.defaultOutputPathDir is set to default: " + messageCacheDir);
        }
        return messageCacheDir;
    }
}
