package cn.amatrix.controller.InfoPanel.user;

import cn.amatrix.model.users.User;
import cn.amatrix.service.users.UserService;
import cn.amatrix.utils.base64.ImageManager;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ChangeAvatar extends JFrame {

    private User user;
    private JLabel avatarLabel;

    public ChangeAvatar(User user) {
        this.user = user;
        setTitle("修改头像");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        avatarLabel = new JLabel();
        avatarLabel.setHorizontalAlignment(JLabel.CENTER);
        avatarLabel.setPreferredSize(new Dimension(256, 256));
        updateAvatarLabel(user.getAvatar());

        JButton changeButton = new JButton("选择新头像");
        changeButton.addActionListener(e -> selectNewAvatar());

        add(avatarLabel, BorderLayout.CENTER);
        add(changeButton, BorderLayout.SOUTH);
    }

    private void updateAvatarLabel(String base64Image) {
        try {
            BufferedImage image = ImageManager.base64ToCircularImage(base64Image);
            if (image != null) {
                Image scaledImage = ImageManager.resizeImage(image, 256, 256);
                avatarLabel.setIcon(new ImageIcon(scaledImage));
            } else {
                avatarLabel.setIcon(createPlaceholderIcon());
            }
        } catch (IOException e) {
            avatarLabel.setIcon(createPlaceholderIcon());
            e.printStackTrace();
        }
    }

    private void selectNewAvatar() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                BufferedImage originalImage = ImageIO.read(selectedFile);
                showCropWindow(originalImage);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "无法读取图像文件: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void showCropWindow(BufferedImage originalImage) {
        JFrame cropFrame = new JFrame("裁剪头像预览");
        cropFrame.setSize(400, 400);
        cropFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cropFrame.setLayout(new BorderLayout());
        cropFrame.setLocationRelativeTo(this);

        // 调整图像大小为窗口大小
        Image scaledImage = originalImage.getScaledInstance(400, 400, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();

        BufferedImage circleCropedImage = ImageManager.cropToCircle(resizedImage);
        JLabel imageLabel = new JLabel(new ImageIcon(circleCropedImage));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        JButton cropButton = new JButton("裁剪并保存");
        cropButton.addActionListener(e -> {
            try {
                String base64Image = ImageManager.imageToBase64(originalImage, 256, 256);
                user.setAvatar(base64Image);
                UserService userService = new UserService();
                userService.updateUser(user);
                updateAvatarLabel(base64Image);
                JOptionPane.showMessageDialog(this, "头像更新成功");
                cropFrame.dispose();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "头像更新失败: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        cropFrame.add(imageLabel, BorderLayout.CENTER);
        cropFrame.add(cropButton, BorderLayout.SOUTH);
        cropFrame.setVisible(true);
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
                return 256;
            }

            @Override
            public int getIconHeight() {
                return 256;
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
        ChangeAvatar changeAvatar = new ChangeAvatar(user);
        changeAvatar.setVisible(true);
    }
}
