package cn.amatrix.utils.webSocketClient.receivedWebSocketMessage;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import cn.amatrix.model.message.Message;

/**
 * 处理接收到的 WebSocket 消息事件的类。
 */
public class ReceivedWebSocketMessageEvent extends ActionEvent {
    Message message;

    /**
     * 构造方法，初始化事件源和消息。
     *
     * @param source 事件源对象
     * @param message 接收到的消息
     */
    public ReceivedWebSocketMessageEvent(Object source, String message) {
        super(source, RESERVED_ID_MAX, message);
        this.message = Message.fromJson(message);

        // 将事件发布到系统事件队列
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(this);
    }

    /**
     * 获取接收到的消息。
     *
     * @return 接收到的消息
     */
    public Message getMessage() {
        return message;
    }
}