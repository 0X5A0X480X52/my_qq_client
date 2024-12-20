package cn.amatrix.controller.InfoPanel.group;

import cn.amatrix.model.groups.Group;
import cn.amatrix.service.groups.GroupService;
import cn.amatrix.utils.base64.ImageManager;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ChangeGroupAvatar extends JFrame {

    private Group group;
    private JLabel avatarLabel;

    public ChangeGroupAvatar(Group group) {
        this.group = group;
        setTitle("修改群组头像");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        avatarLabel = new JLabel();
        avatarLabel.setHorizontalAlignment(JLabel.CENTER);
        avatarLabel.setPreferredSize(new Dimension(256, 256));
        updateAvatarLabel(group.getAvatar());

        JButton changeButton = new JButton("选择新头像");
        changeButton.addActionListener(e -> selectNewAvatar());

        add(avatarLabel, BorderLayout.CENTER);
        add(changeButton, BorderLayout.SOUTH);
    }

    private void updateAvatarLabel(String base64Image) {
        if (base64Image == null || base64Image.isEmpty() || base64Image.equals("null") || base64Image.equals("\"null\""))  {
            avatarLabel.setIcon(createPlaceholderIcon());
            return;
        }

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

        Image scaledImage = originalImage.getScaledInstance(400, 400, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();

        BufferedImage circleCroppedImage = ImageManager.cropToCircle(resizedImage);
        JLabel imageLabel = new JLabel(new ImageIcon(circleCroppedImage));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        JButton cropButton = new JButton("裁剪并保存");
        cropButton.addActionListener(e -> {
            try {
                String base64Image = ImageManager.imageToBase64(originalImage, 256, 256);
                group.setAvatar(base64Image);
                GroupService groupService = new GroupService();
                groupService.updateGroup(group);
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
}
