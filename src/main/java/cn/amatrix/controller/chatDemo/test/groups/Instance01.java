package cn.amatrix.controller.chatDemo.test.groups;

import java.net.URI;
import com.formdev.flatlaf.FlatLightLaf;
import cn.amatrix.controller.chatDemo.GroupChatPanel;

import cn.amatrix.model.groups.Group;
import cn.amatrix.model.users.User;
import cn.amatrix.service.groups.GroupService;
import cn.amatrix.service.signIn.SignInService;
import cn.amatrix.service.users.UserService;
import cn.amatrix.utils.webSocketClient.WebSocketClient;
import cn.amatrix.utils.configManager.managers.WebSocketConfigManager;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Instance01 {
        public static void main(String[] args) {
        try {
            // 使用相对路径加载主题文件
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // URI uri = URI.create("ws://47.97.117.157:8080/demo_webapp/chat");
        String uriStr = WebSocketConfigManager.getURI();
        URI uri = URI.create(uriStr);
        WebSocketClient client1 = new WebSocketClient(uri);

        SignInService signInService1 = new SignInService(client1);
        signInService1.submitSignInInformation("Anon", "123@134.com", "123456");

        UserService userService = new UserService();
        GroupService groupService = new GroupService();

        Group targetGroup = groupService.getGroupById(1);
        User user1 = userService.getUserById(17);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Chat Demo - User17");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 500);
                frame.setLocationRelativeTo(null);

                GroupChatPanel chatPanel = new GroupChatPanel(client1, user1, targetGroup);
                frame.add(chatPanel);
                frame.setVisible(true);
            }
        });
    }
}

