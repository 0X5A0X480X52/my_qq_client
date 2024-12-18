package cn.amatrix.controller.Chat;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import cn.amatrix.controller.InfoPanel.InfoPanel;
import cn.amatrix.controller.InfoPanel.group.GroupInfoPanel;
import cn.amatrix.controller.InfoPanel.user.UserInfoPanel;
import cn.amatrix.controller.chatDemo.GroupChatPanel;
import cn.amatrix.controller.chatDemo.PrivateChatPanel;
import cn.amatrix.model.groups.Group;
import cn.amatrix.model.groups.GroupMember;
import cn.amatrix.model.users.Friend;
import cn.amatrix.model.users.User;
import cn.amatrix.service.groups.GroupService;
import cn.amatrix.service.users.UserService;
import cn.amatrix.utils.webSocketClient.WebSocketClient;

public class InfoPanelList extends JPanel {

    private UserService userService = new UserService();
    private GroupService groupService = new GroupService();
    private User currentUser;
    private List<Friend> FriendList;
    private List<GroupMember> GroupMemberList;
    private WebSocketClient webSocketClient;
    private JPanel chatPanel;

    private QQ contentPanel;

    public InfoPanelList( WebSocketClient client, User currentUser, QQ contentPanel, JPanel chatPanel) {

        this.webSocketClient = client;

        this.contentPanel = contentPanel;
        this.currentUser = currentUser;
        this.chatPanel = chatPanel;

        setPreferredSize(new Dimension(200, 0));
        setLayout(new BorderLayout()); // 使用 BorderLayout
        initFriendList();
        initGroupMemberList();

        JButton refreshButton = new JButton("刷新");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePanel();
            }
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(refreshButton);
        add(bottomPanel, BorderLayout.SOUTH);

        updatePanel();
    }

    private void initFriendList() {
        int userId = currentUser.getUser_id();
        this.FriendList = userService.getFriendsByUserId(userId);
    }

    private void initGroupMemberList() {
        int userId = currentUser.getUser_id();
        this.GroupMemberList = groupService.getGroupMembersByUserId(userId);
    }

    private void updatePanel() {
        initFriendList();
        initGroupMemberList();
        removeAll();

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        for (Friend friend : FriendList) {
            User user = userService.getUserById(friend.getFriendId());
            InfoPanel infoPanel = new UserInfoPanel(this, user, currentUser, null);
            infoPanel.setButton(new JPanel());
            infoPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    friendActionListener(chatPanel,user);
                }
            });
            Box box = Box.createHorizontalBox();
            box.add(infoPanel);
            infoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, infoPanel.getPreferredSize().height + 20)); // 设置最大尺寸
            listPanel.add(box);
            listPanel.setPreferredSize(new Dimension(0, listPanel.getPreferredSize().height + infoPanel.getPreferredSize().height));
        }

        for (GroupMember groupMember : GroupMemberList) {
            Group group = groupService.getGroupById(groupMember.getGroupId());
            InfoPanel infoPanel = new GroupInfoPanel(this, group, currentUser, null);
            infoPanel.setButton(new JPanel());
            infoPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    groupActionListener(chatPanel,group);
                }
            });
            Box box = Box.createHorizontalBox();
            box.add(infoPanel);
            infoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, infoPanel.getPreferredSize().height + 20)); // 设置最大尺寸
            listPanel.add(box);
            listPanel.setPreferredSize(new Dimension(0, listPanel.getPreferredSize().height + infoPanel.getPreferredSize().height));

        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private void friendActionListener(JPanel chatPanel, User user) {
        chatPanel.removeAll();
        PrivateChatPanel privateChatPanel = new PrivateChatPanel(webSocketClient,currentUser,user);
        chatPanel.add(privateChatPanel);
        chatPanel.revalidate();
        chatPanel.repaint();

        updatePanel();
    }

    private void groupActionListener(JPanel chatPanel,Group group) {
        SwingUtilities.invokeLater(() -> {
            try {
                GroupChatPanel groupChatPanel = new GroupChatPanel(webSocketClient, currentUser, group);
                chatPanel.removeAll();
                chatPanel.add(groupChatPanel, BorderLayout.CENTER); // 添加新的群聊面板
                chatPanel.revalidate(); // 刷新布局
                chatPanel.repaint(); // 重新绘制面板
            } catch (Exception e) {
                e.printStackTrace(); // 打印异常
            }
        });

        contentPanel.updatePanel(); // 刷新父容器

        updatePanel();
    }

    public static void main(String[] args) {
        UserService userService = new UserService();
        User user = userService.getUserById(1);

        JFrame frame = new JFrame();
        frame.setSize(400, 300);
        cn.amatrix.controller.InfoPanel.InfoPanelLists infoPanelLists = new cn.amatrix.controller.InfoPanel.InfoPanelLists( user );
        frame.add(infoPanelLists);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
