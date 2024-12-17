package cn.amatrix.controller.InfoPanel.group;

import cn.amatrix.model.groups.Group;
import cn.amatrix.model.groups.GroupMember;
import cn.amatrix.model.users.User;
import cn.amatrix.service.groups.GroupService;
import cn.amatrix.utils.base64.ImageManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GroupDetailedInfoPanel extends JPanel {

    private Group group;
    private User currentUser;

    private JLabel avatarIcon;
    private JLabel groupNameLabel;
    private JLabel groupIdLabel;
    private JLabel createdAtLabel;

    public GroupDetailedInfoPanel(Group group, User currentUser) {
        this.group = group;
        this.currentUser = currentUser;

        setLayout(new BorderLayout());

        initAvatarIcon(group.getAvatar());
        initGroupNameLabel(group.getGroupName());
        initGroupIdLabel(String.format("%06d", group.getGroupId()));
        initCreatedAtLabel(group.getCreatedAt().toString());

        JPanel avatarPanel = new JPanel();
        avatarPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        avatarPanel.add(avatarIcon);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(groupNameLabel);
        infoPanel.add(groupIdLabel);
        infoPanel.add(createdAtLabel);

        for (Component component : infoPanel.getComponents()) {
            if (component instanceof JLabel) {
                ((JLabel) component).setAlignmentX(Component.CENTER_ALIGNMENT);
            }
        }

        add(avatarPanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);

        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    showPopupMenu(e.getX(), e.getY());
                }
            }
        });
    }

    private void initAvatarIcon(String base64Image) {
        avatarIcon = new JLabel();
        avatarIcon.setPreferredSize(new Dimension(80, 80));
        avatarIcon.setOpaque(false);

        if (base64Image == null || base64Image.isEmpty() || base64Image.equals("null") || base64Image.equals("\"null\""))  {
            avatarIcon.setIcon(createPlaceholderIcon());
            return;
        }

        try {
            BufferedImage image = ImageManager.base64ToCircularImage(base64Image);
            if (image != null) {
                Image scaledImage = ImageManager.resizeImage(image, 80, 80);
                avatarIcon.setIcon(new ImageIcon(scaledImage));
            } else {
                avatarIcon.setIcon(createPlaceholderIcon());
            }
        } catch (IOException e) {
            avatarIcon.setIcon(createPlaceholderIcon());
            e.printStackTrace();
        }
    }

    private void initGroupNameLabel(String groupName) {
        groupNameLabel = new JLabel(groupName);
        groupNameLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 16));
        groupNameLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
    }

    private void initGroupIdLabel(String groupId) {
        groupIdLabel = new JLabel("GID: " + groupId);
        groupIdLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
        groupIdLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
    }

    private void initCreatedAtLabel(String createdAt) {
        createdAtLabel = new JLabel("创建时间: " + createdAt);
        createdAtLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 10));
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

    private void showPopupMenu(int x, int y) {
        JPopupMenu popupMenu = new JPopupMenu();
        
        JMenuItem changeInfoItem = new JMenuItem("修改信息");
        changeInfoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("修改信息");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(400, 300);
                frame.setLocationRelativeTo(null);
                frame.add(new ChangeGroupInfoPanel(group));
                frame.setVisible(true);
                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        refreshPanel();
                    }
                });
            }
        });

        JMenuItem sendMessageItem = new JMenuItem("发送消息");
        sendMessageItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 发送消息的逻辑
                JOptionPane.showMessageDialog(null, "发送消息功能尚未实现");
            }
        });

        JMenuItem deleteGroupItem = new JMenuItem("删除群组");
        deleteGroupItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "确定要删除群组吗？", "删除群组", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    // 删除群组的逻辑
                    GroupService groupService = new GroupService();
                    groupService.deleteGroupMember( group.getGroupId(), currentUser.getUser_id());
                    JOptionPane.showMessageDialog(null, "群组已删除");
                }
            }
        });

        JMenuItem destroyGroupItem = new JMenuItem("注销群组");
        destroyGroupItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "确定要注销群组吗？", "删除群组", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    // 删除群组的逻辑
                    GroupService groupService = new GroupService();
                    groupService.deleteGroup(group.getGroupId(), currentUser.getUser_id());
                    JOptionPane.showMessageDialog(null, "群组已注销");
                }
            }
        });

        GroupMember member = new GroupService().getGroupMemberById(group.getGroupId(), currentUser.getUser_id());
        if ( member != null ) {
            if (member.getPower().equals("owner")) {
                popupMenu.add(sendMessageItem);
                popupMenu.add(changeInfoItem);
                popupMenu.add(destroyGroupItem);
            } else {
                popupMenu.add(sendMessageItem);
                popupMenu.add(deleteGroupItem);
            }
        }
        popupMenu.show(this, x, y);
    }

    private void refreshPanel() {
        removeAll();
        group = new GroupService().getGroupById(group.getGroupId());
        initAvatarIcon(group.getAvatar());
        initGroupNameLabel(group.getGroupName());
        initGroupIdLabel(String.format("%06d", group.getGroupId()));
        initCreatedAtLabel(group.getCreatedAt().toString());

        JPanel avatarPanel = new JPanel();
        avatarPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        avatarPanel.add(avatarIcon);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(groupNameLabel);
        infoPanel.add(groupIdLabel);
        infoPanel.add(createdAtLabel);

        for (Component component : infoPanel.getComponents()) {
            if (component instanceof JLabel) {
                ((JLabel) component).setAlignmentX(Component.CENTER_ALIGNMENT);
            }
        }

        add(avatarPanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
