# HttpConnector

`HttpConnector` 类用于发送 HTTP 请求到指定的服务器。它使用 Java 的 `HttpClient` 库来构建和发送请求，并处理响应。

## 功能

- 构造 HTTP 请求，支持 JSON 格式的请求体。
- 发送 POST 请求到指定的 URL。
- 处理服务器返回的响应。

## 使用方法

1. 创建 `HttpConnector` 实例。
2. 调用 `sendRequest` 方法，传入子路径、请求类型和参数。
