package cn.amatrix.controller.Chat;

import cn.amatrix.model.users.User;
import cn.amatrix.utils.webSocketClient.WebSocketClient;

import javax.swing.*;
import java.awt.*;

public class QQ extends JPanel {
    private InfoPanelList infoPanelList;  // 好友聊天面板
    private FunctionPanel functionPanel;  // 功能面板
    private User currentUser;
    private JPanel chatPanel;
    private JSplitPane splitPane;

    public QQ(WebSocketClient client, User currentUser) {
        this.currentUser = currentUser;
        setLayout(new BorderLayout());

        chatPanel = new JPanel(new BorderLayout());
        chatPanel.setPreferredSize(new Dimension(500, 600));
        // 创建左侧功能面板
        functionPanel = new FunctionPanel(this.currentUser, chatPanel);
        // 创建中间的列表面板
        infoPanelList = new InfoPanelList(client, this.currentUser, this, chatPanel);

        // 将功能面板和列表面板放在一个 JSplitPane 中
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, infoPanelList, chatPanel);
        splitPane.setDividerLocation(200); // 设置分割线位置

        add(functionPanel, BorderLayout.WEST);
        add(splitPane, BorderLayout.CENTER);
    }

    public void updatePanel() {
        removeAll();
        add(functionPanel, BorderLayout.WEST);
        add(splitPane, BorderLayout.CENTER);

        revalidate();
        repaint();
    }
}


