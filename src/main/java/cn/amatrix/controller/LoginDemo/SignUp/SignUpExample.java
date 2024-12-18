package cn.amatrix.controller.LoginDemo.SignUp;

import javax.swing.*;

import cn.amatrix.controller.LoginDemo.LoginGUI;
import cn.amatrix.model.message.Message;
import cn.amatrix.service.signUp.SignUpService;
import cn.amatrix.utils.webSocketClient.WebSocketClient;
import cn.amatrix.utils.webSocketClient.WebSocketReceiver;
import cn.amatrix.utils.webSocketClient.receivedWebSocketMessage.ReceivedWebSocketMessageEvent;
import cn.amatrix.utils.webSocketClient.receivedWebSocketMessage.ReceivedWebSocketMessageEventListener;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.regex.Pattern;

public class SignUpExample extends JFrame implements WebSocketReceiver {
    private static final Logger logger = Logger.getLogger(SignUpExample.class.getName());
    static {
        try {
            FileHandler fileHandler = new FileHandler("SignUpExample.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to initialize log file handler", e);
        }
    }

    private JTextField userNameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField captchaField;
    private JButton sendCaptchaButton;
    private JButton signUpButton;
    
    WebSocketClient client;

    SignUpService signUpService;

    public SignUpExample() {
        URI uri = URI.create("ws://47.97.117.157:8080/demo_webapp/chat");
        this.client = new WebSocketClient(uri);

        setTitle("Sign Up");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout()); // 使用GridBagLayout布局
        setVisible(true);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // 设置控件之间的间距

        JLabel passwordLabel1;
        passwordLabel1 = new JLabel("密码由8-16位数字、字母或符号组成");
        passwordLabel1.setForeground(Color.GRAY);
        passwordLabel1.setFont(new Font("Microsoft YaHei", Font.PLAIN, 10));
        JLabel userNameLabel = new JLabel("用户名:");
        userNameField = new JTextField();
        userNameField.setPreferredSize(new Dimension(200, 30));
        JLabel emailLabel = new JLabel("Email邮箱:");
        emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(200, 30));
        JLabel passwordLabel = new JLabel("密码:");
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 30));
        JLabel captchaLabel = new JLabel("验证码:");
        captchaField = new JTextField();
        captchaField.setPreferredSize(new Dimension(100, 30));
        sendCaptchaButton = new JButton("获取验证码");
        signUpButton = new JButton("注册");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        add(userNameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(userNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(captchaLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(captchaField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(sendCaptchaButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(passwordField, gbc);

        gbc.gridx=1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(passwordLabel1, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(signUpButton, gbc);

        this.signUpService = new SignUpService(this.client);

        sendCaptchaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String userName = userNameField.getText();
                String email = emailField.getText();
                logger.log(Level.INFO, "Sending captcha to email: " + email);

                String regex = "^[a-zA-Z0-9_.-]+@qq.com$";
                if (userName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "警告！请输入账号。", "警告", JOptionPane.WARNING_MESSAGE);
                }
                else if (email.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "警告！请输入email。", "警告", JOptionPane.WARNING_MESSAGE);
                }
                else if (!Pattern.matches(regex, email)) {
                    JOptionPane.showMessageDialog(null,"警告！邮箱的格式错误，请输入正确的邮箱。","警告",JOptionPane.WARNING_MESSAGE);
                }
                else {

                    signUpService.getVerificationCode(email);

                    logger.log(Level.INFO, "Captcha sent!");
                }
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String captcha = captchaField.getText();
                String username = userNameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                logger.log(Level.INFO, "Signing up with captcha: " + captcha);
                String regex = "^[a-zA-Z0-9_.-]+@qq.com$";
                if (username.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "警告！请输入账号。", "警告", JOptionPane.WARNING_MESSAGE);
                }
                else if (email.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "警告！请输入email。", "警告", JOptionPane.WARNING_MESSAGE);
                }
                else if (!Pattern.matches(regex, email)) {
                    JOptionPane.showMessageDialog(null,"警告！邮箱的格式错误，请输入正确的邮箱。","警告",JOptionPane.WARNING_MESSAGE);
                }
                else if (password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "警告！请输入密码。", "警告", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    if (password.length() < 8 || password.length() > 16 || !password.matches("^[a-zA-Z\\d!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]{6,16}")) {
                        JOptionPane.showMessageDialog(null, "密码格式错误！请重新输入。");
                    }
                    else {
                        signUpService.submitSignUpInformation(captcha, username, email, password);
                    }
                }
            }
        });

        addReceivedWebSocketMessageEventListener(new ReceivedWebSocketMessageEventListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (e instanceof ReceivedWebSocketMessageEvent) {
                        ReceivedWebSocketMessageEvent event = (ReceivedWebSocketMessageEvent)e;
                        Message message = event.getMessage();
                        logger.log(Level.INFO, "Received WebSocket message: " + message.toJson());

                        if (message.getType().equals("EmailVerificationCodeStatus")) {

                            var status = signUpService.handleWebSocketResponse(message);
                            String info = "Status: " + status.getStatus() + "\nInfo: " + status.getAdditionalInfo();
                            logger.log(Level.INFO, "WebSocket received: " + info);
                            switch(status.getStatus()){
                                case SUCCESS:
                                    JOptionPane.showMessageDialog(null,"注册成功");
                                    new LoginGUI(client);
                                    SignUpExample.this.dispose();
                                    break;
                                case EMAILED:
                                    JOptionPane.showMessageDialog(null,"已发送到你的邮箱！");
                                    break;
                                case FAILED:
                                    JOptionPane.showMessageDialog(null,"注册失败！"+status.getAdditionalInfo());
                                    break;
                                case TIMEOUT:
                                    JOptionPane.showMessageDialog(null,"验证码已过期。");
                                    break;
                                case INVALID:
                                    JOptionPane.showMessageDialog(null,"验证码错误。");
                                    break;
                                case UNKNOWN:
                                    JOptionPane.showMessageDialog(null,"未知错误。");
                                    break;
                                default:
                                    JOptionPane.showMessageDialog(null,"未知错误。");
                                    break;
                            }

                        }
                    }
                } catch (Exception ex) {
                    logger.log(Level.SEVERE, "Error processing WebSocket message", ex);
                }
            }
        });

        JButton returnbutton=new JButton("<返回");
        returnbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SignUpExample.this.dispose();
                new LoginGUI(client);
            }
        });

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        add(returnbutton, gbc);
    }
}