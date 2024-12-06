package cn.amatrix.controller.Login02;
import cn.amatrix.controller.Login02.RetrievePassword.RetrievePassword;
import cn.amatrix.controller.Login02.SignUp.SignUpExample;
import cn.amatrix.service.signUp.SignUpService;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;

public class LoginGUI1 extends JFrame {
    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton;//登录按钮
    JButton registerButton;//注册按钮
    JButton resetpasswordButton;//找回密码按钮

    public LoginGUI1() {
        setTitle("登录界面");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        setVisible(true);

        init();
    }

    void init() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // 设置控件之间的间距
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 30));
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 30));
        JLabel userNameLabel = new JLabel("账号:");
        JLabel passwordLabel = new JLabel("密码");
        loginButton = new JButton("登录");

        registerButton = new JButton("注册");

        resetpasswordButton = new JButton("找回密码");


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        add(userNameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(loginButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(registerButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(resetpasswordButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignUpExample signUpExample = new SignUpExample();
            }
        });

        resetpasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RetrievePassword retrievePassword = new RetrievePassword();
            }
        });
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginGUI1();
            }
        });
    }
}