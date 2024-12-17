package cn.amatrix.controller.chatDemo;

import javax.swing.*;

import com.formdev.flatlaf.FlatLightLaf;

import cn.amatrix.controller.chatDemo.commponent.MessagePanel;
import cn.amatrix.model.groups.Group;
import cn.amatrix.model.groups.GroupMessage;
import cn.amatrix.model.message.Message;
import cn.amatrix.model.users.User;
import cn.amatrix.service.chatMessage.ChatMessageService;
import cn.amatrix.service.groups.GroupService;
import cn.amatrix.service.signIn.SignInService;
import cn.amatrix.service.users.UserService;
import cn.amatrix.utils.webSocketClient.WebSocketClient;
import cn.amatrix.utils.webSocketClient.WebSocketReceiver;
import cn.amatrix.utils.webSocketClient.receivedWebSocketMessage.ReceivedWebSocketMessageEvent;
import cn.amatrix.utils.webSocketClient.receivedWebSocketMessage.ReceivedWebSocketMessageEventListener;
import cn.amatrix.controller.chatDemo.commponent.InputPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URI;
import java.util.List;

public class GroupChatPanel extends JPanel implements WebSocketReceiver {
    private JPanel chatPanel;
    private JScrollPane scrollPane;
    private InputPanel inputPanel;

    private WebSocketClient webSocketClient;
    private ChatMessageService chatMessageService;

    private User currentUser;
    private Group targetGroup;

    public GroupChatPanel(WebSocketClient webSocketClient, User currentUser, Group targetGroup) {

        this.webSocketClient = webSocketClient;
        this.currentUser = currentUser;
        this.targetGroup = targetGroup;

        try {
            this.chatMessageService = new ChatMessageService(this.webSocketClient);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setResizeWeight(0.7); // 设置顶部占页面70%
        add(splitPane, BorderLayout.CENTER);

        chatPanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(scrollPane.getViewport().getWidth(), super.getPreferredSize().height);
            }
        };
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(chatPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); // 始终显示垂直滚动条

        // 添加 ComponentListener 以调整 chatPanel 的宽度
        scrollPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                chatPanel.setPreferredSize(new Dimension(scrollPane.getViewport().getWidth(), chatPanel.getPreferredSize().height));
                chatPanel.revalidate();
            }
        });

        inputPanel = new InputPanel(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = inputPanel.getInputText();
                if (!message.isEmpty()) {
                    try {
                        chatMessageService.sendGroupMessage(message, currentUser, targetGroup);

                        addMessagePanel(currentUser, message);

                        // 滚动到最下方
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
                                verticalBar.setValue(verticalBar.getMaximum());
                            }
                        });
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    inputPanel.clearInputField();
                }
            }
        });

        addReceivedWebSocketMessageEventListener(new ReceivedWebSocketMessageEventListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (e instanceof ReceivedWebSocketMessageEvent) {
                        ReceivedWebSocketMessageEvent event = (ReceivedWebSocketMessageEvent)e;
                        Message message = event.getMessage();

                        if (message.getType().equals("GroupMessage")) {
                            var status = chatMessageService.handleReceivedGroupMessage(message);

                            int SenderId = status.getSender_id();
                            UserService userService = new UserService();
                            String infoString = status.getInfoString();

                            User sender = userService.getUserById(SenderId);

                            addMessagePanel(sender, infoString);

                            // 滚动到最下方
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
                                    verticalBar.setValue(verticalBar.getMaximum());
                                }
                            });
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                } 
            }
        });

        splitPane.add(scrollPane, JSplitPane.TOP); // 将 scrollPane 添加到 splitPane 的顶部
        splitPane.add(inputPanel, JSplitPane.BOTTOM);

        loadCacheMessage();

        String infoString = "系统消息： " + currentUser.getUsername() + " 已经上线";
        try {
            chatMessageService.sendGroupMessage(infoString, currentUser, targetGroup);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCacheMessage() {
        List<GroupMessage> messages = chatMessageService.getGroupMessageCache(targetGroup.getGroupId());

        try {
            for (GroupMessage message : messages) {
                int senderId = message.getSenderId();
                UserService userService = new UserService();
                User senderUser = userService.getUserById(senderId);
                String infoString = message.getMessage();

                addMessagePanel(senderUser, infoString);

                // 滚动到最下方
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
                        verticalBar.setValue(verticalBar.getMaximum());
                    }
                });
                chatPanel.revalidate();
                chatPanel.repaint(); // 确保重新绘制组件
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addMessagePanel(User user, String infoString) throws Exception {
        String avatar = user.getAvatar();
        String username = user.getUsername();

        MessagePanel messagePanel = new MessagePanel(avatar, infoString, username, chatPanel);
        if (user.getUser_id() == currentUser.getUser_id()) {
            messagePanel.setType(MessagePanel.Type.OWN);
        } else {
            messagePanel.setType(MessagePanel.Type.OTHER);
        }

        chatPanel.add(messagePanel);
        chatPanel.setPreferredSize(new Dimension(chatPanel.getPreferredSize().width, chatPanel.getPreferredSize().height + messagePanel.getPreferredSize().height));

        chatPanel.revalidate();
        chatPanel.repaint(); // 确保重新绘制组件
    }

    public static void main(String[] args) {
        try {
            // 使用相对路径加载主题文件
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // URI uri = URI.create("ws://47.97.117.157:8080/demo_webapp/chat");
        URI uri = URI.create("ws://localhost:1145/demo_webapp/chat");
        WebSocketClient client1 = new WebSocketClient(uri);

        SignInService signInService1 = new SignInService(client1);
        signInService1.submitSignInInformation("Anon", "3432900546@qq.com", "123456");

        UserService userService = new UserService();
        GroupService groupService = new GroupService();

        Group targetGroup = groupService.getGroupById(1);
        User user1 = userService.getUserById(1);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Chat Demo - User2");
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
