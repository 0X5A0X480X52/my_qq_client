package cn.amatrix.controller.InfoPanel.user;

import cn.amatrix.model.users.User;
import cn.amatrix.service.users.UserService;
import cn.amatrix.utils.base64.ImageManager;
import javax.swing.*;

import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserDetailedInfoPanel extends JPanel {

    private User currentUser;
    private User targetUser;

    private JLabel avatarIcon;
    private JLabel usernameLabel;
    private JLabel userIdLabel;
    private JLabel emailLabel;
    private JLabel createdAtLabel;
    private JLabel logStatusLabel;
    private JLabel lastLoginAtLabel;
    private JLabel lastLogoutAtLabel;

    public UserDetailedInfoPanel(User user, User currentUser) {

        this.targetUser = user;
        this.currentUser = currentUser;
        setLayout(new BorderLayout());

        initAvatarIcon(user.getAvatar());
        initUsernameLabel(user.getUsername());
        initUserIdLabel(String.format("%06d", user.getUser_id()));
        initEmailLabel(user.getEmail());
        initCreatedAtLabel(user.getCreated_at().toString());
        initLogStatusLabel(user.getLog_status());
        initLastLoginAtLabel(user.getLast_login_at().toString());
        initLastLogoutAtLabel(user.getLast_logout_at().toString());

        JPanel avatarPanel = new JPanel();
        avatarPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        avatarPanel.add(avatarIcon);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(usernameLabel);
        infoPanel.add(userIdLabel);
        infoPanel.add(emailLabel);
        infoPanel.add(logStatusLabel);
        infoPanel.add(createdAtLabel);
        infoPanel.add(lastLoginAtLabel);
        infoPanel.add(lastLogoutAtLabel);

        // 居中放置信息
        for (Component component : infoPanel.getComponents()) {
            if (component instanceof JLabel) {
                ((JLabel) component).setAlignmentX(Component.CENTER_ALIGNMENT);
            }
        }

        add(avatarPanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);

        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // 添加鼠标右键单击事件处理程序
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

        if (base64Image == null || base64Image.equals("null") || base64Image.isEmpty()) {
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

    private void initUsernameLabel(String username) {
        usernameLabel = new JLabel( username);
        usernameLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 16));
        usernameLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
    }

    private void initUserIdLabel(String userId) {
        userIdLabel = new JLabel("UID: " + userId);
        userIdLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
        userIdLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
    }

    private void initEmailLabel(String email) {
        emailLabel = new JLabel("emal: " + email);
        emailLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
        emailLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
    }

    private void initCreatedAtLabel(String createdAt) {
        createdAtLabel = new JLabel("创建时间: " + createdAt);
        createdAtLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 10));
    }

    private void initLogStatusLabel(String logStatus) {
        logStatusLabel = new JLabel("登录状态: " + logStatus);
        logStatusLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 10));
    }

    private void initLastLoginAtLabel(String lastLoginAt) {
        lastLoginAtLabel = new JLabel("最后登录: " + lastLoginAt);
        lastLoginAtLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 10));
    }

    private void initLastLogoutAtLabel(String lastLogoutAt) {
        lastLogoutAtLabel = new JLabel("最后登出: " + lastLogoutAt);
        lastLogoutAtLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 10));
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
                frame.add(new ChangeInfoPanel(targetUser));
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

        JMenuItem deleteFriendItem = new JMenuItem("删除好友");
        deleteFriendItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "确定要删除好友吗？", "删除好友", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    // 删除好友的逻辑
                    UserService userService = new UserService();
                    userService.deleteFriend(currentUser.getUser_id(), targetUser.getUser_id());
                    JOptionPane.showMessageDialog(null, "好友已删除");
                }
            }
        });

        if (currentUser.getUser_id() == targetUser.getUser_id())
            popupMenu.add(changeInfoItem);
            
        popupMenu.add(sendMessageItem);

        if (currentUser.getUser_id() != targetUser.getUser_id())
            popupMenu.add(deleteFriendItem);

        popupMenu.show(this, x, y);
    }

    private void refreshPanel() {
        removeAll();
        targetUser = new UserService().getUserById(targetUser.getUser_id());
        initAvatarIcon(targetUser.getAvatar());
        initUsernameLabel(targetUser.getUsername());
        initUserIdLabel(String.format("%06d", targetUser.getUser_id()));
        initEmailLabel(targetUser.getEmail());
        initCreatedAtLabel(targetUser.getCreated_at().toString());
        initLogStatusLabel(targetUser.getLog_status());
        initLastLoginAtLabel(targetUser.getLast_login_at().toString());
        initLastLogoutAtLabel(targetUser.getLast_logout_at().toString());

        JPanel avatarPanel = new JPanel();
        avatarPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        avatarPanel.add(avatarIcon);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(usernameLabel);
        infoPanel.add(userIdLabel);
        infoPanel.add(emailLabel);
        infoPanel.add(logStatusLabel);
        infoPanel.add(createdAtLabel);
        infoPanel.add(lastLoginAtLabel);
        infoPanel.add(lastLogoutAtLabel);

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

    public static void main(String[] args) {

        try {
            // 使用相对路径加载主题文件
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        UserService userService = new UserService();
        frame.add(new UserDetailedInfoPanel(userService.getUserById(1), userService.getUserById(1)));
        frame.setVisible(true);
    }
}
