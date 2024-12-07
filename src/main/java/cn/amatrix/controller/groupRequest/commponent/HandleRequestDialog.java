package cn.amatrix.controller.groupRequest.commponent;

import cn.amatrix.controller.friendRequest.commponent.UserInfoPanel;
import cn.amatrix.model.groups.GroupJoinRequest;
import cn.amatrix.model.users.User;
import cn.amatrix.service.groups.GroupService;
import cn.amatrix.service.users.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HandleRequestDialog extends JDialog {
    private JPanel messagePanel;
    private JTextArea requestMessageField;
    private UserService userService = new UserService();
    private GroupService groupService = new GroupService();

    public HandleRequestDialog(JFrame parent, int currentUserId, GroupJoinRequest request) {
        super(parent, "处理入群请求", true);
        setLayout(new BorderLayout());
        setSize(300, 250);

        int requestId = request.getRequestId();
        int senderId = request.getUserId();
        User user = userService.getUserById(senderId);

        UserInfoPanel userInfo = new UserInfoPanel(rootPane, user, null);

        // 创建下拉按钮
        String[] options = {"同意", "不同意"};
        JComboBox<String> comboBox = new JComboBox<>(options);
        JButton actionButton = new JButton("操作");

        actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) comboBox.getSelectedItem();
                if ("同意".equals(selectedOption)) {
                    // 执行同意操作
                    groupService.handleGroupJoinRequest(requestId, senderId, true);
                } else if ("不同意".equals(selectedOption)) {
                    // 执行不同意操作
                    groupService.handleGroupJoinRequest(requestId, senderId,false);
                }
                dispose(); // 关闭对话框
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(comboBox);
        buttonPanel.add(actionButton);

        userInfo.setButton(buttonPanel);

        // 添加验证消息面板
        this.messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout());
        JLabel messageLabel = new JLabel("验证消息:");
        messageLabel.setFont(new Font("微软雅黑", Font.BOLD, 10));
        messageLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        messagePanel.add(messageLabel, BorderLayout.NORTH);

        requestMessageField = new JTextArea(request.getRequestMessage());
        requestMessageField.setEditable(false); // 不可编辑
        requestMessageField.setLineWrap(true); // 自动换行
        requestMessageField.setWrapStyleWord(true); // 断行不断字
        requestMessageField.setAlignmentX(LEFT_ALIGNMENT); // 左对齐
        requestMessageField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5), // 外边框
            BorderFactory.createEmptyBorder(3, 3, 3, 3)  // 内边框
        ));
        messagePanel.add(requestMessageField, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(messagePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        add(userInfo, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER); // 调整位置
    }

    public void showDialog() {
        setLocationRelativeTo(getParent());
        setVisible(true);
    }
}
