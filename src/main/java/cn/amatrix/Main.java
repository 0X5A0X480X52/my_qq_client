package cn.amatrix;

import cn.amatrix.controller.Login02.LoginGUI;
import cn.amatrix.utils.configManager.managers.WebSocketConfigManager;
import cn.amatrix.utils.webSocketClient.WebSocketClient;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.net.URI;

public class Main {
    public static void main(String[] args) {
        try {
            // 使用相对路径加载主题文件
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String uriStr = WebSocketConfigManager.getURI();
        URI uri = URI.create(uriStr);
        WebSocketClient client = new WebSocketClient(uri);
        SwingUtilities.invokeLater(() -> new LoginGUI(client));
    }
}
