# West2-Online Java方向第三轮

## 项目概述
本项目是一个简单的订单管理系统，基于 JDBC 实现商品和订单的增删改查功能。项目提供了事务管理、防止 SQL 注入等功能。

## 功能说明

### 商品管理
- **插入商品**：新增商品信息，包括商品名称和价格。
- **查询商品**：根据商品名称查找商品信息。
- **更新商品**：修改商品价格。
- **删除商品**：从数据库中移除商品。

### 订单管理
- **创建订单**：为指定的商品和用户创建订单。
- **查询订单**：
    - 按下单时间排序查询订单。
    - 按订单价格排序查询订单。
- **更新订单**：修改订单的商品或购买数量。
- **删除订单**：根据订单编号删除订单。

## 文件结构
```code
    |-src
     |-manager
      |-ItemManager.java    //实现商品管理的核心逻辑。
      |-OrderManager.java   //实现订单管理的核心逻辑。
      |-IllegalNum.java     //价格异常
      |-IllegalName.java    //名字异常
     |-object
      |-Item.java   //商品对象
      |-Order.java  //订单对象
     |-utils    //配置文件
      |-db.properties
      |-JDBCUtils.java
     |-test
      |-Test.java   //用于测试 `ItemManager` 和 `OrderManager` 的功能。
    |-README.md //项目说明文档（当前文件）。
```

- `ItemManager.java`：实现商品管理的核心逻辑。
- `OrderManager.java`：实现订单管理的核心逻辑。
- `Test.java`：用于测试 `ItemManager` 和 `OrderManager` 的功能。
- `README.md`：项目说明文档（当前文件）。

## 环境配置
1. **开发环境**：
    - MySQL 数据库
    - IntelliJ IDEA

2. **数据库配置**：
    - 数据库名称：`oms`
    - 数据库表结构：

```sql
CREATE TABLE `item` (
  `itemId` int NOT NULL AUTO_INCREMENT COMMENT '商品编号',
  `itemName` varchar(20) COLLATE utf8mb3_bin NOT NULL COMMENT '商品名',
  `itemPrice` double unsigned NOT NULL COMMENT '商品价格',
  PRIMARY KEY (`itemId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin

CREATE TABLE `order` (
  `orderId` int NOT NULL AUTO_INCREMENT COMMENT '订单编号',
  `itemId` int NOT NULL COMMENT '商品编号',
  `purchaserName` varchar(20) COLLATE utf8mb3_bin NOT NULL DEFAULT '匿名' COMMENT '购买人姓名',
  `orderTime` datetime NOT NULL COMMENT '下单时间',
  `orderPrice` double unsigned NOT NULL COMMENT '订单价格',
  `purchaseNum` int unsigned NOT NULL COMMENT '购买数量',
  PRIMARY KEY (`orderId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin
```

3. **JDBC 配置**：
    - 在项目的 `JDBCUtils` 类中设置数据库连接 URL、用户名和密码。

## 测试
- 使用 `Test` 类测试项目功能。
- 包含以下测试用例：
    - 插入、查询、更新、删除商品。
    - 创建、查询、更新、删除订单。
- 测试结果：
- 
  <img width="731" alt="屏幕截图 2025-01-06 133551" src="https://github.com/user-attachments/assets/24056cd2-f92b-447f-8e33-35bc42fb03b1" />

  <img width="724" alt="屏幕截图 2025-01-06 133617" src="https://github.com/user-attachments/assets/47b3569c-7503-45d2-8a1a-bbca62fb93dd" />



## 注意事项
1. **事务管理**：
    - 所有数据库操作均使用事务处理，保证数据的一致性。
2. **防止 SQL 注入**：
    - 使用 `PreparedStatement` 处理所有 SQL 查询。
3. **异常处理**：
    - 捕获并处理可能的 SQL 异常，确保程序稳定性。
4. **资源释放**
    - 使用`JDBCUtils`类中的`release()`释放
5. **处理冲突**
    - 当删除的商品在订单中时，会终止删除任务，并返回失败信息和原因
> 学号 022301121
