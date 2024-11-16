package cn.amatrix.utils.webSocketClient;

import java.awt.event.ActionEvent;
import java.net.URI;
import java.util.Scanner;

import cn.amatrix.model.message.Message;
import cn.amatrix.utils.webSocketClient.receivedWebSocketMessage.ReceivedWebSocketMessageEvent;
import cn.amatrix.utils.webSocketClient.receivedWebSocketMessage.ReceivedWebSocketMessageEventListener;

public class WebClientExample {
    public static void main(String[] args) {

        URI uri = URI.create("ws://localhost:1145/demo_webapp/chat");
        WebSocketClient client = new WebSocketClient(uri);
        client.sendMessage("Hello WebSocket");


        WebClientClass myClass_1 = new WebClientClass();
        myClass_1.addReceivedWebSocketMessageEventListener( new ReceivedWebSocketMessageEventListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println( "ReceivedWebSocketMessageEventListener of " + myClass_1.getId() + ": hello!");
                try {
                    if (e instanceof ReceivedWebSocketMessageEvent) {
                        ReceivedWebSocketMessageEvent event = (ReceivedWebSocketMessageEvent)e;
                        Message message = event.getMessage();
                        System.out.println( "ReceivedWebSocketMessageEvent : " + message);
                        if (message.getType().equals("text")) {
                            String content = (String)message.getContent();
                            System.out.println( "ReceivedWebSocketMessageContent : " + content);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        WebClientClass myClass_2 = new WebClientClass();
        myClass_2.addReceivedWebSocketMessageEventListener( new ReceivedWebSocketMessageEventListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println( "ReceivedWebSocketMessageEventListener of " + myClass_2.getId() + ": hi!");
            }
        });

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