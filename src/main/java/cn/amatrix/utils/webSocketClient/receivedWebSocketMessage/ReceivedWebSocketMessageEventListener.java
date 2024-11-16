package cn.amatrix.utils.webSocketClient.receivedWebSocketMessage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ReceivedWebSocketMessageEventListener 接口定义了处理接收到的 WebSocket 消息事件的方法。
 */
public interface ReceivedWebSocketMessageEventListener extends ActionListener {

    /**
     * 处理接收到的 WebSocket 消息事件。
     *
     * @param e 接收到的 WebSocket 消息事件
     */
    @Override
    public void actionPerformed(ActionEvent e);

}