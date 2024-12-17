package cn.amatrix.utils.webSocketClient.receivedWebSocketMessage;

import java.awt.AWTEvent;
import java.awt.AWTEventMulticaster;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

/**
 * ReceivedWebSocketMessageEventQueue 类用于处理接收到的 WebSocket 消息事件。
 */
public class ReceivedWebSocketMessageEventQueue extends EventQueue {

    ActionListener listener = new ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            System.out.println("ReceivedWebSocketMessageEventListener : " + e);
        }
    };

    /**
     * 构造方法，初始化事件队列并将其推送到系统事件队列。
     */
    public ReceivedWebSocketMessageEventQueue() {
        super();
        Toolkit.getDefaultToolkit().getSystemEventQueue().push(this);
    }

    /**
     * 分发事件的方法。
     *
     * @param event 要分发的事件
     */
    @Override
    protected void dispatchEvent(AWTEvent event) {
        if (event instanceof ReceivedWebSocketMessageEvent) {
            ReceivedWebSocketMessageEvent gettedEvent = (ReceivedWebSocketMessageEvent) event;
            System.out.println("ReceivedWebSocketMessageEvent : " + gettedEvent.getMessage());
            this.listener.actionPerformed(gettedEvent);
        } else {
            super.dispatchEvent(event); // 处理其他事件
        }
    }

    /**
     * 添加接收到 WebSocket 消息的事件监听器。
     *
     * @param listener 接收到 WebSocket 消息的事件监听器
     */
    public void addReceivedWebSocketMessageEventListener( ReceivedWebSocketMessageEventListener listener) {
        if (listener == null) {
            return;
        }
        this.listener = AWTEventMulticaster.add(this.listener, listener);
    }

    /**
     * 添加接收到 WebSocket 消息的事件监听器。
     *
     * @param listener 接收到 WebSocket 消息的事件监听器
     */
    public void removeReceivedWebSocketMessageEventListener( ReceivedWebSocketMessageEventListener listener) {
        if (listener == null) {
            return;
        }
        this.listener = AWTEventMulticaster.remove(this.listener, listener);
    }
}