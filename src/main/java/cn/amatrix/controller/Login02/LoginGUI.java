package cn.amatrix.controller.Login02;

import javax.imageio.ImageIO;
import javax.swing.*;

import cn.amatrix.controller.Login02.RetrievePassword.RetrievePassword;
import cn.amatrix.controller.Login02.SignUp.SignUpExample;
import cn.amatrix.controller.Chat.QQ;
import cn.amatrix.model.message.Message;
import cn.amatrix.model.users.User;
import cn.amatrix.utils.base64.ImageManager;
import cn.amatrix.utils.webSocketClient.WebSocketClient;
import cn.amatrix.utils.webSocketClient.WebSocketReceiver;
import cn.amatrix.utils.webSocketClient.receivedWebSocketMessage.ReceivedWebSocketMessageEvent;
import cn.amatrix.utils.webSocketClient.receivedWebSocketMessage.ReceivedWebSocketMessageEventListener;

import cn.amatrix.service.signIn.SignInService;
import cn.amatrix.service.users.UserService;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class LoginGUI extends JFrame implements WebSocketReceiver {

    final int WIDTH = 400;
    final int HEIGHT = 600;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private WebSocketClient webSocketClient;

    public LoginGUI(WebSocketClient webSocketClient) {
        this.webSocketClient = webSocketClient;
        // 设置窗口标题和大小
        setTitle("登录");
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 居中显示


        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWeights = new double[]{1,9,1};
        gridBagLayout.rowWeights = new double[]{0.1875,0.1875,0.0625,0.46875,0.09375,};
        setLayout(gridBagLayout);

        JPanel panel = new JPanel();
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.gridx = 1;
        gbc_panel.gridy = 1;
        add(panel, gbc_panel);
        
        JPanel panel_1 = new JPanel();
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.fill = GridBagConstraints.BOTH;
        gbc_panel_1.gridx = 1;
        gbc_panel_1.gridy = 3;
        add(panel_1, gbc_panel_1);

        JPanel panel_2 = new JPanel();
        GridBagConstraints gbc_panel_2 = new GridBagConstraints();
        gbc_panel_2.fill = GridBagConstraints.BOTH;
        gbc_panel_2.gridx = 2;
        gbc_panel_2.gridy = 4;
        add(panel_2, gbc_panel_2);

        JPanel panel_3 = new JPanel();
        GridBagConstraints gbc_panel_3 = new GridBagConstraints();
        gbc_panel_3.fill = GridBagConstraints.BOTH;
        gbc_panel_3.gridx = 0;
        gbc_panel_3.gridy = 4;
        add(panel_3, gbc_panel_3);

        panel.setLayout(new BorderLayout());
        // 创建圆形的 JLabel，并设置图像
        SignInService sign =new SignInService(this.webSocketClient);
        
        CircularImageLabel circularLabel = new CircularImageLabel("src\\main\\resources\\icons\\defaultAvatar01.jpg");
        circularLabel.setPreferredSize(new Dimension(10, 10));
        panel.add(circularLabel, BorderLayout.CENTER); // 将圆形 JLabel 添加到面板中间

        JPanel panel_4 = new JPanel();
        int width = WIDTH / 11 * 9;
        int height = (int)(HEIGHT * 0.1875);
        int width_w = (width - height) / 2;

        panel_4.setPreferredSize(new Dimension(width_w,10));
        panel.add(panel_4, BorderLayout.WEST);

        GridBagLayout gb_panel_1 = new GridBagLayout();
        panel_1.setLayout(gb_panel_1);
        gb_panel_1.columnWeights = new double[]{ 4,3};
        gb_panel_1.rowWeights = new double[]{ 1, 0.6, 1, 0.6, 0.6, 0.8, 0.6, 0.2};

        usernameField = new JTextField("请输入用户ID");
        usernameField.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        usernameField.setHorizontalAlignment(SwingConstants.CENTER);

        // 添加焦点监听器来管理提示文本
        usernameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // 当获得焦点时，清空提示文本，并设置隐藏字符
                if (String.valueOf(usernameField.getText()).equals("请输入用户ID")) {
                    usernameField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // 当失去焦点时，如果没有输入内容，则显示提示文本
                if (String.valueOf(usernameField.getText()).isEmpty()) {
                    usernameField.setText("请输入用户ID");
                }
            }
        });
        
        GridBagConstraints gbc_usernameField = new GridBagConstraints();
        gbc_usernameField.fill = GridBagConstraints.BOTH;
        gbc_usernameField.gridx = 0;
        gbc_usernameField.gridy = 0;
        gbc_usernameField.gridwidth = 2;
        panel_1.add(usernameField, gbc_usernameField);

        passwordField = new JPasswordField("请输入密码");
        passwordField.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        passwordField.setHorizontalAlignment(SwingConstants.CENTER);
        passwordField.setEchoChar((char) 0); // 显示提示文本
        // 添加焦点监听器来管理提示文本
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // 当获得焦点时，清空提示文本，并设置隐藏字符
                if (String.valueOf(passwordField.getPassword()).equals("请输入密码")) {
                    passwordField.setText("");
                    passwordField.setEchoChar('•'); // 设置隐藏字符
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // 当失去焦点时，如果没有输入内容，则显示提示文本
                if (String.valueOf(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText("请输入密码");
                    passwordField.setEchoChar((char) 0); // 显示提示文本
                }
            }
        });

        GridBagConstraints gbc_passwordField = new GridBagConstraints();
        gbc_passwordField.fill = GridBagConstraints.BOTH;
        gbc_passwordField.gridx = 0;
        gbc_passwordField.gridy = 2;
        gbc_passwordField.gridwidth = 2;
        panel_1.add(passwordField, gbc_passwordField);

        // 创建登录按钮并添加事件监听
        JButton loginButton = new JButton("登录");
        loginButton.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.equals("请输入用户ID")||username.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "未输入账号，请输入账号");
                } else if (password.isEmpty()||password.equals("请输入密码")) {
                    JOptionPane.showMessageDialog(null, "未输入密码，请输入密码");
                } else {
                    sign.submitSignInInformation(null, username, password);
                }
            }
        });
        GridBagConstraints gbc_loginButton = new GridBagConstraints();
        gbc_loginButton.fill = GridBagConstraints.BOTH;
        gbc_loginButton.gridx = 0;
        gbc_loginButton.gridy = 5;
        gbc_loginButton.gridwidth = 2;
        panel_1.add(loginButton, gbc_loginButton);        


        JPanel panel_5 = new JPanel();
        GridBagConstraints gbc_panel_5 = new GridBagConstraints();
        gbc_panel_5.fill = GridBagConstraints.BOTH;
        gbc_panel_5.gridx = 0;
        gbc_panel_5.gridy = 6;
        gbc_panel_5.gridwidth = 2;
        panel_1.add(panel_5, gbc_panel_5);

        JButton registerButton = new JButton("注册");
        registerButton.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SignUpExample();
                LoginGUI.this.dispose();
            }
        });
        GridBagConstraints gbc_registerButton = new GridBagConstraints();
        gbc_registerButton.fill = GridBagConstraints.BOTH;
        gbc_registerButton.gridx = 0;
        gbc_registerButton.gridy = 7;
        gbc_registerButton.gridwidth = 1;
        panel_1.add(registerButton, gbc_registerButton);

        // 创建修改密码按钮并添加事件监听
        JButton changePasswordButton = new JButton("修改密码");
        changePasswordButton.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new RetrievePassword();
                LoginGUI.this.dispose();
            }
        });
        GridBagConstraints gbc_changePasswordButton = new GridBagConstraints();
        gbc_changePasswordButton.fill = GridBagConstraints.BOTH;
        gbc_changePasswordButton.gridx = 1;
        gbc_changePasswordButton.gridy = 7;
        gbc_changePasswordButton.gridwidth = 1;
        panel_1.add(changePasswordButton, gbc_changePasswordButton);

        setVisible(true);

        addReceivedWebSocketMessageEventListener(new ReceivedWebSocketMessageEventListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (e instanceof ReceivedWebSocketMessageEvent) {
                        ReceivedWebSocketMessageEvent event = (ReceivedWebSocketMessageEvent)e;
                        Message message = event.getMessage();

                        if (message.getType().equals("SignInCodeStatus")) {

                            var status = sign.handleWebSocketResponse(message);

                            switch(status.getStatus()){
                                case SUCCESS:
                                    JOptionPane.showMessageDialog(null,"登录成功");
                                    
                                    // 创建并显示主窗口
                                    SwingUtilities.invokeLater(() -> {
                                        JFrame frame = new JFrame("QQ");
                                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                        frame.setSize(1000, 600);
                                        frame.setMinimumSize(new Dimension(700, 500)); // 设置最小窗口大小
                                        frame.setLayout(new BorderLayout());

                                        UserService userService = new UserService();
                                        User user = userService.getUserById(1);
                                        QQ contentPanel = new QQ(webSocketClient, user);

                                        frame.add(contentPanel, BorderLayout.CENTER);

                                        // 显示窗口
                                        frame.setVisible(true);
                                    });

                                    LoginGUI.this.dispose();
                                    removeReceivedWebSocketMessageEventListener(this);
                                    break;
                                case FAILED:
                                    JOptionPane.showMessageDialog(null,"登录失败！"+status.getAdditionalInfo());
                                    passwordField.setText("");
                                    break;
                                case UNSIGNUP:
                                    JOptionPane.showMessageDialog(null,"登陆失败，账号未注册");
                                    break;
                                case UNKNOW:
                                    JOptionPane.showMessageDialog(null,"未知。");
                                    break;
                            }

                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

        // 自定义 JLabel 类，用于绘制圆形图像
    class CircularImageLabel extends JLabel {
        private BufferedImage image;

        public CircularImageLabel(String imagePath) {
            try {
                // 读取图像
                BufferedImage orgImage = ImageIO.read(new File(imagePath));
                image = ImageManager.cropToCircle(orgImage);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            // 绘制圆形背景
            int size = Math.min(getWidth(), getHeight()); // 获取最小的边长
            g.setClip(0, 0, size, size); // 设置绘图区域为正方形
            g.fillOval(0, 0, size, size); // 绘制圆形

            // 绘制图像
            if (image != null) {
                // 确保图像按比例缩放
                Image scaledImage = image.getScaledInstance(size, size, Image.SCALE_SMOOTH);
                g.drawImage(scaledImage, 0, 0, this); // 绘制图像
            }

        }
    }
}

