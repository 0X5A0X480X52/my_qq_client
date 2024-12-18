package cn.amatrix.controller.LoginDemo.RetrievePassword.GetCaptcha;

import cn.amatrix.controller.LoginDemo.LoginGUI;
import cn.amatrix.controller.LoginDemo.RetrievePassword.RetrievePassword;
import cn.amatrix.model.message.Message;
import cn.amatrix.service.emailVerfication.EmailVerficationService;
import cn.amatrix.utils.webSocketClient.WebSocketClient;
import cn.amatrix.utils.webSocketClient.WebSocketReceiver;
import cn.amatrix.utils.webSocketClient.receivedWebSocketMessage.ReceivedWebSocketMessageEvent;
import cn.amatrix.utils.webSocketClient.receivedWebSocketMessage.ReceivedWebSocketMessageEventListener;


import javax.swing.*;
import java.awt.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GetCaptcha extends JFrame implements WebSocketReceiver {
    private static final Logger logger = Logger.getLogger(RetrievePassword.class.getName());

    static {
        try {
            FileHandler fileHandler = new FileHandler("RetrievePassword.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to initialize log file handler", e);
        }
    }

    JTextField newpasswordField;
    JLabel passwordLabel,Label1,Label2;
    JTextField passwordField;
    JButton PasswordButton;
    WebSocketClient client;
    EmailVerficationService emailVerficationService;
    String username, email, captcha;

    public GetCaptcha() {}

   public GetCaptcha( WebSocketClient client, String username, String email, String captcha) {
        this.client = client;
        this.username = username;
        this.email = email;
        this.captcha = captcha;
        setTitle("验证");
       setSize(600, 400);
       setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       setLocationRelativeTo(null);
       setLayout(new GridBagLayout());
        init();

       setVisible(true);
    }

    void init() {
        this.emailVerficationService=new EmailVerficationService(this.client);

        newpasswordField = new JPasswordField("");
        newpasswordField.setPreferredSize(new Dimension(400, 50));
        newpasswordField.setFont(new Font("Arial", Font.PLAIN, 30)); // 设置字体样式和大小
        newpasswordField.setForeground(Color.BLACK); // 设置默认提示颜色为黑色
        passwordField = new JPasswordField("");
        passwordField.setPreferredSize(new Dimension(400, 50));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 30)); // 设置字体样式和大小
        passwordField.setForeground(Color.BLACK); // 设置默认提示颜色为黑色
        PasswordButton = new JButton("确定");
        PasswordButton.setPreferredSize(new Dimension(200, 50));
        passwordLabel = new JLabel("密码由8-16位数字、字母或符号组成");
        Label1=new JLabel("密码");
        Label1.setForeground(Color.BLACK);
        Label2=new JLabel("再次确定密码");
        Label2.setForeground(Color.BLACK);
        passwordLabel.setForeground(Color.GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        add(Label1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(passwordField, gbc);

        gbc.gridx=0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(Label2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(newpasswordField, gbc);

        gbc.gridx=0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        add(passwordLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        add(PasswordButton, gbc);

        PasswordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String password = newpasswordField.getText();
                String passwordAgain = passwordField.getText();
                if (password.equals("input password") && passwordAgain.equals("input password again")) {
                    JOptionPane.showMessageDialog(null, "请输入修改的密码");
                } else {
                    if (password.equals(passwordAgain)) {
                        if (password.length() < 8 || password.length() > 16 || !password.matches("^[a-zA-Z\\d!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]{6,16}")) {
                            JOptionPane.showMessageDialog(null, "密码格式错误！请重新输入。");
                        } else {
                            emailVerficationService.submitPasswordRecoveryInformation(captcha, email, password);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "密码不同，请重新输入");
                    }
                }
            }
        });

        addReceivedWebSocketMessageEventListener(new ReceivedWebSocketMessageEventListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    try {
                        if (e instanceof ReceivedWebSocketMessageEvent) {
                            ReceivedWebSocketMessageEvent event = (ReceivedWebSocketMessageEvent) e;
                            Message message = event.getMessage();
                            logger.log(Level.INFO, "Received WebSocket message: " + message.toJson());

                            if (message.getType().equals("EmailVerificationCodeStatus")) {

                                var status = emailVerficationService.handleWebSocketResponse(message);
                                switch (status.getStatus()) {
                                    case SUCCESS:
                                        JOptionPane.showMessageDialog(null, "密码修改成功");
                                        new LoginGUI(client);
                                        GetCaptcha.this.dispose();
                                        break;
                                    case FAILED:
                                        JOptionPane.showMessageDialog(null, "密码修改失败！" + status.getAdditionalInfo());
                                        break;
                                    case TIMEOUT:
                                        JOptionPane.showMessageDialog(null, "验证码已过期。");
                                        break;
                                    case INVALID:
                                        JOptionPane.showMessageDialog(null, "验证码错误。");
                                        break;
                                    case EMAILED:
                                        JOptionPane.showMessageDialog(null, "验证码已发送。");
                                        break;
                                    case HAVESIGNUP:
                                        JOptionPane.showMessageDialog(null, "用户已注册。");
                                        break;
                                    case UNKNOWN:
                                        JOptionPane.showMessageDialog(null, "未知错误。");
                                        break;
                                    case UNSIGNUP:
                                        JOptionPane.showMessageDialog(null, "用户未注册。");
                                        break;
                                }
                                String info = "Status: " + status.getStatus() + "\nInfo: " + status.getAdditionalInfo();
                                logger.log(Level.INFO, "WebSocket received: " + info);
                            }
                        }
                    } catch (Exception ex) {
                        logger.log(Level.SEVERE, "Error processing WebSocket message", ex);
                    }
                }
        });
    }

}
