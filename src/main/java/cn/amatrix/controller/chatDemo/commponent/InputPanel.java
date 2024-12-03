package cn.amatrix.controller.chatDemo.commponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class InputPanel extends JPanel {
    private JTextField inputField;
    private JButton sendButton;

    public InputPanel(ActionListener sendAction) {
        inputField = new JTextField();
        sendButton = new JButton("Send");

        setLayout(new BorderLayout());
        add(inputField, BorderLayout.CENTER);
        add(sendButton, BorderLayout.SOUTH);

        sendButton.setPreferredSize(new Dimension(40, 30)); // 固定按钮大小
        sendButton.setMaximumSize( sendButton.getPreferredSize()); // 使按钮大小固定
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
