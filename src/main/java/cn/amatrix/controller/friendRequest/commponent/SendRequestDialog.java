package cn.amatrix.controller.friendRequest.commponent;

import cn.amatrix.controller.InfoPanel.user.UserInfoPanel;
import cn.amatrix.model.users.User;
import cn.amatrix.service.users.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SendRequestDialog extends JDialog {
    private JTextArea requestMessageField;
    
    private int currentUserId;
    private User currentUser;

    public SendRequestDialog(JFrame parent, User currentUser, User user, UserService userService) {
        super(parent, "发送好友请求", true);

        this.currentUser = currentUser;
        this.currentUserId = this.currentUser.getUser_id();
        setLayout(new BorderLayout());
        setSize(300, 250);

        UserInfoPanel userInfo = new UserInfoPanel(rootPane, user, currentUser, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userService.addFriendRequest(currentUserId, user.getUser_id(), requestMessageField.getText());
                JOptionPane.showMessageDialog(null, "好友请求已发送");
                dispose();
            }
        });

        userInfo.setAdditionalInfo("这个人什么也没说~");

        requestMessageField = new JTextArea("请加我为好友（请填写验证消息）");
        requestMessageField.setLineWrap(true); // 自动换行
        requestMessageField.setWrapStyleWord(true); // 断行不断字
        requestMessageField.setAlignmentX(LEFT_ALIGNMENT); // 左对齐
        requestMessageField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5), // 外边框
            BorderFactory.createEmptyBorder(3, 3, 3, 3)  // 内边框
        ));

        JScrollPane scrollPane = new JScrollPane(requestMessageField);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(userInfo, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER); // 调整位置
    }

    public void showDialog() {
        setLocationRelativeTo(getParent());
        setVisible(true);
    }
}
