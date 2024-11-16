package cn.amatrix.controller.Login;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginGUI() {

        // 设置界面标题和尺寸
        setTitle("登录界面");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 居中显示

        // 创建主面板
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        // 添加用户名和密码标签及输入框
        panel.add(new JLabel("用户名:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("密码:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        // 创建登录按钮并添加事件监听
        JButton loginButton = new JButton("登录");
        loginButton.addActionListener(new LoginAction());

        // 在主面板底部添加按钮
        panel.add(loginButton);

        // 添加主面板到框架
        add(panel);

        // 设置可见性
        setVisible(true);
    }

    // 登录按钮的动作监听器
    private class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // 简单的用户名密码验证逻辑
            if (username.equals("admin") && password.equals("password")) {
                JOptionPane.showMessageDialog(LoginGUI.this, "登录成功！");
            } else {
                JOptionPane.showMessageDialog(LoginGUI.this, "用户名或密码错误", "登录失败", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginGUI());
    }
}
