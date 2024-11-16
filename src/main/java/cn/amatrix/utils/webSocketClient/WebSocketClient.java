package cn.amatrix.utils.webSocketClient;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import cn.amatrix.utils.webSocketClient.receivedWebSocketMessage.ReceivedWebSocketMessageEvent;
import cn.amatrix.utils.webSocketClient.receivedWebSocketMessage.ReceivedWebSocketMessageEventQueue;

import java.net.URI;
import java.util.Scanner;

/**
 * WebSocketClient 类实现了 WebSocket 客户端的基本功能。
 */
@ClientEndpoint
public class WebSocketClient {

    private Session userSession = null;

    /**
     * 构造方法，初始化 WebSocket 客户端并连接到指定的 URI。
     *
     * @param endpointURI WebSocket 服务器的 URI
     */
    public WebSocketClient(URI endpointURI) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, endpointURI);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new ReceivedWebSocketMessageEventQueue();
    }

    /**
     * 处理连接建立事件。
     *
     * @param session WebSocket 会话
     */
    @OnOpen
    public void onOpen(Session session) {
        this.userSession = session;
        System.out.println("Connected to server");
    }

    /**
     * 处理从服务器接收消息。
     *
     * @param message 从服务器接收的消息
     */
    @OnMessage
    public void onMessage(String message) {
        System.out.println("Received message from server: " + message);
        new ReceivedWebSocketMessageEvent(this, message);
    }

    /**
     * 处理连接关闭事件。
     *
     * @param session WebSocket 会话
     * @param closeReason 关闭原因
     */
    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("Session closed: " + closeReason);
    }

    /**
     * 处理连接错误事件。
     *
     * @param session WebSocket 会话
     * @param throwable 错误信息
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("Error occurred: " + throwable.getMessage());
    }

    /**
     * 发送消息到服务器。
     *
     * @param message 要发送的消息
     */
    public void sendMessage(String message) {
        if (userSession != null) {
            this.userSession.getAsyncRemote().sendText(message);
        }
    }

    /**
     * 主动关闭连接的方法。
     */
    public void closeConnection() {
        if (userSession != null) {
            try {
                userSession.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Client requested to close the connection"));
                System.out.println("Connection closed by client.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 主方法（用于测试），启动 WebSocket 客户端并发送消息。
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        URI uri = URI.create("ws://localhost:1145/demo_webapp/chat");
        WebSocketClient client = new WebSocketClient(uri);
        client.sendMessage("Hello WebSocket");

        Scanner reader = new Scanner(System.in);
        while (reader.hasNextLine()) {
            String message = reader.nextLine();
            if (message.equals("end")) {
                break;
            } else
                client.sendMessage(message);
        }
        reader.close();

        // 主动关闭连接
        client.closeConnection();
    }
}
