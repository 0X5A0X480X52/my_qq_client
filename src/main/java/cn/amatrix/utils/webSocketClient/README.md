# WebSocket Client 实现

`webSocketClient` 包实现了一个简单的 WebSocket 客户端，能够连接到 WebSocket 服务器，发送和接收消息，并处理各种 WebSocket 事件。其主要功能包括连接到 WebSocket 服务器、发送消息到服务器、接收来自服务器的消息、处理连接建立、关闭和错误事件，并使用事件队列机制处理接收到的 WebSocket 消息。

在实现思路上，`WebSocketClient` 类是核心类，负责管理 WebSocket 连接和消息传递。它通过注解 `@ClientEndpoint` 标识为 WebSocket 客户端，并实现了连接建立、消息接收、连接关闭和错误处理的回调方法。`WebSocketClient` 类还提供了发送消息和主动关闭连接的方法。

为了处理接收到的 WebSocket 消息，定义了 `ReceivedWebSocketMessageEvent` 类，该类继承自 `ActionEvent`，封装了接收到的消息，并将事件发布到系统事件队列。`ReceivedWebSocketMessageEventQueue` 类继承自 `EventQueue`，用于处理接收到的 WebSocket 消息事件。它重写了 `dispatchEvent` 方法，在接收到 `ReceivedWebSocketMessageEvent` 时，调用监听器的 `actionPerformed` 方法处理消息。

`WebSocketReceiver` 接口定义了添加和移除接收到 WebSocket 消息的事件监听器的方法。实现该接口的类可以通过 `addReceivedWebSocketMessageEventListener` 和 `removeReceivedWebSocketMessageEventListener` 方法管理监听器，并在接收到消息时触发相应的事件。

`ReceivedWebSocketMessageEventListener` 接口继承自 `ActionListener`，定义了处理接收到的 WebSocket 消息事件的方法。实现该接口的类需要实现 `actionPerformed` 方法，以处理接收到的消息事件。

通过这些类和接口的协作，`webSocketClient` 包实现了一个功能完备的 WebSocket 客户端，能够处理 WebSocket 连接的各个方面，并通过事件机制处理接收到的消息。

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
