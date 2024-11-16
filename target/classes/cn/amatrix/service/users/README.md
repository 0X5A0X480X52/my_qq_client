# UserService 类

`UserService` 类提供对用户信息和消息的管理功能。它使用 `UserDAO` 和 `PrivateMessageDAO` 类与数据库进行交互。

## 提供的服务与接口

### 用户管理

- `getUserById(int userId)`: 根据用户ID获取用户信息。
- `getUserByUsername(String username)`: 根据用户名获取用户信息。
- `getUserByEmail(String email)`: 根据用户邮箱获取用户信息。
- `addUser(User user)`: 添加新用户。
- `updateUser(User user)`: 更新用户信息。
- `deleteUser(int userId)`: 根据用户ID删除用户。
- `getAllUsers()`: 获取所有用户信息。

### 私信管理

- `getPrivateMessageById(int messageId)`: 根据消息ID获取私信。
- `addPrivateMessage(PrivateMessage message)`: 添加私信。
- `updatePrivateMessage(PrivateMessage message)`: 更新私信。
- `deletePrivateMessage(int messageId)`: 根据消息ID删除私信。
- `getAllPrivateMessages()`: 获取所有私信。
- `getPrivateMessagesBySenderAndReceiver(int senderId, int receiverId)`: 根据发送者和接收者ID获取私信。
- `getPrivateMessagesBySender(int senderId)`: 根据发送者ID获取私信。
- `getPrivateMessagesByReceiver(int receiverId)`: 根据接收者ID获取私信。
