package cn.amatrix.utils.webSocketClient;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.management.RuntimeErrorException;

import cn.amatrix.utils.webSocketClient.receivedWebSocketMessage.ReceivedWebSocketMessageEventListener;
import cn.amatrix.utils.webSocketClient.receivedWebSocketMessage.ReceivedWebSocketMessageEventQueue;

public class WebClientClass {

    private static int instanceCount = 0;
    private int id;
    ReceivedWebSocketMessageEventQueue eventQueue;
    ReceivedWebSocketMessageEventListener listener;

    public WebClientClass () throws RuntimeErrorException {
        EventQueue eventQueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
        if ( eventQueue instanceof ReceivedWebSocketMessageEventQueue ) {
            this.eventQueue = (ReceivedWebSocketMessageEventQueue)eventQueue;
        } else
            throw new RuntimeErrorException(null, " EventQueue is not instance of ReceivedWebSocketMessageEventQueue ");

        this.id = instanceCount++;
    }

    public void addReceivedWebSocketMessageEventListener( ReceivedWebSocketMessageEventListener listener ) {
        // this.listener = listener;
        eventQueue.addReceivedWebSocketMessageEventListener( listener );
    }

    public int getId() {
        return id;
    }
}