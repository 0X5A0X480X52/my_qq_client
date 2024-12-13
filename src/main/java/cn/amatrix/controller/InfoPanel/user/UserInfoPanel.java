package cn.amatrix.controller.InfoPanel.user;

import cn.amatrix.controller.InfoPanel.InfoPanel;
import cn.amatrix.model.users.User;
import cn.amatrix.service.users.UserService;
import cn.amatrix.utils.base64.ImageManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class UserInfoPanel extends InfoPanel {

    private User targetUser;
    private User currentUser;

    private static final int FIXED_HEIGHT = 55; // 固定高度
    private JLabel avatarIcon;
    private JLabel usernameLabel;
    private JLabel userIdLabel;
    private JLabel additionalInfoLabel;
    private JPanel avatarPanel;
    private JPanel buttonPanel;
    private JPanel infoPanel;
    private String additionalInfo = "";

    private JComponent parentComponent;

    public UserInfoPanel(JComponent parentComponent, User user, User currentUser, ActionListener sendRequestListener) {

        this.targetUser = user;
        this.currentUser = currentUser;

        setLayout(new BorderLayout());
        this.parentComponent = parentComponent;
        int parentWidth = this.parentComponent.getWidth();
        setPreferredSize(new Dimension(parentWidth, FIXED_HEIGHT)); // 宽度为父组件宽度，高度固定

        initAvatarIcon(user.getAvatar());
        initUsernameLabel(user.getUsername());
        initUserIdLabel(String.format("%06d", user.getUser_id()));
        initAdditionalInfoLabel();

        // 创建子Panel用于放置头像
        avatarPanel = new JPanel();
        avatarPanel.setLayout(new BorderLayout());
        avatarPanel.add(avatarIcon, BorderLayout.NORTH);

        // 创建子Panel用于放置用户名标签
        infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(usernameLabel);
        infoPanel.add(userIdLabel);
        infoPanel.add(additionalInfoLabel);

        // 添加子Panel到userInfoPanel
        add(avatarPanel, BorderLayout.WEST);
        add(infoPanel, BorderLayout.CENTER);

        // 右侧按钮
        JButton sendRequestButton = new JButton("发送");
        sendRequestButton.addActionListener(sendRequestListener);
        this.buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(sendRequestButton);
        add(buttonPanel, BorderLayout.EAST);

        // 设置边距
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        avatarPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

        // 添加鼠标右键单击事件处理程序
        avatarPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    showDetailedInfoPanel(user);
                }
            }
        });
    }

    private void showDetailedInfoPanel(User user) {
        JFrame frame = new JFrame("用户详细信息");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.add(new UserDetailedInfoPanel(user, currentUser));
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
        UserService userService = new UserService();
        this.targetUser = userService.getUserById(this.targetUser.getUser_id());
        initAvatarIcon(targetUser.getAvatar());
        initUsernameLabel(targetUser.getUsername());
        initUserIdLabel(String.format("%06d", targetUser.getUser_id()));
        initAdditionalInfoLabel();

        avatarPanel = new JPanel();
        avatarPanel.setLayout(new BorderLayout());
        avatarPanel.add(avatarIcon, BorderLayout.NORTH);

        infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(usernameLabel);
        infoPanel.add(userIdLabel);
        infoPanel.add(additionalInfoLabel);

        add(avatarPanel, BorderLayout.WEST);
        add(infoPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);

        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        avatarPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        revalidate();
        repaint();
    }

    private void initAvatarIcon(String base64Image) {
        avatarIcon = new JLabel();
        avatarIcon.setPreferredSize(new Dimension(40, 40));
        avatarIcon.setOpaque(false);

        if (base64Image == null || base64Image.equals("null") || base64Image.isEmpty()) {
            avatarIcon.setIcon(createPlaceholderIcon());
            return;
        }

        try {
            BufferedImage image = ImageManager.base64ToCircularImage(base64Image);
            if (image != null) {
                Image scaledImage = ImageManager.resizeImage(image, 40, 40);
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
        usernameLabel = new JLabel(username);
        usernameLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
    }

    private void initUserIdLabel(String userId) {
        userIdLabel = new JLabel("UID: " + userId);
        userIdLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 8));
    }

    private void initAdditionalInfoLabel() {
        additionalInfoLabel = new JLabel(this.additionalInfo);
        additionalInfoLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 10));
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
        additionalInfoLabel.setText(additionalInfo);
    }

    public void setButton(JComponent button) {
        this.buttonPanel.removeAll();
        this.buttonPanel.add(button);
    }

    public JComponent getButton() {
        return this.buttonPanel;
    }

    private Icon createPlaceholderIcon() {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                g.setColor(Color.GRAY); // 圆形颜色
                g.fillOval(x, y, getIconWidth(), getIconHeight());
            }

            @Override
            public int getIconWidth() {
                return 40;
            }

            @Override
            public int getIconHeight() {
                return 40;
            }
        };
    }
}
