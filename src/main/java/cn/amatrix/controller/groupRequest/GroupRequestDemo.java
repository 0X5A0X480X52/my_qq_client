package cn.amatrix.controller.groupRequest;

import cn.amatrix.model.groups.Group;
import cn.amatrix.model.groups.GroupJoinRequest;
import cn.amatrix.model.users.User;
import cn.amatrix.service.groups.GroupService;
import cn.amatrix.service.users.UserService;
import cn.amatrix.utils.base64.ImageManager;
import cn.amatrix.controller.friendRequest.commponent.UserInfoPanel;
import cn.amatrix.controller.groupRequest.commponent.GroupInfoPanel;
import cn.amatrix.controller.groupRequest.commponent.HandleRequestDialog;
import cn.amatrix.controller.groupRequest.commponent.SendRequestDialog;

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

public class GroupRequestDemo extends JFrame {
    private GroupService groupService = new GroupService();
    private UserService userService = new UserService();
    private int currentUserId;

    JPanel topPanel;
    JPanel searchPanel;
    JPanel groupInfoPanel;
    JPanel bottomPanel;
    JPanel receivedRequestsPanel;
    JPanel sentRequestsPanel;

    public GroupRequestDemo(int currentUserId) {
        this.currentUserId = currentUserId;
        setTitle("群组申请管理");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());

