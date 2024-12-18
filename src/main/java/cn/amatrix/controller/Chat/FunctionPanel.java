package cn.amatrix.controller.Chat;


import cn.amatrix.controller.InfoPanel.user.UserDetailedInfoPanel;
import cn.amatrix.controller.friendRequest.friendRequestDemo;
import cn.amatrix.controller.groupRequest.GroupRequestDemo;
import cn.amatrix.model.users.User;
import cn.amatrix.utils.base64.ImageManager;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
        // AvatarPanel.setBackground(Color.CYAN);//头像
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
            }
        });

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        // buttonPanel.setBackground(Color.GRAY);
        // buttonPanel.setPreferredSize(new Dimension(50, 120));

        try {
            BufferedImage originalImage1 = ImageIO.read(new File("src\\main\\resources\\icons\\chat01.png"));
            BufferedImage resizedImage = ImageManager.resizeImage(originalImage1, 50, 50);
            JButton chatButton = new JButton();
            chatButton.setIcon(new ImageIcon(resizedImage));
            JPanel chatButtonPanel = new JPanel();
            chatButtonPanel.setLayout(new BorderLayout());
            chatButtonPanel.add(chatButton, BorderLayout.CENTER);
            chatButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            // chatButtonPanel.setPreferredSize(new Dimension(50, 50));
            // chatButtonPanel.setMaximumSize(new Dimension(50, 50));

            BufferedImage originalImage2 = ImageIO.read(new File("src\\main\\resources\\icons\\user01.png"));
            resizedImage = ImageManager.resizeImage(originalImage2, 50, 50);
            JButton friendRequestButton = new JButton();
            friendRequestButton.setIcon(new ImageIcon(resizedImage));
            JPanel friendRequestButtonPanel = new JPanel();
            friendRequestButtonPanel.setLayout(new BorderLayout());
            friendRequestButtonPanel.add(friendRequestButton, BorderLayout.CENTER);
            friendRequestButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            // friendRequestButtonPanel.setPreferredSize(new Dimension(50, 50));
            // friendRequestButtonPanel.setMaximumSize(new Dimension(50, 50));

            BufferedImage originalImage3 = ImageIO.read(new File("src\\main\\resources\\icons\\group01.png"));
            resizedImage = ImageManager.resizeImage(originalImage3, 50, 50);
            JButton groupButton = new JButton();
            groupButton.setIcon(new ImageIcon(resizedImage));
            JPanel groupButtonPanel = new JPanel();
            groupButtonPanel.setLayout(new BorderLayout());
            groupButtonPanel.add(groupButton, BorderLayout.CENTER);
            groupButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            // groupButtonPanel.setPreferredSize(new Dimension(50, 50));
            // groupButtonPanel.setMaximumSize(new Dimension(50, 50));

            this.add(AvatarPanel, BorderLayout.NORTH);
            this.add(buttonPanel, BorderLayout.CENTER);
            buttonPanel.add(chatButton);
            buttonPanel.add(friendRequestButton);
            buttonPanel.add(groupButton);

            chatButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openChat();
                }
            });

            friendRequestButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openFriendRequest();
                }
            });

            groupButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openGroupRequest();
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

