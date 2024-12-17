# 简易 QQ 聊天系统 - 项目结构简介

## 用户模块类说明

### User

表示用户的基本信息，包括用户ID、用户名、密码、电子邮件、头像、创建时间、登录状态、最后登录时间和最后登出时间。

- `user_id`: 用户ID
- `username`: 用户名
- `password`: 密码
- `email`: 电子邮件
- `avatar`: 头像
- `created_at`: 创建时间
- `log_status`: 登录状态
- `last_login_at`: 最后登录时间
- `last_logout_at`: 最后登出时间

### PrivateMessage

表示用户之间的私信，包括消息ID、发送者ID、接收者ID、消息内容和发送时间。

- `messageId`: 消息ID
- `senderId`: 发送者ID
- `receiverId`: 接收者ID
- `message`: 消息内容
- `sentAt`: 发送时间

### FriendRequest

表示用户之间的好友请求，包括请求ID、发送者ID、接收者ID、请求消息、请求状态和请求时间。

- `requestId`: 请求ID
- `senderId`: 发送者ID
- `receiverId`: 接收者ID
- `requestMessage`: 请求消息
- `requestStatus`: 请求状态
- `requestedAt`: 请求时间

### Friend

表示用户之间的好友关系，包括用户ID、好友ID和添加时间。

- `userId`: 用户ID
- `friendId`: 好友ID
- `addedAt`: 添加时间
