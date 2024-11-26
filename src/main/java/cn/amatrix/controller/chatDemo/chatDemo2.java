package cn.amatrix.controller.chatDemo;

import cn.amatrix.model.groups.Group;
import cn.amatrix.model.message.Message;
import cn.amatrix.model.users.User;
import cn.amatrix.service.chatMessage.ChatMessageService;
import cn.amatrix.service.signIn.SignInService;
import cn.amatrix.utils.webSocketClient.WebSocketClient;
import cn.amatrix.utils.webSocketClient.WebSocketReceiver;
import cn.amatrix.utils.webSocketClient.receivedWebSocketMessage.ReceivedWebSocketMessageEvent;
import cn.amatrix.utils.webSocketClient.receivedWebSocketMessage.ReceivedWebSocketMessageEventListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

public class chatDemo2 extends JFrame implements WebSocketReceiver {
    private static final Logger logger = Logger.getLogger(chatDemo2.class.getName());

    private JTextField messageField;
    private JTextArea chatArea;
    private JButton sendButton;
    private JComboBox<String> messageTypeComboBox;
    private JTextField receiverField;

    private ChatMessageService chatMessageService;
    private SignInService signInService;
    private User currentUser;

    public chatDemo2() {
        URI uri = URI.create("ws://localhost:1145/demo_webapp/chat");
        WebSocketClient client = new WebSocketClient(uri);

        try {
            chatMessageService = new ChatMessageService(client);
        } catch (Exception e) {
            System.out.println(e);
        }

        signInService = new SignInService(client);
        signInService.submitSignInInformation("1234", "123@134.com", "123456");
        
        // 17 is the user id of the current user
        currentUser = new User();
        currentUser.setUser_id(17);
        currentUser.setUsername("CurrentUser");

        setTitle("Chat Demo2");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        // 读取缓存
        var cache = chatMessageService.getGroupMessages(1);
        for (var message : cache) {
            chatArea.append("History: " + message.toJson() + "\n");
            System.out.println(message.toJson());
        }
        var privateCache = chatMessageService.getPrivateMessages(17, 1);
        for (var message : privateCache) {
            chatArea.append("History: " + message.toJson() + "\n");
            System.out.println(message.toJson());
        }

        JPanel panel = new JPanel(new BorderLayout());
        messageField = new JTextField();
        panel.add(messageField, BorderLayout.CENTER);

        sendButton = new JButton("Send");
        panel.add(sendButton, BorderLayout.EAST);

        JPanel topPanel = new JPanel(new GridLayout(1, 3));
        messageTypeComboBox = new JComboBox<>(new String[]{"PrivateMessage", "GroupMessage"});
        receiverField = new JTextField();
        topPanel.add(messageTypeComboBox);
        topPanel.add(new JLabel("Receiver ID:"));
        topPanel.add(receiverField);

        add(topPanel, BorderLayout.NORTH);
        add(panel, BorderLayout.SOUTH);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String messageType = (String) messageTypeComboBox.getSelectedItem();
                String messageContent = messageField.getText();
                int receiverId = Integer.parseInt(receiverField.getText());

                try {
                    if ("PrivateMessage".equals(messageType)) {
                        User receiver = new User();
                        receiver.setUser_id(receiverId);
                        chatMessageService.sendPrivateMessage(messageContent, currentUser, receiver);
                    } else if ("GroupMessage".equals(messageType)) {
                        Group group = new Group();
                        group.setGroupId(receiverId);
                        chatMessageService.sendGroupMessage(messageContent, currentUser, group);
                    }
                    chatArea.append("Sent: " + messageContent + "\n");
                    messageField.setText("");
                } catch (IOException ex) {
                    logger.log(Level.SEVERE, "Failed to send message", ex);
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
                        logger.log(Level.INFO, "Received WebSocket message: " + message.toJson());

                        if (message.getType().equals("GroupMessage")) {

                            var status = chatMessageService.handleReceivedGroupMessage(message);
                            String info = "{GroupId: " + status.getReceiver_id() + ", SenderId:"  + status.getSender_id() + ", message:" + status.getInfoString() + ", Info: " + status.getAdditionalInfo() + '}';
                            // JOptionPane.showMessageDialog(null, info );
                            logger.log(Level.INFO, "WebSocket received: " + info);
                            chatArea.append("Get: " + info + "\n");

                        } else if (message.getType().equals("PrivateMessage")) {
                            var status = chatMessageService.handleReceivedPrivateMessage(message);
                            String info = "{ReceiverId: " + status.getReceiver_id() + ", SenderId:"  + status.getSender_id() + ", message:" + status.getInfoString() + ", Info: " + status.getAdditionalInfo() + '}';
                            // JOptionPane.showMessageDialog(null, info );
                            logger.log(Level.INFO, "WebSocket received: " + info);
                            chatArea.append("Get: " + info + "\n");
                        }
                    }
                } catch (Exception ex) {
                    logger.log(Level.SEVERE, "Error processing WebSocket message", ex);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new chatDemo2().setVisible(true);
                logger.log(Level.INFO, "chatDemo application started");
            }
        });
    }
}
