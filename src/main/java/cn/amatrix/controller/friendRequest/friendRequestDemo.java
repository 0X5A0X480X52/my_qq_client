package cn.amatrix.controller.friendRequest;

import cn.amatrix.model.users.User;
import cn.amatrix.model.users.FriendRequest;
import cn.amatrix.service.users.UserService;
import cn.amatrix.utils.base64.ImageManager;
import cn.amatrix.controller.InfoPanel.InfoPanel;
import cn.amatrix.controller.InfoPanel.user.UserInfoPanel;
import cn.amatrix.controller.friendRequest.commponent.HandleRequestDialog;
import cn.amatrix.controller.friendRequest.commponent.SendRequestDialog;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.ui.FlatRoundBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class friendRequestDemo extends JFrame {
    private UserService userService;
    private int currentUserId;
    private User currentUser;

    JPanel topPanel;
    JPanel searchPanel;
    JPanel userInfoPanel;
    JPanel bottomPanel;
    JPanel receivedRequestsPanel;
    JPanel sentRequestsPanel;

    public friendRequestDemo(int currentUserId) {

        userService = new UserService();
        this.currentUserId = currentUserId;
        this.currentUser = userService.getUserById(currentUserId);

        setTitle("好友请求管理");
        setSize(400, 600);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());

        // 上方面板：查找用户并发送好友请求
        this.topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(0, 100)); // 固定高度
        
        // 新增的面板，用于并排放置 userIdField 和 searchButton
        this.searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        JTextField userIdField = new JTextField();
        JButton searchButton = new JButton("查找用户");
        searchButton.setPreferredSize(new Dimension(120, 30)); // 固定宽度
        TitlePanel searchUserslabelPanel = new TitlePanel("查找用户");

        searchPanel.add(userIdField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);
        searchPanel.add(searchUserslabelPanel, BorderLayout.NORTH);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // 设置边距
        
        this.userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new BorderLayout()); // 设置布局管理器
        topPanel.add(searchPanel, BorderLayout.NORTH);
        topPanel.add(userInfoPanel, BorderLayout.CENTER);

        // 为 topPanel 添加滚动条
        JScrollPane topScrollPane = new JScrollPane(topPanel);
        topScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        topScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int userId = Integer.parseInt(userIdField.getText());
                User user = userService.getUserById(userId);
                userInfoPanel.removeAll();
                if (user != null) {
                    InfoPanel userInfo = new UserInfoPanel(userInfoPanel, user, currentUser, null);
                    // InfoPanel userInfo = new UserBriefInfoPanel(userInfoPanel, user, currentUser);
                    JButton sendRequestButton = new JButton("发送好友请求");
                    sendRequestButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            SendRequestDialog requestDialog = new SendRequestDialog(friendRequestDemo.this, currentUser, user, userService);
                            requestDialog.showDialog();
                            friendRequestDemo.this.updateFriendRequestsPanel();
                        }
                    });
                    sendRequestButton.setFont(new Font("微软雅黑", Font.BOLD, 12));
                    sendRequestButton.setBorder(new FlatRoundBorder());
                    userInfo.setButton(sendRequestButton);

                    userInfoPanel.add(userInfo);
                } else {
                    userInfoPanel.add(new JLabel("用户未找到"));
                }
                userInfoPanel.revalidate();
                userInfoPanel.repaint();
            }
        });

        // 下方面板：处理他人发送的好友请求和本用户发送的好友请求
        this.bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        this.receivedRequestsPanel = new JPanel();
        receivedRequestsPanel.setLayout(new BoxLayout(receivedRequestsPanel, BoxLayout.Y_AXIS));

        this.sentRequestsPanel = new JPanel();
        sentRequestsPanel.setLayout(new BoxLayout(sentRequestsPanel, BoxLayout.Y_AXIS));

        // 添加标签
        TitlePanel receivedRequestslabelPanel = new TitlePanel("收到的好友请求", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFriendRequestsPanel();
            }
        });
        receivedRequestslabelPanel.setMaximumSize(new Dimension(this.getWidth(), 40)); // 固定高度

        TitlePanel sentRequestslabelPanel = new TitlePanel("已发送的好友请求", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFriendRequestsPanel();
            }
        });
        sentRequestslabelPanel.setMaximumSize(new Dimension(this.getWidth(), 40)); // 固定高度

        this.bottomPanel.add(receivedRequestslabelPanel);
        this.bottomPanel.add(receivedRequestsPanel);
        this.bottomPanel.add(Box.createVerticalStrut(10)); // 添加间隔
        this.bottomPanel.add(sentRequestslabelPanel);
        this.bottomPanel.add(sentRequestsPanel);

        updateFriendRequestsPanel();

        // 为 bottomPanel 添加滚动条
        JScrollPane bottomScrollPane = new JScrollPane(bottomPanel);
        bottomScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        bottomScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // 使用 JSplitPane 将 topScrollPane 和 bottomScrollPane 分开
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topScrollPane, bottomScrollPane);
        splitPane.setDividerLocation(150); // 设置初始分割位置
        splitPane.setResizeWeight(0.5); // 设置调整权重

        add(splitPane, BorderLayout.CENTER);

    }

    private void updateFriendRequestsPanel() {
        updateReceivedRequestsPanel();
        updateSentRequestsPanel();
    }

    private void updateReceivedRequestsPanel() {
        receivedRequestsPanel.removeAll();
        List<FriendRequest> receivedRequests = userService.getFriendRequestsByReceiver(this.currentUserId);

        if (receivedRequests.size() == 0) {
            JPanel infoPanel = new JPanel();
            infoPanel.add(new JLabel("暂无未处理请求"));
            receivedRequestsPanel.add(infoPanel);
        }

        for (FriendRequest request : receivedRequests) {
            int senderId = request.getSenderId();
            String requestMessage = request.getRequestMessage();
            InfoPanel userInfoPanel = new UserInfoPanel(this.receivedRequestsPanel, userService.getUserById(senderId), currentUser, null);
            userInfoPanel.setAdditionalInfo("验证消息：" + requestMessage);

            if (request.getRequestStatus().equals(FriendRequest.RequestStatus.pending.toString())) {
                JButton statusLabel = new JButton("处理请求");
                statusLabel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        HandleRequestDialog requestDialog = new HandleRequestDialog(friendRequestDemo.this, currentUser, request);
                        requestDialog.showDialog();
                        friendRequestDemo.this.updateFriendRequestsPanel();
                    }
                });
                statusLabel.setFont(new Font("微软雅黑", Font.BOLD, 12));
                statusLabel.setBorder(new FlatRoundBorder());
                userInfoPanel.setButton(statusLabel);
            }

            if (request.getRequestStatus().equals(FriendRequest.RequestStatus.approved.toString())) {
                JButton statusLabel = new JButton("已同意");
                statusLabel.setEnabled(false);
                statusLabel.setFont(new Font("微软雅黑", Font.BOLD, 12));
                statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                userInfoPanel.setButton(statusLabel);
            }

            if (request.getRequestStatus().equals(FriendRequest.RequestStatus.rejected.toString())) {
                JButton statusLabel = new JButton("已拒绝");
                statusLabel.setEnabled(false);
                statusLabel.setFont(new Font("微软雅黑", Font.BOLD, 12));
                statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                userInfoPanel.setButton(statusLabel);
            }

            receivedRequestsPanel.add(userInfoPanel);
        }

        receivedRequestsPanel.revalidate();
        receivedRequestsPanel.repaint();

    }

    private void updateSentRequestsPanel() {
        sentRequestsPanel.removeAll();
        List<FriendRequest> sentRequests = userService.getFriendRequestsBySender(this.currentUserId);

        if (sentRequests.size() == 0) {
            JPanel infoPanel = new JPanel();
            infoPanel.add(new JLabel("暂无已发送请求"));
            sentRequestsPanel.add(infoPanel);
        }

        for (FriendRequest request : sentRequests) {
            int receiverId = request.getReceiverId();
            String requestMessage = request.getRequestMessage();
            InfoPanel userInfoPanel = new UserInfoPanel(this.sentRequestsPanel, userService.getUserById(receiverId), currentUser, null);
            userInfoPanel.setAdditionalInfo("验证消息：" + requestMessage);

            if (request.getRequestStatus().equals(FriendRequest.RequestStatus.pending.toString())) {
                JButton statusLabel = new JButton("等待对方处理");
                statusLabel.setEnabled(false);
                statusLabel.setFont(new Font("微软雅黑", Font.BOLD, 12));
                statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                userInfoPanel.setButton(statusLabel);
            }

            if (request.getRequestStatus().equals(FriendRequest.RequestStatus.approved.toString())) {
                JButton statusLabel = new JButton("已通过");
                statusLabel.setEnabled(false);
                statusLabel.setFont(new Font("微软雅黑", Font.BOLD, 12));
                statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                userInfoPanel.setButton(statusLabel);
            }

            if (request.getRequestStatus().equals(FriendRequest.RequestStatus.rejected.toString())) {
                JButton statusLabel = new JButton("未通过");
                statusLabel.setEnabled(false);
                statusLabel.setFont(new Font("微软雅黑", Font.BOLD, 12));
                statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                userInfoPanel.setButton(statusLabel);
            }

            sentRequestsPanel.add(userInfoPanel);
        }

        sentRequestsPanel.revalidate();
        sentRequestsPanel.repaint();
    }

    private class TitlePanel extends JPanel {
        JLabel titleLabel;
        JButton refreshButton;

        public TitlePanel(String title) {
            setLayout(new FlowLayout(FlowLayout.LEFT));
            titleLabel = new JLabel(title);
            titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 12));
            titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            add(titleLabel);
        }

        public TitlePanel(String title, ActionListener listener) {
            setLayout(new BorderLayout());
            titleLabel = new JLabel(title);
            titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 12));
            titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            add(titleLabel, BorderLayout.WEST);

            JPanel buttonPanel = new JPanel();
            refreshButton = new JButton();
            refreshButton.setPreferredSize(new Dimension(24, 24));
            refreshButton.setMaximumSize(refreshButton.getPreferredSize());
            refreshButton.setBorder(new FlatRoundBorder());
            refreshButton.addActionListener(listener);
            ImageIcon icon = new ImageIcon("src/main/resources/icons/refresh03.png");
            try {
                BufferedImage image = ImageIO.read(new File("src/main/resources/icons/refresh03.png"));
                BufferedImage croppedImage = ImageManager.cropToCircle(image);
                BufferedImage resizedImage = ImageManager.resizeImage(croppedImage, 16, 16);
                
                icon = new ImageIcon(resizedImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            refreshButton.setIcon(icon);
            refreshButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            buttonPanel.add(refreshButton);
            add(buttonPanel, BorderLayout.EAST);
        }
    }

    public static void main(String[] args) {

        try {
            // 使用相对路径加载主题文件
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new friendRequestDemo(1).setVisible(true); // 假设当前用户ID为
            }
        });
    }
}
