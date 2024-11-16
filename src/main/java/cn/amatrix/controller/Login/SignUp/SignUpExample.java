package cn.amatrix.controller.Login.SignUp;

import javax.swing.*;

import cn.amatrix.model.message.Message;
import cn.amatrix.model.message.Message.MessageEndPoint;
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
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField captchaField;
    private JButton sendCaptchaButton;
    private JButton signUpButton;
    
    WebSocketClient client;

    public SignUpExample() {
        URI uri = URI.create("ws://localhost:1145/demo_webapp/chat");
        this.client = new WebSocketClient(uri);

        setTitle("Sign Up");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout()); // 使用GridBagLayout布局

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // 设置控件之间的间距

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        JLabel captchaLabel = new JLabel("Captcha:");
        captchaField = new JTextField();
        sendCaptchaButton = new JButton("Send Captcha");
        signUpButton = new JButton("Sign Up");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(captchaLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(captchaField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(sendCaptchaButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(signUpButton, gbc);

        sendCaptchaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement sending captcha logic here

                String email = emailField.getText();
                logger.log(Level.INFO, "Sending captcha to email: " + email);

                MessageEndPoint receiver = new MessageEndPoint();
                receiver.setId("receiver1");
                receiver.setType("user");
                MessageEndPoint sender = new MessageEndPoint();
                sender.setId("sender1");
                sender.setType("user");
                Message message = new Message( receiver, sender,"getVerificationCode", email, "请求验证码");
                client.sendMessage(message.toJson());

                JOptionPane.showMessageDialog(null, "Captcha sent!");
                logger.log(Level.INFO, "Captcha sent!");
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement sign up logic here

                String captcha = captchaField.getText();
                logger.log(Level.INFO, "Signing up with captcha: " + captcha);

                MessageEndPoint receiver = new MessageEndPoint();
                receiver.setId("receiver1");
                receiver.setType("user");
                MessageEndPoint sender = new MessageEndPoint();
                sender.setId("sender1");
                sender.setType("user");
                Message message = new Message( receiver, sender,"checkVerificationCode", captcha, "请求验证码");
                client.sendMessage(message.toJson());

                // // Validate and process the sign-up information
                // JOptionPane.showMessageDialog(null, "Sign Up successful!");
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
                            String status = message.getStatus();
                            JOptionPane.showMessageDialog(null, "Captcha received: " + status);
                            logger.log(Level.INFO, "Captcha received: " + status);
                        }
                    }
                } catch (Exception ex) {
                    logger.log(Level.SEVERE, "Error processing WebSocket message", ex);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SignUpExample().setVisible(true);
                logger.log(Level.INFO, "SignUpExample application started");
            }
        });
    }
}