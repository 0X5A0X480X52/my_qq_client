# 简易 QQ 聊天系统 - 项目结构简介

## 目录说明

该目录包含与群组相关的模型类，每个类的功能如下：

### Group.java

表示一个群组，包含群组的基本信息，如群组ID、群组名称、头像和创建时间。

- `groupId`: 群组ID
- `groupName`: 群组名称
- `avatar`: 群组头像
- `createdAt`: 群组创建时间

### GroupMessage.java

表示在群组中发送的消息，包含消息ID、群组ID、发送者ID、消息内容和发送时间。提供了消息的序列化和反序列化方法。

- `messageId`: 消息ID
- `groupId`: 群组ID
- `senderId`: 发送者ID
- `message`: 消息内容
- `sentAt`: 发送时间

### GroupMember.java

表示群组中的成员，包含群组ID、用户ID、成员权限和加入时间。提供了成员信息的序列化和反序列化方法。

- `groupId`: 群组ID
- `userId`: 用户ID
- `power`: 成员权限
- `joinedAt`: 加入时间

### GroupJoinRequest.java

表示加入群组的请求，包含请求ID、群组ID、用户ID、请求消息、请求状态和请求时间。提供了请求的序列化和反序列化方法。

- `requestId`: 请求ID
- `groupId`: 群组ID
- `userId`: 用户ID
- `requestMessage`: 请求消息
- `requestStatus`: 请求状态
- `requestedAt`: 请求时间
