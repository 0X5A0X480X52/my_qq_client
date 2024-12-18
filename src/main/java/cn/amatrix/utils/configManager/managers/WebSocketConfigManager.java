package cn.amatrix.utils.configManager.managers;

import cn.amatrix.utils.configManager.ConfigManager;

public class WebSocketConfigManager {
    private static final String DEFALT_URI = "ws://localhost:8080/demo_webapp";

    public static String getURI() {
        String uri = ConfigManager.getProperty("websocket.uri");
        if ( uri == null) {
            System.out.println("websocket.uri is null");
            uri = DEFALT_URI;
            System.out.println("websocket.uri is set to default: " + uri);
        }
        return uri;
    }

}