package cn.amatrix.controller.chatDemo.test;

import javax.swing.*;

import com.formdev.flatlaf.FlatLightLaf;

import cn.amatrix.controller.chatDemo.commponent.MessagePanel;
import cn.amatrix.controller.chatDemo.commponent.InputPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class chatDemo3 extends JFrame {
    private JPanel chatPanel;
    private JScrollPane scrollPane;
    private InputPanel inputPanel;

    public chatDemo3() {

        setTitle("Chat Demo");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setResizeWeight(0.7); // 设置顶部占页面70%
        add(splitPane);

        chatPanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(scrollPane.getViewport().getWidth(), super.getPreferredSize().height);
            }
        };
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(chatPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); // 始终显示垂直滚动条

        // 添加 ComponentListener 以调整 chatPanel 的宽度
        scrollPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                chatPanel.setPreferredSize(new Dimension(scrollPane.getViewport().getWidth(), chatPanel.getPreferredSize().height));
                chatPanel.revalidate();
            }
        });

        inputPanel = new InputPanel(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = inputPanel.getInputText();
                if (!message.isEmpty()) {
                    try {
                        JPanel messagePanel = new MessagePanel(null, message, "message", chatPanel);
                        chatPanel.add(messagePanel);
                        chatPanel.setPreferredSize(new Dimension(chatPanel.getPreferredSize().width, chatPanel.getPreferredSize().height + messagePanel.getPreferredSize().height));
                        // 滚动到最下方
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
                                verticalBar.setValue(verticalBar.getMaximum());
                            }
                        });
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    chatPanel.revalidate();
                    inputPanel.clearInputField();
                }
            }
        });
        
        splitPane.add(scrollPane, JSplitPane.TOP); // 将 scrollPane 添加到 splitPane 的顶部
        splitPane.add(inputPanel, JSplitPane.BOTTOM);
        // add(scrollPane, BorderLayout.CENTER);
        // add(inputPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        try {
            // 使用相对路径加载主题文件
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new chatDemo3().setVisible(true);
            }
        });
    }
}
