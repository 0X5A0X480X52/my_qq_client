# Model 包

## 文件结构

```sh
    model/ 
    ├── users/  # 用户相关实体类
    │ ├── User.java  # 用户实体类，表示系统中的用户
    │ ├── PrivateMessage.java  # 私信实体类，表示用户之间的私信内容
    │ ├── FriendRequest.java  # 好友请求实体类，表示用户间的好友请求
    │ └── Friend.java  # 好友关系实体类，表示用户之间的好友关系
    ├── groups/  # 群组相关实体类
    │ ├── Group.java  # 群组实体类，表示群组的基本信息
    │ ├── GroupMessage.java  # 群消息实体类，表示群组内的消息内容
    │ ├── GroupJoinRequest.java  # 群组加入请求实体类，表示用户请求加入群组
    │ └── GroupMember.java  # 群组成员实体类，表示用户在群组中的成员身份和角色
    ├── message/  # 消息相关实体类
    │ └── Message.java  # 通用消息实体类，表示各种类型的消息（私信或群消息）
    └── README.md  # 说明文档
```
