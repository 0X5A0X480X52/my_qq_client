package cn.amatrix.controller.chatDemo.test.users;

import javax.swing.*;

import com.formdev.flatlaf.FlatLightLaf;

import cn.amatrix.controller.chatDemo.commponent.MessagePanel;
import cn.amatrix.model.message.Message;
import cn.amatrix.model.users.PrivateMessage;
import cn.amatrix.model.users.User;
import cn.amatrix.service.chatMessage.ChatMessageService;
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
public class ChatDemo5 extends JFrame implements WebSocketReceiver {
    private JPanel chatPanel;
    private JScrollPane scrollPane;
    private InputPanel inputPanel;

    private WebSocketClient webSocketClient;
    private ChatMessageService chatMessageService;

    private User currentUser;
    private User targetUser;

    public ChatDemo5( WebSocketClient webSocketClient, User currentUser, User targetUser) {

        this.webSocketClient = webSocketClient;
        this.currentUser = currentUser;
        this.targetUser = targetUser;

        try {
            this.chatMessageService = new ChatMessageService(this.webSocketClient);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Chat Demo");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setResizeWeight(0.7); // 设置顶部占页面70%
        add(splitPane);

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

        // loadCacheMessage();

        inputPanel = new InputPanel(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = inputPanel.getInputText();
                if (!message.isEmpty()) {
                    try {
                        String targetUserId = String.valueOf(targetUser.getUser_id());
                        String currentUserId = String.valueOf(currentUser.getUser_id());
                        chatMessageService.sendPrivateMessage(message, currentUserId, targetUserId);

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
                    // chatPanel.revalidate();
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

                        if (message.getType().equals("PrivateMessage")) {
                            var status = chatMessageService.handleReceivedPrivateMessage(message);

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
                            // chatPanel.revalidate();

                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                } 
            }
        });

        
        splitPane.add(scrollPane, JSplitPane.TOP); // 将 scrollPane 添加到 splitPane 的顶部
        splitPane.add(inputPanel, JSplitPane.BOTTOM);
        // add(scrollPane, BorderLayout.CENTER);
        // add(inputPanel, BorderLayout.SOUTH);


        loadCacheMessage();
        
        String targetUserId = String.valueOf(currentUser.getUser_id());
        String currentUserId = String.valueOf(currentUser.getUser_id());
        String infoString = "系统消息： " + currentUser.getUsername() + " 已经上线";
        try {
            chatMessageService.sendPrivateMessage(infoString, currentUserId, targetUserId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCacheMessage() {
        List<PrivateMessage> messages = chatMessageService.getPrivateMessageCache(currentUser.getUser_id(), targetUser.getUser_id(), 10);

        try {
            for (PrivateMessage message : messages) {
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
    }

    public static void main(String[] args) {
        try {
            // 使用相对路径加载主题文件
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        URI uri = URI.create("ws://47.97.117.157:8080/demo_webapp/chat");
        WebSocketClient client1 = new WebSocketClient(uri);

        SignInService signInService1 = new SignInService(client1);
        signInService1.submitSignInInformation("Anon", "3432900546@qq.com", "123456");

        UserService userService = new UserService();

        User user1 = userService.getUserById(1);
        User user2 = userService.getUserById(2);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ChatDemo5 window2 = new ChatDemo5(client1, user1, user2);

                window2.setTitle("Chat Demo - User2");

                window2.setVisible(true);
            }
        });
    }
}
