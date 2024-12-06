package cn.amatrix.controller.Login02.RetrievePassword.GetCaptcha;

import cn.amatrix.controller.Login02.LoginGUI1;
import cn.amatrix.controller.Login02.RetrievePassword.RetrievePassword;
import cn.amatrix.model.message.Message;
import cn.amatrix.service.emailVerfication.EmailVerficationService;
import cn.amatrix.utils.webSocketClient.WebSocketClient;
import cn.amatrix.utils.webSocketClient.WebSocketReceiver;
import cn.amatrix.utils.webSocketClient.receivedWebSocketMessage.ReceivedWebSocketMessageEvent;
import cn.amatrix.utils.webSocketClient.receivedWebSocketMessage.ReceivedWebSocketMessageEventListener;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
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
    JLabel passwordLabel;
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
       setSize(400, 300);
       setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       setLocationRelativeTo(null);
       setLayout(new GridBagLayout());
        init();

       setVisible(true);
    }

    void init() {
        // URI uri = URI.create("ws://localhost:1145/demo_webapp/chat");
        // this.client = new WebSocketClient(uri);
        this.emailVerficationService=new EmailVerficationService(this.client);

        newpasswordField = new JTextField("input password");
        newpasswordField.setPreferredSize(new Dimension(200, 30));
        newpasswordField.setFont(new Font("Arial", Font.PLAIN, 20)); // 设置字体样式和大小
        newpasswordField.setForeground(Color.GRAY); // 设置默认提示颜色为灰色
        passwordField = new JTextField("input password again");
        passwordField.setPreferredSize(new Dimension(200, 30));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 20)); // 设置字体样式和大小
        passwordField.setForeground(Color.GRAY); // 设置默认提示颜色为灰色
        PasswordButton = new JButton("确定");
        passwordLabel = new JLabel("密码由8-16位数字、字母或符号组成");
        passwordLabel.setForeground(Color.GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(newpasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(passwordLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(PasswordButton, gbc);

        newpasswordField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (newpasswordField.getText().equals("input password")) {
                    newpasswordField.setText(""); // 清空文本框
                    newpasswordField.setForeground(Color.BLACK); // 设置正常输入颜色
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (newpasswordField.getText().isEmpty()) {
                    newpasswordField.setText("input password"); // 恢复提示信息
                    newpasswordField.setForeground(Color.GRAY); // 设置提示颜色
                }
            }
        });

        passwordField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (passwordField.getText().equals("input password again")) {
                    passwordField.setText(""); // 清空文本框
                    passwordField.setForeground(Color.BLACK); // 设置正常输入颜色
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (passwordField.getText().isEmpty()) {
                    passwordField.setText("input password again"); // 恢复提示信息
                    passwordField.setForeground(Color.GRAY); // 设置提示颜色
                }
            }
        });

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
                            JOptionPane.showMessageDialog(null, "密码修改成功！请重新登录");
                            LoginGUI1 loginGUI1 = new LoginGUI1();
                            GetCaptcha.this.dispose();
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
                            }
                            String info = "Status: " + status.getStatus() + "\nInfo: " + status.getAdditionalInfo();
                            JOptionPane.showMessageDialog(null, info);
                            logger.log(Level.INFO, "WebSocket received: " + info);
                        }
                    }
                } catch (Exception ex) {
                    logger.log(Level.SEVERE, "Error processing WebSocket message", ex);
                }
            }
        });
    }
    // public static void main(String args[]){
    //     GetCaptcha getCaptcha = new GetCaptcha("scxwa","sca","232");
    // }
}
