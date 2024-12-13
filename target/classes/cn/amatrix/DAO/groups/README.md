# 文件结构及功能简述

## 文件结构

` groups ` 包实现了客户端与服务器间的数据交互，其中 `Imp/` 包中提供了程序使用的接口，`http/` 和 `mySQL/` 本别实现了基于 http 协议和基于 JDBC - mySQL 数据库的两种数据访问方式。本包的具体结构如下：

```sh
    user/ 
    ├── Imp/ # 接口类
    │ ├── GroupDAOImp.java
    │ ├── GroupMessageDAOImp.java
    │ ├── GroupJoinRequestDAOImp.java
    │ └── GroupMemberDAOImp.java
    ├── http/ # 基于 http 实现的数据访问类
    │ ├── GroupDAO.java
    │ ├── GroupMessageDAO.java
    │ ├── GroupJoinRequestDAO.java
    │ └── GroupMemberDAO.java
    ├── mySQL/ # 基于 mySQL 实现的数据访问类
    │ ├── GroupDAO.java
    │ ├── GroupMessageDAO.java
    │ ├── GroupJoinRequestDAO.java
    │ └── GroupMemberDAO.java
    └── README.md 
```

## 各个文件实现的功能

- Imp 文件夹：包含所有数据访问对象（DAO）的接口。这些接口定义了用户、私信、好友请求和好友关系等操作方法，为具体的实现提供了统一的规范。

- http 文件夹：实现了基于 HTTP 协议的 DAO。通过网络请求与服务器交互，实现对用户、私信等数据的远程访问和操作。

- mySQL 文件夹：实现了基于 MySQL 数据库的 DAO。直接与数据库交互，执行 SQL 操作，实现对数据的增删改查。

这样组织的思路是将接口与其不同的实现分离。接口定义了规范，具体实现可以根据需要选择 HTTP 或 MySQL。这种设计符合面向接口编程的原则，提高了代码的模块化和可扩展性，便于以后添加新的数据源或更改实现方式。
