# GroupService

`GroupService` 类提供了对群组、群成员、入群申请和群消息的管理功能。

## 提供的服务与接口

### 群组管理

- `Group getGroupById(int groupId)`: 根据群组 ID 获取群组信息。
- `Group getGroupByName(String groupName)`: 根据群组名称获取群组信息。
- `void addGroup(Group group, int creatorUserId)`: 添加新群组，并将创建者添加为群主。
- `void updateGroup(Group group, int userId)`: 更新群组信息，权限为 `owner` 有权操作。
- `void deleteGroup(int groupId, int userId)`: 删除群组，权限为 `owner` 有权操作。
- `List<Group> getAllGroups()`: 获取所有群组信息。

### 群成员管理

- `GroupMember getGroupMemberById(int groupId, int userId)`: 根据群组 ID 和用户 ID 获取群成员信息。
- `void updateGroupMember(GroupMember member)`: 更新群成员信息。
- `void deleteGroupMember(int groupId, int userId)`: 删除群成员。
- `List<GroupMember> getAllGroupMembers()`: 获取所有群成员信息。

### 入群申请管理

- `void addGroupJoinRequest(int groupId, int userId, String requestMessage)`: 添加入群申请。
- `List<GroupJoinRequest> getGroupJoinRequestsByGroupId(int groupId)`: 根据群组 ID 获取入群申请列表。
- `void handleGroupJoinRequest(int requestId, int userId, boolean isApproved)`: 处理入群申请，权限为 `owner,admin` 有权操作。

### 群消息管理

- `void addGroupMessage(int groupId, int senderId, String messageContent)`: 添加群消息。
- `GroupMessage getGroupMessageById(int messageId)`: 根据消息 ID 获取群消息。
- `void updateGroupMessage(GroupMessage message)`: 更新群消息。
- `void deleteGroupMessage(int messageId)`: 删除群消息。
- `List<GroupMessage> getAllGroupMessages()`: 获取所有群消息。
- `List<GroupMessage> getGroupMessagesByGroupId(int groupId)`: 根据群组 ID 获取群消息。
- `List<GroupMessage> getGroupMessagesBySenderId(int senderId)`: 根据发送者 ID 获取群消息。
- `List<GroupMessage> getGroupMessagesBySenderIdAndGroupId(int senderId, int groupId)`: 根据发送者 ID 和群组 ID 获取群消息。
