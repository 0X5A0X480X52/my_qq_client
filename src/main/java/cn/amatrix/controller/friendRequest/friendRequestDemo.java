package cn.amatrix.controller.friendRequest;

import cn.amatrix.model.users.User;
import cn.amatrix.model.users.FriendRequest;
import cn.amatrix.service.users.UserService;
import cn.amatrix.controller.friendRequest.commponent.userInfoPanel;
import cn.amatrix.controller.friendRequest.commponent.SendRequestDialog;

import javax.swing.*;

import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class friendRequestDemo extends JFrame {
    private UserService userService;
    private int currentUserId;

    public friendRequestDemo(int currentUserId) {
        this.currentUserId = currentUserId;
        this.userService = new UserService();
        setTitle("好友请求管理");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 上方面板：查找用户并发送好友请求
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        
        // 新增的面板，用于并排放置 userIdField 和 searchButton
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        JTextField userIdField = new JTextField();
        JButton searchButton = new JButton("查找用户");
        searchButton.setPreferredSize(new Dimension(120, 30)); // 固定宽度
        searchPanel.add(userIdField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);
        
        JPanel userInfoPanel = new JPanel();
        topPanel.add(searchPanel, BorderLayout.NORTH);
        topPanel.add(userInfoPanel, BorderLayout.CENTER);
        topPanel.setPreferredSize(new Dimension(0, 100)); // 固定高度

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
                    userInfoPanel.add(new userInfoPanel(userInfoPanel, user, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            SendRequestDialog requestDialog = new SendRequestDialog(friendRequestDemo.this, currentUserId, user, userService);
                            requestDialog.showDialog();
                        }
                    }));
                } else {
                    userInfoPanel.add(new JLabel("用户未找到"));
                }
                userInfoPanel.revalidate();
                userInfoPanel.repaint();
            }
        });

        // 下方面板：处理他人发送的好友请求
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        List<FriendRequest> friendRequests = userService.getFriendRequestsByReceiver(this.currentUserId);
        for (FriendRequest request : friendRequests) {
            JPanel requestPanel = new JPanel();
            requestPanel.setLayout(new BorderLayout());
            JLabel requestLabel = new JLabel("来自用户ID " + request.getSenderId() + " 的好友请求: " + request.getRequestMessage());
            JButton handleButton = new JButton("处理请求");
            handleButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    userService.updateFriendRequest(request);
                    JOptionPane.showMessageDialog(null, "好友请求已处理");
                    bottomPanel.remove(requestPanel);
                    bottomPanel.revalidate();
                    bottomPanel.repaint();
                }
            });
            requestPanel.add(requestLabel, BorderLayout.CENTER);
            requestPanel.add(handleButton, BorderLayout.EAST);
            bottomPanel.add(requestPanel);
        }

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
                new friendRequestDemo(1).setVisible(true); // 假设当前用户ID为1
            }
        });
    }
}
