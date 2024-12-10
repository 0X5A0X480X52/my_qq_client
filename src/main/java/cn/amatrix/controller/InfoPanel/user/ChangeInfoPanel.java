package cn.amatrix.controller.InfoPanel.user;

import cn.amatrix.model.users.User;
import cn.amatrix.service.users.UserService;
import cn.amatrix.utils.base64.ImageManager;

import javax.swing.*;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.ui.FlatRoundBorder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeInfoPanel extends JPanel {

    private User user;
    private UserService userService;
    
    private JLabel usernameLabel;
    private JPanel userNamePanel;
    private JTextField usernameField;
    private JLabel userIdLabel;
    private JLabel emailLabel;
    private JLabel avatarLabel;

    public ChangeInfoPanel(User user) {
        this.user = user;
        this.userService = new UserService();
        setLayout(new BorderLayout());

        initAvatarLabel(user.getAvatar());
        initUsernameLabel(user.getUsername());
        initUserNamePanel(user.getUsername());
        initUserIdLabel(String.format("%06d", user.getUser_id()));
        initEmailLabel(user.getEmail());

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(avatarLabel);
    
        infoPanel.add(usernameLabel);
        infoPanel.add(userIdLabel);
        infoPanel.add(emailLabel);
        
        infoPanel.add(userNamePanel);

        // 居中放置信息
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

    private void initUsernameLabel(String username) {
        usernameLabel = new JLabel(username);
        usernameLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 16));
        usernameLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
    }

    private void initUserNamePanel(String username) {

        userNamePanel = new JPanel();
        userNamePanel.setMaximumSize(new Dimension(200, 40));
        userNamePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        JLabel usernameLabel = new JLabel("新用户名: ");
        usernameLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
        usernameLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        usernameField = new JTextField(username);
        usernameField.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
        usernameField.setBorder(new FlatRoundBorder());

        userNamePanel.setLayout(new BoxLayout(userNamePanel, BoxLayout.X_AXIS));
        userNamePanel.add(usernameLabel);
        userNamePanel.add(usernameField);
    }

    private void initUserIdLabel(String userId) {
        userIdLabel = new JLabel("UID: " + userId);
        userIdLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
        userIdLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
    }

    private void initEmailLabel(String email) {
        emailLabel = new JLabel("Email: " + email);
        emailLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
        emailLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
    }

    private void saveChanges() {
        String newUsername = usernameField.getText();
        userService.updateUsername(user.getUser_id(), newUsername);
        JOptionPane.showMessageDialog(this, "信息已更新");
        refreshPanel();
    }

    private void changeAvatar() {
        JFrame frame = new ChangeAvatar(user);
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
        user = userService.getUserById(user.getUser_id());
        initAvatarLabel(user.getAvatar());
        initUsernameLabel(user.getUsername());
        initUserNamePanel(user.getUsername());
        initUserIdLabel(String.format("%06d", user.getUser_id()));
        initEmailLabel(user.getEmail());

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(avatarLabel);
        infoPanel.add(usernameLabel);
        infoPanel.add(userIdLabel);
        infoPanel.add(emailLabel);
        infoPanel.add(userNamePanel);

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

    public static void main(String[] args) {
        try {
            // 使用相对路径加载主题文件
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        UserService userService = new UserService();
        User user = userService.getUserById(1);
        ChangeInfoPanel changeInfoPanel = new ChangeInfoPanel(user);
        JFrame frame = new JFrame();
        frame.add(changeInfoPanel);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

