package cn.amatrix.controller.friendRequest.commponent;

import cn.amatrix.model.users.User;
import cn.amatrix.utils.base64.ImageManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class UserInfoPanel extends JPanel {

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

    public UserInfoPanel(JComponent parentComponent, User user, ActionListener sendRequestListener) {
        setLayout(new BorderLayout());
        this.parentComponent = parentComponent;
        int parentWidth = this.parentComponent.getWidth();
        setPreferredSize(new Dimension(parentWidth, FIXED_HEIGHT)); // 宽度为父组件宽度，高度固定

        // String base64Image = "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";

        initAvatarIcon(user.getAvatar());
        // initAvatarIcon(base64Image);
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
    }

    private void initAvatarIcon(String base64Image) {
        avatarIcon = new JLabel();
        avatarIcon.setPreferredSize(new Dimension(40, 40));
        avatarIcon.setOpaque(false);
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
