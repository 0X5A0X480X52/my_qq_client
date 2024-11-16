package cn.amatrix.utils.webSocketClient;
import java.awt.Toolkit;

import javax.swing.*;

import cn.amatrix.utils.webSocketClient.receivedWebSocketMessage.ReceivedWebSocketMessageEvent;
import cn.amatrix.utils.webSocketClient.receivedWebSocketMessage.ReceivedWebSocketMessageEventQueue;

public class ReceivedWebSocketMessageEventExample {
    public static void main(String[] args) {
        ReceivedWebSocketMessageEventQueue customQueue = new ReceivedWebSocketMessageEventQueue();
        Toolkit.getDefaultToolkit().getSystemEventQueue().push(customQueue); // 设置自定义事件队列

        JFrame frame = new JFrame("Custom Event Queue Example");
        JButton button = new JButton("Trigger Custom Event");

        button.addActionListener(e -> {
            new ReceivedWebSocketMessageEvent(button, "hello");
        });

        frame.add(button);
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}