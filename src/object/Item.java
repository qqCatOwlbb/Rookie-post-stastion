package object;

import java.util.ArrayList;

public class Item {
    private int itemId;
    private String itemName;
    private double itemPrice;

    public Item() {
    }

    public Item(int itemId, String itemName, double itemPrice) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public static void show(Item item){
        if(item==null){
            System.out.println("不存在该商品");
            return;
        }
        System.out.println("商品编号："+item.itemId+"；商品名："+item.itemName+"；商品价格："+item.itemPrice);
    }

    public static void showList(ArrayList<Item> arr){
        if(arr.size()==0){
            System.out.println("系统还没有一个商品");
            return;
        }
        for (int i = 0; i < arr.size(); i++) {
            System.out.println("商品编号："+arr.get(i).itemId+"；商品名："+arr.get(i).itemName+"；商品价格："+arr.get(i).itemPrice);
        }
    }
}
