# 简易 QQ 聊天系统 - 项目结构简介

## Message 类属性简述

- `receiver` (`MessageEndPoint`): 消息接收者
- `sender` (`MessageEndPoint`): 消息发送者
- `messageId` (`String`): 消息 ID
- `type` (`String`): 消息类型
- `content` (`String`): 消息内容
- `timestamp` (`ZonedDateTime`): 消息时间戳
- `status` (`String`): 消息状态
- `extraFields` (`Map<String, Object>`): 额外字段
- `messageCnt` (`int`): 消息计数器
