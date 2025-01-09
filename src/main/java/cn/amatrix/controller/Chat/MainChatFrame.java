package cn.amatrix.controller.Chat;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import cn.amatrix.model.users.User;
import cn.amatrix.service.users.UserService;
import cn.amatrix.utils.webSocketClient.WebSocketClient;

public class MainChatFrame extends JFrame {
    public MainChatFrame(WebSocketClient webSocketClient, String email) {
        setTitle("OO —— 比 QQ 少亿点点的功能的实时通讯工具");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(400,300);
        setSize(1000, 600);
        setMinimumSize(new Dimension(700, 500)); // 设置最小窗口大小
        setLayout(new BorderLayout());

        // 创建菜单栏
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("帮助");
        JMenuItem aboutMenuItem = new JMenuItem("关于");
        aboutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainChatFrame.this, "作者: Amatrix\ndyf,zyh,ncx", "关于", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        menu.add(aboutMenuItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        UserService userService = new UserService();
        User user = userService.getUserByEmail(email);
        QQ contentPanel = new QQ(webSocketClient, user);

        add(contentPanel, BorderLayout.CENTER);
        setVisible(true);
    }
}
