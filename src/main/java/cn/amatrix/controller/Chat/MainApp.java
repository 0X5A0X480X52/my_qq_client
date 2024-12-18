package cn.amatrix.controller.Chat;

import cn.amatrix.model.users.User;
import cn.amatrix.service.signIn.SignInService;
import cn.amatrix.service.users.UserService;
import cn.amatrix.utils.webSocketClient.WebSocketClient;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.net.URI;

public class MainApp {
    public static void main(String[] args) {

        try {
            // 使用相对路径加载主题文件
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }


        // 创建并显示主窗口
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("QQ");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 600);
            frame.setMinimumSize(new Dimension(700, 500)); // 设置最小窗口大小
            frame.setLayout(new BorderLayout());

            URI uri = URI.create("ws://47.97.117.157:8080/demo_webapp/chat");
            // URI uri = URI.create("ws://localhost:1145/demo_webapp/chat");
            WebSocketClient client = new WebSocketClient(uri);

            SignInService signInService = new SignInService(client);
            signInService.submitSignInInformation("Anon", "3432900546@qq.com", "123456");

            UserService userService = new UserService();
            User user = userService.getUserById(1);
            QQ contentPanel = new QQ(client, user);

            frame.add(contentPanel, BorderLayout.CENTER);

            // 显示窗口
            frame.setVisible(true);
        });
    }
}


