package cn.amatrix.controller.Login02.RetrievePassword;

import javax.swing.*;

import cn.amatrix.controller.Login02.RetrievePassword.GetCaptcha.GetCaptcha;
import cn.amatrix.model.message.Message;
import cn.amatrix.service.emailVerfication.EmailVerficationService;
import cn.amatrix.utils.webSocketClient.WebSocketClient;
import cn.amatrix.utils.webSocketClient.WebSocketReceiver;
import cn.amatrix.utils.webSocketClient.receivedWebSocketMessage.ReceivedWebSocketMessageEvent;
import cn.amatrix.utils.webSocketClient.receivedWebSocketMessage.ReceivedWebSocketMessageEventListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
public class RetrievePassword extends JFrame implements WebSocketReceiver{

    //private
    private JTextField userNameField;
    private JTextField emailField;
    private JTextField captchaField;
    JButton sendCaptchaButton;
    JButton retrievePasswordButton;
    WebSocketClient client;

    EmailVerficationService emailVerficationService;

    public RetrievePassword() {
        URI uri = URI.create("ws://localhost:1145/demo_webapp/chat");
        this.client = new WebSocketClient(uri);
        setTitle("找回密码");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new GridBagLayout());
        init();
    }

    void init() {
        userNameField = new JTextField("input username");
        userNameField.setPreferredSize(new Dimension(200,30));
        userNameField.setFont(new Font("Arial",Font.PLAIN,20));// 设置字体样式和大小
        userNameField.setForeground(Color.GRAY);//颜色设置为灰色
        emailField = new JTextField("input Email");// 设置默认提示颜色为灰色
        emailField.setPreferredSize(new Dimension(200,30));
        emailField.setFont(new Font("Arial", Font.PLAIN, 20)); // 设置字体样式和大小
        captchaField = new JTextField("input Captcha");
        captchaField.setPreferredSize(new Dimension(200,30));
        captchaField.setFont(new Font("Arial", Font.PLAIN, 20));
        captchaField.setForeground(Color.GRAY); // 设置默认提示颜色为灰色
        sendCaptchaButton = new JButton("获取验证码");
        retrievePasswordButton = new JButton("确认");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8,8, 8);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        add(userNameField,gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(emailField,gbc);

        gbc.gridx=0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(sendCaptchaButton,gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(captchaField,gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        add(retrievePasswordButton,gbc);

        this.emailVerficationService = new EmailVerficationService(this.client);

        userNameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (userNameField.getText().equals("input username")) {
                    userNameField.setText(""); // 清空文本框
                    userNameField.setForeground(Color.BLACK); // 设置正常输入颜色
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (userNameField.getText().isEmpty()) {
                    userNameField.setText("input username"); // 恢复提示信息
                    userNameField.setForeground(Color.GRAY); // 设置提示颜色
                }
            }
        });

        emailField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (emailField.getText().equals("input Email")) {
                    emailField.setText(""); // 清空文本框
                    emailField.setForeground(Color.BLACK); // 设置正常输入颜色
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (emailField.getText().isEmpty()) {
                    emailField.setText("input Email"); // 恢复提示信息
                    emailField.setForeground(Color.GRAY); // 设置提示颜色
                }
            }
        });

        captchaField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (captchaField.getText().equals("input Captcha")) {
                    captchaField.setText(""); // 清空文本框
                    captchaField.setForeground(Color.BLACK); // 设置正常输入颜色
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (captchaField.getText().isEmpty()) {
                    captchaField.setText("input Captcha"); // 恢复提示信息
                    captchaField.setForeground(Color.GRAY); // 设置提示颜色
                }
            }
        });

        sendCaptchaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userName = userNameField.getText();
                String email = emailField.getText();
                if (userName.isEmpty() || userName.equals("input username")) {
                    JOptionPane.showMessageDialog(null, "警告！请输入账号。", "警告", JOptionPane.WARNING_MESSAGE);
                }
                else if (email.isEmpty()||email.equals("input Email")) {
                    JOptionPane.showMessageDialog(null, "警告！请输入email。", "警告", JOptionPane.WARNING_MESSAGE);
                }
                else if (email.equals("[a-zA-Z0-9_.-]+@qq.com")) {
                       JOptionPane.showMessageDialog(null,"警告！邮箱的格式错误，请输入正确的邮箱。","警告",JOptionPane.WARNING_MESSAGE);
                }
                else{
//                    logger.log(Level.INFO, "Sending captcha to email: " + email);
                    emailVerficationService.getVerificationCode(email);
                    JOptionPane.showMessageDialog(null, "Captcha sent!");
//                    logger.log(Level.INFO, "Captcha sent!");
                }
            }
        });

        retrievePasswordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                emailVerficationService.sendVerificationCode(captchaField.getText());
                String userName = userNameField.getText();
                String email = emailField.getText();
                GetCaptcha getCaptcha=new GetCaptcha( client, userName,email,captchaField.getText());
            }
        });

        addReceivedWebSocketMessageEventListener(new ReceivedWebSocketMessageEventListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (e instanceof ReceivedWebSocketMessageEvent) {
                        ReceivedWebSocketMessageEvent event = (ReceivedWebSocketMessageEvent)e;
                        Message message = event.getMessage();
//                        logger.log(Level.INFO, "Received WebSocket message: " + message.toJson());

                        if (message.getType().equals("EmailVerificationCodeStatus")) {

                            var status =emailVerficationService.handleWebSocketResponse(message);
                            switch(status.getStatus()){
                                case SUCCESS:
                                    JOptionPane.showMessageDialog(null,"验证码验证成功");
                                  //  GetCaptcha getCaptcha= new GetCaptcha(userNameField.getText(),emailField.getText(),captchaField.getText());
                                    break;
                                case EMAILED:
                                    JOptionPane.showMessageDialog(null,"已发送到你的邮箱");
                                    break;
                                case FAILED:
                                    JOptionPane.showMessageDialog(null,"验证码发送失败！"+status.getAdditionalInfo());
                                    break;
                                case TIMEOUT:
                                    JOptionPane.showMessageDialog(null,"验证码已过期。");
                                    break;
                                case INVALID:
                                    JOptionPane.showMessageDialog(null,"验证码错误。");
                                    break;
                            }
                            String info = "Status: " + status.getStatus() + "\nInfo: " + status.getAdditionalInfo();
                            JOptionPane.showMessageDialog(null, info );
//                            logger.log(Level.INFO, "WebSocket received: " + info);
                        }
                    }
                } catch (Exception ex) {
//                    logger.log(Level.SEVERE, "Error processing WebSocket message", ex);
                }
            }
        });


        }
}
