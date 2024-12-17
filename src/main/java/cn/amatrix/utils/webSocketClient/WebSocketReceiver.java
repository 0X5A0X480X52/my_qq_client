package cn.amatrix.utils.webSocketClient;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.management.RuntimeErrorException;

import cn.amatrix.utils.webSocketClient.receivedWebSocketMessage.ReceivedWebSocketMessageEventListener;
import cn.amatrix.utils.webSocketClient.receivedWebSocketMessage.ReceivedWebSocketMessageEventQueue;

/**
 * WebSocketReceiver 接口定义了接收 WebSocket 消息的基本方法。
 */
public interface WebSocketReceiver {

    /**
     * 添加一个接收到 WebSocket 消息的事件监听器。
     *
     * @param listener 接收到 WebSocket 消息的事件监听器
     * @throws RuntimeErrorException 如果系统事件队列不是 ReceivedWebSocketMessageEventQueue 的实例
     */
    default public void addReceivedWebSocketMessageEventListener( ReceivedWebSocketMessageEventListener listener ) throws RuntimeErrorException {

        EventQueue eventQueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
        if ( eventQueue instanceof ReceivedWebSocketMessageEventQueue ) {
            ReceivedWebSocketMessageEventQueue ReceivedWebSocketMessageEvent = (ReceivedWebSocketMessageEventQueue)eventQueue;
            ReceivedWebSocketMessageEvent.addReceivedWebSocketMessageEventListener( listener );
        } else
            throw new RuntimeErrorException(null, " EventQueue is not instance of ReceivedWebSocketMessageEventQueue ");
        
    }
    /**
     * 移除一个接收到 WebSocket 消息的事件监听器。
     * 
     * @param listener 接收到 WebSocket 消息的事件监听器
     * @throws RuntimeErrorException 如果系统事件队列不是 ReceivedWebSocketMessageEventQueue 的实例
     */
    default public void removeReceivedWebSocketMessageEventListener( ReceivedWebSocketMessageEventListener listener ) throws RuntimeErrorException {

        EventQueue eventQueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
        if ( eventQueue instanceof ReceivedWebSocketMessageEventQueue ) {
            ReceivedWebSocketMessageEventQueue ReceivedWebSocketMessageEvent = (ReceivedWebSocketMessageEventQueue)eventQueue;
            ReceivedWebSocketMessageEvent.removeReceivedWebSocketMessageEventListener( listener );
        } else
            throw new RuntimeErrorException(null, " EventQueue is not instance of ReceivedWebSocketMessageEventQueue ");
        
    }
}