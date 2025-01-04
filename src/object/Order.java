package object;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Order {
    private int orderId;
    private int itemId;
    private String purchaserName;
    private LocalDateTime orderTime;
    private double orderPrice;
    private int purchaseNum;

    public Order() {
    }

    public Order(int orderId, int itemId, String purchaserName, LocalDateTime orderTime, double orderPrice, int purchaseNum) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.purchaserName = purchaserName;
        this.orderTime = orderTime;
        this.orderPrice = orderPrice;
        this.purchaseNum = purchaseNum;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getItemId() {
        return itemId;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public int getPurchaseNum() {
        return purchaseNum;
    }

    public static void show(ArrayList<Order> arr){
        for (int i = 0; i < arr.size(); i++) {
            System.out.println("订单编号："+ arr.get(i).orderId+"；商品编号："+arr.get(i).itemId+"；购买人姓名："+arr.get(i).purchaserName+"；订单时间："+arr.get(i).orderTime+"；订单价格："+arr.get(i).orderPrice+"；采购数量："+arr.get(i).purchaseNum);
        }
    }
}
