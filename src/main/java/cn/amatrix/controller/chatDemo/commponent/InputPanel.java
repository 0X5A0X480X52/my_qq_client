package cn.amatrix.controller.chatDemo.commponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class InputPanel extends JPanel {
    private JTextArea inputField; // 修改为 JTextArea
    private JButton sendButton;

    public InputPanel(ActionListener sendAction) {
        inputField = new JTextArea(); // 修改为 JTextArea
        inputField.setLineWrap(true); // 自动换行
        inputField.setWrapStyleWord(true); // 断行不断字
        inputField.setAlignmentX(Component.LEFT_ALIGNMENT); // 文本靠左对齐
        inputField.setAlignmentY(Component.TOP_ALIGNMENT); // 文本靠上对齐
        
        sendButton = new JButton("Send");

        setLayout(new BorderLayout());
        add(new JScrollPane(inputField), BorderLayout.CENTER); // 使用 JScrollPane 包装 JTextArea
        add(sendButton, BorderLayout.SOUTH);

        sendButton.setPreferredSize(new Dimension(40, 30)); // 固定按钮大小
        sendButton.setMaximumSize(sendButton.getPreferredSize()); // 使按钮大小固定
        sendButton.setAlignmentX(Component.RIGHT_ALIGNMENT); // 按钮靠右对齐
        sendButton.addActionListener(sendAction);
    }

    public String getInputText() {
        return inputField.getText();
    }

    public void clearInputField() {
        inputField.setText("");
    }
}
