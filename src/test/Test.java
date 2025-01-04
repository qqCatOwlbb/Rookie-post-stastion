package test;

import manager.ItemManager;
import manager.OrderManager;
import object.Item;
import object.Order;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        testItemManager();
        testOrderManager();
    }

    // 测试 ItemManager 的增删改查功能
    public static void testItemManager() {
        System.out.println("=== 测试 ItemManager ===");

        // 测试插入商品
        System.out.println("测试插入商品：");
        ItemManager.insertItem("测试商品1", 100.0);
        ItemManager.insertItem("测试商品2", 200.0);

        // 测试查询商品
        System.out.println("测试查询商品：");
        Item item = ItemManager.selectItem("测试商品1");
        if (item != null) {
            System.out.println("查询到商品: " + item);
        } else {
            System.out.println("未找到商品");
        }

        // 测试更新商品
        System.out.println("测试更新商品价格：");
        ItemManager.updateItem("测试商品1", 150.0);

        // 验证更新后的价格
        item = ItemManager.selectItem("测试商品1");
        if (item != null) {
            System.out.println("更新后商品信息: " + item);
        }

        // 测试删除商品
        System.out.println("测试删除商品：");
        ItemManager.deleteItem("测试商品2");
        Item deletedItem = ItemManager.selectItem("测试商品2");
        if (deletedItem == null) {
            System.out.println("商品已成功删除");
        }
    }

    // 测试 OrderManager 的增删改查功能
    public static void testOrderManager() {
        System.out.println("=== 测试 OrderManager ===");

        // 测试创建订单
        System.out.println("测试创建订单：");
        OrderManager.insertOrder("测试商品1", "测试用户", 2);

        // 测试按时间查询订单
        System.out.println("测试按时间查询订单：");
        ArrayList<Order> ordersByTime = OrderManager.selectOrderByTime("测试用户");
        for (Order order : ordersByTime) {
            System.out.println(order);
        }

        // 测试按价格查询订单
        System.out.println("测试按价格查询订单：");
        ArrayList<Order> ordersByPrice = OrderManager.selectOrderByPrice("测试用户");
        for (Order order : ordersByPrice) {
            System.out.println(order);
        }

        // 测试修改订单
        System.out.println("测试修改订单：");
        if (!ordersByTime.isEmpty()) {
            int orderId = ordersByTime.get(0).getOrderId();
            OrderManager.updateOrder(orderId, "测试商品1", 3);
        }

        // 测试删除订单
        System.out.println("测试删除订单：");
        if (!ordersByTime.isEmpty()) {
            int orderId = ordersByTime.get(0).getOrderId();
            OrderManager.deleteOrder(orderId);
        }
    }
}