        // 上方面板：查找群组并发送入群申请
        this.topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(0, 100)); // 固定高度
        
        // 新增的面板，用于并排放置 groupIdField 和 searchButton
        this.searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        JTextField groupIdField = new JTextField();
        JButton searchButton = new JButton("查找群组");
        searchButton.setPreferredSize(new Dimension(120, 30)); // 固定宽度
        TitlePanel searchGroupsLabelPanel = new TitlePanel("查找群组");

        searchPanel.add(groupIdField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);
        searchPanel.add(searchGroupsLabelPanel, BorderLayout.NORTH);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // 设置边距
        
        this.groupInfoPanel = new JPanel();
        groupInfoPanel.setLayout(new BorderLayout()); // 设置布局管理器
        topPanel.add(searchPanel, BorderLayout.NORTH);
        topPanel.add(groupInfoPanel, BorderLayout.CENTER);

        // 为 topPanel 添加滚动条
        JScrollPane topScrollPane = new JScrollPane(topPanel);
        topScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        topScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int groupId = Integer.parseInt(groupIdField.getText());
                Group group = groupService.getGroupById(groupId);
                groupInfoPanel.removeAll();
                if (group != null) {
                    GroupInfoPanel groupInfo = new GroupInfoPanel(groupInfoPanel, group, null);
                    JButton sendRequestButton = new JButton("发送入群申请");
                    sendRequestButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            SendRequestDialog requestDialog = new SendRequestDialog(GroupRequestDemo.this, currentUserId, group);
                            requestDialog.showDialog();
                            GroupRequestDemo.this.updateGroupRequestsPanel();
                        }
                    });
                    sendRequestButton.setFont(new Font("微软雅黑", Font.BOLD, 12));
                    sendRequestButton.setBorder(new FlatRoundBorder());
                    groupInfo.setButton(sendRequestButton);

                    groupInfoPanel.add(groupInfo);
                } else {
                    groupInfoPanel.add(new JLabel("群组未找到"));
                }
                groupInfoPanel.revalidate();
                groupInfoPanel.repaint();
            }
        });

        // 下方面板：处理他人发送的入群申请和本用户发送的入群申请
        this.bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        this.receivedRequestsPanel = new JPanel();
        receivedRequestsPanel.setLayout(new BoxLayout(receivedRequestsPanel, BoxLayout.Y_AXIS));

        this.sentRequestsPanel = new JPanel();
        sentRequestsPanel.setLayout(new BoxLayout(sentRequestsPanel, BoxLayout.Y_AXIS));

        // 添加标签
        TitlePanel receivedRequestsLabelPanel = new TitlePanel("收到的入群申请", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGroupRequestsPanel();
            }
        });

        TitlePanel sentRequestsLabelPanel = new TitlePanel("已发送的入群申请", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGroupRequestsPanel();
            }
        });

        this.bottomPanel.add(receivedRequestsLabelPanel);
        this.bottomPanel.add(receivedRequestsPanel);
        this.bottomPanel.add(Box.createVerticalStrut(10)); // 添加间隔
        this.bottomPanel.add(sentRequestsLabelPanel);
        this.bottomPanel.add(sentRequestsPanel);

        updateGroupRequestsPanel();

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

    private void updateGroupRequestsPanel() {
        updateReceivedRequestsPanel();
        updateSentRequestsPanel();
    }

    private void updateReceivedRequestsPanel() {
        receivedRequestsPanel.removeAll();
        List<GroupJoinRequest> receivedRequests = groupService.getGroupJoinRequestsByUserId_toHandle(this.currentUserId);
        for (GroupJoinRequest request : receivedRequests) {
            String requestMessage = request.getRequestMessage();
            User user = userService.getUserById(request.getUserId());
            UserInfoPanel userInfoPanel = new UserInfoPanel(this.receivedRequestsPanel, user, null);
            userInfoPanel.setAdditionalInfo("验证消息：" + requestMessage);

            if (request.getRequestStatus().equals(GroupJoinRequest.RequestStatus.pending.toString())) {
                JButton statusLabel = new JButton("处理请求");
                statusLabel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        HandleRequestDialog requestDialog = new HandleRequestDialog(GroupRequestDemo.this, currentUserId, request);
                        requestDialog.showDialog();
                        GroupRequestDemo.this.updateGroupRequestsPanel();
                    }
                });
                statusLabel.setFont(new Font("微软雅黑", Font.BOLD, 12));
                statusLabel.setBorder(new FlatRoundBorder());
                userInfoPanel.setButton(statusLabel);
            }

            if (request.getRequestStatus().equals(GroupJoinRequest.RequestStatus.approved.toString())) {
                JButton statusLabel = new JButton("已同意");
                statusLabel.setEnabled(false);
                statusLabel.setFont(new Font("微软雅黑", Font.BOLD, 12));
                statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                userInfoPanel.setButton(statusLabel);
            }

            if (request.getRequestStatus().equals(GroupJoinRequest.RequestStatus.rejected.toString())) {
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
        List<GroupJoinRequest> sentRequests = groupService.getGroupJoinRequestsByUserId_send(this.currentUserId);
        for (GroupJoinRequest request : sentRequests) {
            int groupId = request.getGroupId();
            String requestMessage = request.getRequestMessage();
            GroupInfoPanel groupInfoPanel = new GroupInfoPanel(this.sentRequestsPanel, groupService.getGroupById(groupId), null);
            groupInfoPanel.setAdditionalInfo("验证消息：" + requestMessage);

            if (request.getRequestStatus().equals(GroupJoinRequest.RequestStatus.pending.toString())) {
                JButton statusLabel = new JButton("等待对方处理");
                statusLabel.setEnabled(false);
                statusLabel.setFont(new Font("微软雅黑", Font.BOLD, 12));
                statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                groupInfoPanel.setButton(statusLabel);
            }

            if (request.getRequestStatus().equals(GroupJoinRequest.RequestStatus.approved.toString())) {
                JButton statusLabel = new JButton("已通过");
                statusLabel.setEnabled(false);
                statusLabel.setFont(new Font("微软雅黑", Font.BOLD, 12));
                statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                groupInfoPanel.setButton(statusLabel);
            }

            if (request.getRequestStatus().equals(GroupJoinRequest.RequestStatus.rejected.toString())) {
                JButton statusLabel = new JButton("未通过");
                statusLabel.setEnabled(false);
                statusLabel.setFont(new Font("微软雅黑", Font.BOLD, 12));
                statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                groupInfoPanel.setButton(statusLabel);
            }

            sentRequestsPanel.add(groupInfoPanel);
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
                new GroupRequestDemo(1).setVisible(true); // 假设当前用户ID为1
            }
        });
    }
}
