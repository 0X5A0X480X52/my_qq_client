package cn.amatrix.controller.groupRequest.commponent;

import cn.amatrix.controller.InfoPanel.group.GroupInfoPanel;
import cn.amatrix.model.groups.Group;
import cn.amatrix.service.groups.GroupService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SendRequestDialog extends JDialog {
    private JTextArea requestMessageField;
    private GroupService groupService = new GroupService();

    public SendRequestDialog(JFrame parent, int currentUserId, Group group) {
        super(parent, "发送入群请求", true);
        setLayout(new BorderLayout());
        setSize(300, 250);

        GroupInfoPanel userInfo = new GroupInfoPanel(rootPane, group, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                groupService.addGroupJoinRequest(group.getGroupId(), currentUserId, requestMessageField.getText());;
                JOptionPane.showMessageDialog(null, "好友请求已发送");
                dispose();
            }
        });

        userInfo.setAdditionalInfo("群主很懒，什么也没写~");

        requestMessageField = new JTextArea("请允许我入群（请填写验证消息）");
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
