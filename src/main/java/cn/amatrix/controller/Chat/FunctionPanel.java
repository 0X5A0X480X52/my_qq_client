package cn.amatrix.controller.Chat;


import cn.amatrix.controller.InfoPanel.user.UserDetailedInfoPanel;
import cn.amatrix.controller.friendRequest.friendRequestDemo;
import cn.amatrix.controller.groupRequest.GroupRequestDemo;
import cn.amatrix.model.users.User;
import cn.amatrix.service.users.UserService;
import cn.amatrix.utils.base64.ImageManager;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

class FunctionPanel extends JPanel {
    private User currentUser;
    private JLabel avatarIcon;

    private JPanel AvatarPanel;
    private JPanel buttonPanel;

    FunctionPanel(User currentUser, JPanel chatPanel) {
        this.currentUser = currentUser;
        this.setPreferredSize(new Dimension(50, 500));
        this.setMaximumSize(new Dimension(50,Integer.MAX_VALUE));
        this.setLayout(new BorderLayout());

        AvatarPanel = new JPanel();
        AvatarPanel.setPreferredSize(new Dimension(50, 50));
        AvatarPanel.setMaximumSize(new Dimension(50, 50));
        initAvatarIcon(currentUser.getAvatar());
        AvatarPanel.add(avatarIcon);
        AvatarPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    showDetailedInfoPanel(currentUser);
                }
                AvatarPanel.removeAll();
                UserService userService = new UserService();
                FunctionPanel.this.currentUser = userService.getUserById(FunctionPanel.this.currentUser.getUser_id());
                initAvatarIcon(FunctionPanel.this.currentUser.getAvatar());
                AvatarPanel.add(avatarIcon);
                AvatarPanel.revalidate();
            }
        });

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        try {
            InputStream imageStream1 = getClass().getResourceAsStream("/icons/chat01.png");
            BufferedImage originalImage1 = ImageIO.read(imageStream1);
            BufferedImage resizedImage = ImageManager.resizeImage(originalImage1, 40, 40);
            JLabel chatLabel = new JLabel(new ImageIcon(resizedImage));
            JPanel chatButtonPanel = new JPanel();
            chatButtonPanel.setLayout(new BorderLayout());
            chatButtonPanel.add(chatLabel, BorderLayout.CENTER);
            chatButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            chatButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            chatButtonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

            InputStream imageStream2 = getClass().getResourceAsStream("/icons/user01.png");
            BufferedImage originalImage2 = ImageIO.read(imageStream2);
            resizedImage = ImageManager.resizeImage(originalImage2, 40, 40);
            JLabel friendRequestLabel = new JLabel(new ImageIcon(resizedImage));
            JPanel friendRequestButtonPanel = new JPanel();
            friendRequestButtonPanel.setLayout(new BorderLayout());
            friendRequestButtonPanel.add(friendRequestLabel, BorderLayout.CENTER);
            friendRequestButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            friendRequestButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            friendRequestButtonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

            InputStream imageStream3 = getClass().getResourceAsStream("/icons/group01.png");
            BufferedImage originalImage3 = ImageIO.read(imageStream3);
            resizedImage = ImageManager.resizeImage(originalImage3, 40, 40);
            JLabel groupLabel = new JLabel(new ImageIcon(resizedImage));
            JPanel groupButtonPanel = new JPanel();
            groupButtonPanel.setLayout(new BorderLayout());
            groupButtonPanel.add(groupLabel, BorderLayout.CENTER);
            groupButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            groupButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            groupButtonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

            this.add(AvatarPanel, BorderLayout.NORTH);
            this.add(buttonPanel, BorderLayout.CENTER);
            buttonPanel.add(chatButtonPanel);
            buttonPanel.add(friendRequestButtonPanel);
            buttonPanel.add(groupButtonPanel);

            chatButtonPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        openChat();
                    }
                }
            });

            friendRequestButtonPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        openFriendRequest();
                    }
                }
            });

            groupButtonPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        openGroupRequest();
                    }
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void openChat(){
        // InfoPanelList infoPanelList = new InfoPanelList(currentUser,qq,chatPanel);
    }

    private void openFriendRequest() {
    friendRequestDemo friendRequest = new friendRequestDemo(currentUser.getUser_id());
    friendRequest.setVisible(true);
    }

    private void openGroupRequest(){
    GroupRequestDemo groupRequest = new GroupRequestDemo(currentUser.getUser_id());
    groupRequest.setVisible(true);
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

    private void showDetailedInfoPanel(User user) {
        JFrame frame = new JFrame("用户详细信息");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.add(new UserDetailedInfoPanel(user, currentUser));
        frame.setVisible(true);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                AvatarPanel.removeAll();
                initAvatarIcon(currentUser.getAvatar());
                AvatarPanel.add(avatarIcon);
            }
        });
    }
}

