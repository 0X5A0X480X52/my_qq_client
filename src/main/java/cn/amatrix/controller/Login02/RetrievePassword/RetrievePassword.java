package cn.amatrix.controller.Login02.RetrievePassword;

import javax.swing.*;

import cn.amatrix.controller.Login02.LoginGUI;
import cn.amatrix.controller.Login02.RetrievePassword.GetCaptcha.GetCaptcha;
import cn.amatrix.model.message.Message;
import cn.amatrix.service.emailVerfication.EmailVerficationService;
import cn.amatrix.utils.webSocketClient.WebSocketClient;
import cn.amatrix.utils.webSocketClient.WebSocketReceiver;
import cn.amatrix.utils.webSocketClient.receivedWebSocketMessage.ReceivedWebSocketMessageEvent;
import cn.amatrix.utils.webSocketClient.receivedWebSocketMessage.ReceivedWebSocketMessageEventListener;

import java.awt.event.*;

import java.awt.*;
import java.net.URI;
import java.util.regex.Pattern;

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
        URI uri = URI.create("ws://47.97.117.157:8080/demo_webapp/chat");
        this.client = new WebSocketClient(uri);
        setTitle("找回密码");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new GridBagLayout());
        init();
    }

    void init() {
        userNameField = new JTextField("请输入用户ID");
        userNameField.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        userNameField.setHorizontalAlignment(SwingConstants.CENTER);
        userNameField.setPreferredSize(new Dimension(200,30));
        userNameField.setForeground(Color.GRAY);//颜色设置为灰色
        emailField = new JTextField("请输入Email");// 设置默认提示颜色为灰色
        emailField.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        emailField.setHorizontalAlignment(SwingConstants.CENTER);
        emailField.setPreferredSize(new Dimension(200, 30));
        emailField.setForeground(Color.GRAY);
        captchaField = new JTextField("请输入验证码");
        captchaField.setPreferredSize(new Dimension(100,30));
        captchaField.setFont(new Font("Microsoft YaHei", Font.PLAIN, 10));
        captchaField.setHorizontalAlignment(SwingConstants.CENTER);
        captchaField.setForeground(Color.GRAY); // 设置默认提示颜色为灰色
        sendCaptchaButton = new JButton("获取验证码");
        retrievePasswordButton = new JButton("确认");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8,8, 8);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(userNameField,gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(emailField,gbc);

        gbc.gridx=1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(sendCaptchaButton,gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(captchaField,gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(retrievePasswordButton,gbc);

        this.emailVerficationService = new EmailVerficationService(this.client);

        userNameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // 当获得焦点时，清空提示文本，并设置隐藏字符
                if (String.valueOf(userNameField.getText()).equals("请输入用户ID")) {
                    userNameField.setText("");
                    userNameField.setForeground(Color.BLACK); // 设置正常输入颜色
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // 当失去焦点时，如果没有输入内容，则显示提示文本
                if (String.valueOf(userNameField.getText()).isEmpty()) {
                    userNameField.setText("请输入用户ID");
                    userNameField.setForeground(Color.GRAY); // 设置提示颜色
                }
            }
        });

        emailField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (emailField.getText().equals("请输入Email")) {
                    emailField.setText(""); // 清空文本框
                    emailField.setForeground(Color.BLACK); // 设置正常输入颜色
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (emailField.getText().isEmpty()) {
                    emailField.setText("请输入Email"); // 恢复提示信息
                    emailField.setForeground(Color.GRAY); // 设置提示颜色
                }
            }
        });

        captchaField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (captchaField.getText().equals("请输入验证码")) {
                    captchaField.setText(""); // 清空文本框
                    captchaField.setForeground(Color.BLACK); // 设置正常输入颜色
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (captchaField.getText().isEmpty()) {
                    captchaField.setText("请输入验证码"); // 恢复提示信息
                    captchaField.setForeground(Color.GRAY); // 设置提示颜色
                }
            }
        });

        sendCaptchaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userName = userNameField.getText();
                String email = emailField.getText();
                String regex = "^[a-zA-Z0-9_.-]+@qq.com$";
                if (userName.isEmpty() || userName.equals("请输入用户ID")) {
                    JOptionPane.showMessageDialog(null, "警告！请输入账号。", "警告", JOptionPane.WARNING_MESSAGE);
                }
                else if (email.isEmpty()||email.equals("请输入Email")) {
                    JOptionPane.showMessageDialog(null, "警告！请输入email。", "警告", JOptionPane.WARNING_MESSAGE);
                }
                else if (!Pattern.matches(regex, email)) {
                    JOptionPane.showMessageDialog(null,"警告！邮箱的格式错误，请输入正确的邮箱。","警告",JOptionPane.WARNING_MESSAGE);
                }
                else{
                    emailVerficationService.getVerificationCode(email);
                }
            }
        });

        retrievePasswordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userName = userNameField.getText();
                String email = emailField.getText();
                String regex = "^[a-zA-Z0-9_.-]+@qq.com$";
                if (userName.isEmpty() || userName.equals("请输入用户ID")) {
                    JOptionPane.showMessageDialog(null, "警告！请输入账号。", "警告", JOptionPane.WARNING_MESSAGE);
                }else if (email.isEmpty()||email.equals("请输入Email")) {
                    JOptionPane.showMessageDialog(null, "警告！请输入email。", "警告", JOptionPane.WARNING_MESSAGE);
                }else if (!Pattern.matches(regex, email)) {
                    JOptionPane.showMessageDialog(null,"警告！邮箱的格式错误，请输入正确的邮箱。","警告",JOptionPane.WARNING_MESSAGE);
                }
                else{
                    emailVerficationService.sendVerificationCode(captchaField.getText());
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

                            if (message.getType().equals("EmailVerificationCodeStatus")) {

                                var status = emailVerficationService.handleWebSocketResponse(message);
                                switch (status.getStatus()) {
                                    case SUCCESS:
                                        JOptionPane.showMessageDialog(null, "验证修改成功");
                                        new GetCaptcha(client, userNameField.getText(), emailField.getText(), captchaField.getText());
                                        removeReceivedWebSocketMessageEventListener(this);
                                        RetrievePassword.this.dispose();
                                        break;
                                    case EMAILED:
                                        JOptionPane.showMessageDialog(null, "已发送到你的邮箱");
                                        break;
                                    case FAILED:
                                        JOptionPane.showMessageDialog(null, "验证码发送失败！" + status.getAdditionalInfo());
                                        break;
                                    case TIMEOUT:
                                        JOptionPane.showMessageDialog(null, "验证码已过期。");
                                        break;
                                    case INVALID:
                                        JOptionPane.showMessageDialog(null, "验证码错误。");
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
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
        });

        JButton Returnbutton=new JButton("<返回");
        Returnbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RetrievePassword.this.dispose();
                new LoginGUI(client);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        add(Returnbutton, gbc);

        }
}
