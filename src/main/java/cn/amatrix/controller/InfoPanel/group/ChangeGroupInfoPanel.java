package cn.amatrix.controller.InfoPanel.group;

import cn.amatrix.model.groups.Group;
import cn.amatrix.service.groups.GroupService;
import cn.amatrix.utils.base64.ImageManager;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeGroupInfoPanel extends JPanel {

    private Group group;
    private GroupService groupService;
    private JLabel groupNameLabel;
    private JPanel groupNamePanel;
    private JTextField groupNameField;
    private JLabel groupIdLabel;
    private JLabel avatarLabel;

    public ChangeGroupInfoPanel(Group group) {
        this.group = group;
        this.groupService = new GroupService();
        setLayout(new BorderLayout());

        initAvatarLabel(group.getAvatar());
        initGroupNameLabel(group.getGroupName());
        initGroupNamePanel(group.getGroupName());
        initGroupIdLabel(String.format("%06d", group.getGroupId()));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(avatarLabel);
        infoPanel.add(groupNameLabel);
        infoPanel.add(groupIdLabel);
        infoPanel.add(groupNamePanel);

        for (Component component : infoPanel.getComponents()) {
            if (component instanceof JLabel || component instanceof JTextField) {
                ((JComponent) component).setAlignmentX(Component.CENTER_ALIGNMENT);
            }
        }

        JButton saveButton = new JButton("保存");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveChanges();
            }
        });

        JButton changeAvatarButton = new JButton("修改头像");
        changeAvatarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeAvatar();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(changeAvatarButton);

        add(infoPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    private void initAvatarLabel(String base64Image) {
        avatarLabel = new JLabel();
        avatarLabel.setPreferredSize(new Dimension(80, 80));
        avatarLabel.setOpaque(false);

        if (base64Image == null || base64Image.isEmpty() || base64Image.equals("null") || base64Image.equals("\"null\""))  {
            avatarLabel.setIcon(createPlaceholderIcon());
            return;
        }

        try {
            BufferedImage image = ImageManager.base64ToCircularImage(base64Image);
            if (image != null) {
                Image scaledImage = ImageManager.resizeImage(image, 80, 80);
                avatarLabel.setIcon(new ImageIcon(scaledImage));
            } else {
                avatarLabel.setIcon(createPlaceholderIcon());
            }
        } catch (IOException e) {
            avatarLabel.setIcon(createPlaceholderIcon());
            e.printStackTrace();
        }
    }

    private void initGroupNameLabel(String groupName) {
        groupNameLabel = new JLabel(groupName);
        groupNameLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 16));
        groupNameLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
    }

    private void initGroupNamePanel(String groupName) {
        groupNamePanel = new JPanel();
        groupNamePanel.setMaximumSize(new Dimension(200, 40));
        groupNamePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        JLabel groupNameLabel = new JLabel("新群组名: ");
        groupNameLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
        groupNameLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        groupNameField = new JTextField(groupName);
        groupNameField.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
        groupNameField.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        groupNamePanel.setLayout(new BoxLayout(groupNamePanel, BoxLayout.X_AXIS));
        groupNamePanel.add(groupNameLabel);
        groupNamePanel.add(groupNameField);
    }

    private void initGroupIdLabel(String groupId) {
        groupIdLabel = new JLabel("GID: " + groupId);
        groupIdLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
        groupIdLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
    }

    private void saveChanges() {
        String newGroupName = groupNameField.getText();
        group.setGroupName(newGroupName);
        groupService.updateGroup(group, group.getGroupId());
        JOptionPane.showMessageDialog(this, "信息已更新");
        refreshPanel();
    }

    private void changeAvatar() {
        JFrame frame = new ChangeGroupAvatar(group);
        frame.setVisible(true);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                refreshPanel();
            }
        });
    }

    private void refreshPanel() {
        removeAll();
        group = groupService.getGroupById(group.getGroupId());
        initAvatarLabel(group.getAvatar());
        initGroupNameLabel(group.getGroupName());
        initGroupNamePanel(group.getGroupName());
        initGroupIdLabel(String.format("%06d", group.getGroupId()));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(avatarLabel);
        infoPanel.add(groupNameLabel);
        infoPanel.add(groupIdLabel);
        infoPanel.add(groupNamePanel);

        for (Component component : infoPanel.getComponents()) {
            if (component instanceof JLabel || component instanceof JTextField) {
                ((JComponent) component).setAlignmentX(Component.CENTER_ALIGNMENT);
            }
        }

        JButton saveButton = new JButton("保存");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveChanges();
            }
        });

        JButton changeAvatarButton = new JButton("修改头像");
        changeAvatarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeAvatar();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(changeAvatarButton);

        add(infoPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        revalidate();
        repaint();
    }

    private Icon createPlaceholderIcon() {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                g.setColor(Color.GRAY);
                g.fillOval(x, y, getIconWidth(), getIconHeight());
            }

            @Override
            public int getIconWidth() {
                return 80;
            }

            @Override
            public int getIconHeight() {
                return 80;
            }
        };
    }
}
