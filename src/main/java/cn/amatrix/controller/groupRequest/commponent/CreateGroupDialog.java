package cn.amatrix.controller.groupRequest.commponent;

import cn.amatrix.model.groups.Group;
import cn.amatrix.service.groups.GroupService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateGroupDialog extends JDialog {
    private GroupService groupService;

    public CreateGroupDialog(Frame owner, int currentUserId) {
        super(owner, "创建群聊", true);
        this.groupService = new GroupService();

        setLayout(new BorderLayout());
        setSize(300, 150);
        setLocationRelativeTo(owner);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        JLabel infoLabel = new JLabel("是否确认创建群聊？");
        infoLabel.setHorizontalAlignment(JLabel.CENTER);
        infoPanel.add(infoLabel, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton createButton = new JButton("确认");
        JButton cancelButton = new JButton("取消");

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Group newGroup = groupService.createGroup(currentUserId);
                if (newGroup == null) {
                    JOptionPane.showMessageDialog(CreateGroupDialog.this, "创建群聊失败！");
                } else {
                    JOptionPane.showMessageDialog(CreateGroupDialog.this, String.format("创建群聊成功！\n群聊ID：GID:%06d", newGroup.getGroupId()));
                }
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        panel.add(createButton);
        panel.add(cancelButton);

        add(infoPanel, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
    }
}
