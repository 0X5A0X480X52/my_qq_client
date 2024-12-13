package cn.amatrix.controller.InfoPanel;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import cn.amatrix.controller.InfoPanel.user.UserBriefInfoPanel;
import cn.amatrix.controller.InfoPanel.user.UserDetailedInfoPanel;
import cn.amatrix.model.users.Friend;
import cn.amatrix.model.users.User;
import cn.amatrix.service.users.UserService;
import javax.swing.JScrollPane;

public class InfoPanelLists extends JPanel {

    private UserService userService = new UserService();
    private User currentUser;
    private List<Friend> FriendList;

    public InfoPanelLists(User currentUser) {
        this.currentUser = currentUser;
        initFriendList();
        updatePanel();
    }

    private void initFriendList() {
        int userId = currentUser.getUser_id();
        this.FriendList = userService.getFriendsByUserId(userId);
    }

    private void updatePanel() {
        initFriendList();
        removeAll();

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane);

        for (Friend friend : FriendList) {
            User user = userService.getUserById(friend.getFriendId());
            InfoPanel infoPanel = new UserBriefInfoPanel(this, user, currentUser);
            infoPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    friendActionListener(user);
                }
            });
            scrollPane.add(infoPanel);
        }
        revalidate();
        repaint();
    }

    private void friendActionListener(User user) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.add(new UserDetailedInfoPanel(user, currentUser));
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        UserService userService = new UserService();
        User user = userService.getUserById(1);
        InfoPanelLists infoPanelLists = new InfoPanelLists(user);
        JFrame frame = new JFrame();
        frame.add(infoPanelLists);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
