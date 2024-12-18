# InfoPanel 包简介

## 文件结构

```sh
    InfoPanel/     
    ├── user/ # 用户相关的信息面板
    │ ├── UserInfoPanel.java # 基础的用户信息面板
    │ ├── UserBriefInfoPanel.java # 简略的用户信息面板
    │ ├── UserDetailedInfoPanel.java # 详细的用户信息面板
    │ ├── ChangeInfoPanel.java # 修改用户信息的面板
    │ └── ChangeAvatar.java # 修改用户头像的面板
    ├── group/ # 依赖的组件
    │ ├── GroupInfoPanel.java # 基本的用户信息面板
    │ ├── GroupBriefInfoPanel.java # 简略的用户信息面板
    │ ├── GroupDetailedInfoPanel.java # 详细的群组信息面板
    │ ├── ChangeGroupInfoPanel.java # 修改群组信息的面板
    │ └── ChangeGroupAvatar.java # 修改群组头像的面板
    ├── InfoPanel.java # 信息面板抽象类
    ├── InfoPanelLists.java # 信息面板列表显示组件
    └── README.md 
```
