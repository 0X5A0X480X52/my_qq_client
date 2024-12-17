# MySQLConnector 类

## 功能

MySQLConnector 类用于连接 MySQL 数据库。它提供了一个静态方法 `getConnection`，该方法返回一个数据库连接对象。通过调用此方法，您可以轻松地连接到指定的 MySQL 数据库。

## 设计

- **DRIVER**: MySQL 数据库驱动程序的类名。
- **URL**: 数据库连接的 URL，包括数据库的 IP 地址、端口号和数据库名称。
- **USER**: 数据库用户名。
- **PASSWORD**: 数据库密码。

### 方法

- `getConnection()`: 加载 MySQL 数据库驱动程序并返回一个数据库连接对象。如果驱动程序加载失败或连接失败，将打印相应的错误信息。
- `main(String[] args)`: 测试数据库连接是否成功。
