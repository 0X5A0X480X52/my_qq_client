# WebSocket Client 实现

该项目实现了一个简单的 WebSocket 客户端，能够连接到 WebSocket 服务器，发送和接收消息，并处理各种 WebSocket 事件。

## 功能

- 连接到 WebSocket 服务器
- 发送消息到服务器
- 接收来自服务器的消息
- 处理连接建立、关闭和错误事件
- 使用事件队列机制处理接收到的 WebSocket 消息

## 文件结构

```sh
webSocketClient/ 
    ├── WebSocketClient.java # 实现 WebSocket 客户端的主要功能，包括连接、发送和接收消息。
    ├── WebSocketReceiver.java # 定义接收 WebSocket 消息的接口。
    ├── receivedWebSocketMessage/
    │ ├── ReceivedWebSocketMessageEvent.java # 处理接收到的 WebSocket 消息事件的类。
    │ ├── ReceivedWebSocketMessageEventListener.java # 定义处理接收到的 WebSocket 消息事件的方法的接口。
    │ └── ReceivedWebSocketMessageEventQueue.java # 用于处理接收到的 WebSocket 消息事件的事件队列类。
    └── README.md # 项目说明文件
```

## 各个文件的功能

- `WebSocketClient.java`：实现 WebSocket 客户端的主要功能，包括连接、发送和接收消息，处理连接建立、关闭和错误事件。
- `WebSocketReceiver.java`：定义接收 WebSocket 消息的接口，提供添加事件监听器的方法。
- `ReceivedWebSocketMessageEvent.java`：处理接收到的 WebSocket 消息事件的类，封装接收到的消息并将事件发布到系统事件队列。
- `ReceivedWebSocketMessageEventListener.java`：定义处理接收到的 WebSocket 消息事件的方法的接口，继承自 `ActionListener`。
- `ReceivedWebSocketMessageEventQueue.java`：用于处理接收到的 WebSocket 消息事件的事件队列类，继承自 `EventQueue`，并实现事件分发和监听器管理。

## 使用方法

1. 启动 WebSocket 服务器。
2. 程序的类中通过 `WebSocketClient client = new WebSocketClient(URI)` 方法实例化一个客户端 `client`。
3. **发送请求**：通过客户端 `client` 实例向服务器发送请求。
4. **接收请求**：接收服务器发回的请求时，事件队列应是 `ReceivedWebSocketMessageEventQueue` 的实例，即必须先实例化一个客户端 `client` 对象。接收服务器发回的请求的实例实现 `WebSocketReceiver` 接口，通过 `public void addReceivedWebSocketMessageEventListener( ReceivedWebSocketMessageEventListener listener )` 方法添加一个用于处理服务器发回的请求的监听器 `listener`.
