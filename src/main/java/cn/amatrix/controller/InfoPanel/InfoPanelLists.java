package cn.amatrix.controller.InfoPanel;

import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import cn.amatrix.controller.InfoPanel.group.GroupBriefInfoPanel;
import cn.amatrix.controller.InfoPanel.group.GroupDetailedInfoPanel;
import cn.amatrix.controller.InfoPanel.user.UserBriefInfoPanel;
import cn.amatrix.controller.InfoPanel.user.UserDetailedInfoPanel;
import cn.amatrix.model.groups.Group;
import cn.amatrix.model.groups.GroupMember;
import cn.amatrix.model.users.Friend;
import cn.amatrix.model.users.User;
import cn.amatrix.service.groups.GroupService;
import cn.amatrix.service.users.UserService;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;

public class InfoPanelLists extends JPanel {

    private UserService userService = new UserService();
    private GroupService groupService = new GroupService();
    private User currentUser;
    private List<Friend> FriendList;
    private List<GroupMember> GroupMemberList;

    public InfoPanelLists( User currentUser) {
        this.currentUser = currentUser;
        setPreferredSize(new Dimension(400, 0));
        setLayout(new BorderLayout()); // 使用 BorderLayout
        initFriendList();
        initGroupMemberList();
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
            InfoPanel infoPanel = new UserBriefInfoPanel(this, user, currentUser);
            infoPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    friendActionListener(user);
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
            InfoPanel infoPanel = new GroupBriefInfoPanel(this, group, currentUser);
            infoPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    groupActionListener(group);
                }
            });
            Box box = Box.createHorizontalBox();
            box.add(infoPanel);
            System.out.println(infoPanel.getPreferredSize());
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

    private void friendActionListener(User user) {
        JFrame frame = new JFrame();
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.add(new UserDetailedInfoPanel(user, currentUser));
        frame.setVisible(true);
    }

    private void groupActionListener(Group group) {
        JFrame frame = new JFrame();
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.add(new GroupDetailedInfoPanel(group, currentUser));
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        UserService userService = new UserService();
        User user = userService.getUserById(1);
        
        JFrame frame = new JFrame();
        frame.setSize(400, 300);
        InfoPanelLists infoPanelLists = new InfoPanelLists( user );
        frame.add(infoPanelLists);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
