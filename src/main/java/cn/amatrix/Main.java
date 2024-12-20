package cn.amatrix;

import cn.amatrix.controller.LoginDemo.LoginGUI;
import cn.amatrix.utils.configManager.managers.WebSocketConfigManager;
import cn.amatrix.utils.webSocketClient.WebSocketClient;

// import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.net.URI;

public class Main {
    public static void main(String[] args) {
        try {
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
