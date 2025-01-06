package manager;

import object.Item;
import object.Order;
import utils.JDBCUtils;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

/*
 * orderManager类包含实现商品的增删改查的函数
 * */
public class OrderManager {
    /*
    * 创建订单
    * 传入：
    * 商品名itemName
    * 采购人名purchaserName
    * 采购数量purchaseNum
    * */
    public static void insertOrder(String itemName,String purchaserName,int purchaseNum){
        Connection con=null;
        PreparedStatement st=null;
        try {
            Item item=ItemManager.selectItem(itemName);
            if(item==null){
                throw new IllegalName();
            }
            con= JDBCUtils.getConnection();
            con.setAutoCommit(false);
            String sql="INSERT INTO `order`(itemId,purchaserName,orderTime,orderPrice,purchaseNum) VALUES(?,?,?,?,?)";
            st=con.prepareStatement(sql);
            st.setInt(1,item.getItemId());
            st.setString(2,purchaserName);
            LocalDateTime orderTime = LocalDateTime.now();
            st.setTimestamp(3, Timestamp.valueOf(orderTime));
            st.setDouble(4,item.getItemPrice()*purchaseNum);
            st.setInt(5,purchaseNum);
            int i = st.executeUpdate();
            if (i > 0) {
                con.commit();
                System.out.println("创建订单成功");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IllegalName e) {
            System.out.println("不存在该商品");
        } finally{
            JDBCUtils.release(con,st,null);
        }
    }


    /*
    * 删除订单
    * 传入：
    * 订单编号orderId
    * */
    public static void deleteOrder(int orderId) {
        Connection con = null;
        PreparedStatement st = null;
        try {
            con = JDBCUtils.getConnection();
            con.setAutoCommit(false);
            String sql = "DELETE FROM `order` WHERE orderId=?";
            st = con.prepareStatement(sql);
            st.setInt(1, orderId);
            int i = st.executeUpdate();
            if (i > 0) {
                con.commit();
                System.out.println("删除订单成功");
            } else {
                System.out.println("系统中不存在该订单");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.release(con, st, null);
        }
    }

    /*
    * 查找订单，按时间先后顺序排列
    * 传入：
    * 采购人名purchaserName
    * */
    public static ArrayList<Order> selectOrderByTime(String purchaserName){
        Connection con=null;
        PreparedStatement st=null;
        ResultSet rs=null;
        ArrayList<Order> orderArrayList=new ArrayList<>();
        try {
            con=JDBCUtils.getConnection();
            con.setAutoCommit(false);
            String sql="SELECT orderId, itemId, orderTime, orderPrice, purchaseNum FROM `order` WHERE purchaserName = ? ORDER BY orderTime DESC";
            st=con.prepareStatement(sql);
            st.setString(1,purchaserName);
            rs=st.executeQuery();
            if(rs==null){
                System.out.println("系统中没有该客户的订单");
            }else{
                while(rs.next()){
                    orderArrayList.add(new Order(rs.getInt("orderId"),rs.getInt("itemId"),purchaserName, rs.getTimestamp("orderTime").toLocalDateTime(),rs.getDouble("orderPrice"),rs.getInt("purchaseNum")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.release(con,st,rs);
        }
        return orderArrayList;
    }

    /*
     * 查找订单，按价格降序顺序排列
     * 传入：
     * 采购人名purchaserName
     * */
    public static ArrayList<Order> selectOrderByPrice(String purchaserName){
        Connection con=null;
        PreparedStatement st=null;
        ResultSet rs=null;
        ArrayList<Order> orderArrayList=new ArrayList<>();
        try {
            con=JDBCUtils.getConnection();
            con.setAutoCommit(false);
            String sql="SELECT orderid, itemid, ordertime, orderprice, purchasenum FROM `order` WHERE purchasername = ? ORDER BY orderPrice DESC";
            st=con.prepareStatement(sql);
            st.setString(1,purchaserName);
            rs=st.executeQuery();
            if(rs==null){
                System.out.println("系统中没有该客户的订单");
            }else{
                while(rs.next()){
                    orderArrayList.add(new Order(rs.getInt("orderId"),rs.getInt("itemId"),purchaserName, rs.getTimestamp("orderTime").toLocalDateTime(),rs.getDouble("orderPrice"),rs.getInt("purchaseNum")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.release(con,st,rs);
        }
        return orderArrayList;
    }

    /*
     * 判断商品是否被购买
     * 传入：
     * 商品名itemName
     * */
    public static Boolean isInOrder(String itemName){
        Connection con=null;
        PreparedStatement st=null;
        ResultSet rs=null;
        Boolean flag=false;
        try {
            con=JDBCUtils.getConnection();
            con.setAutoCommit(false);
            String sql="SELECT EXISTS (SELECT 1 FROM `order` WHERE itemid = ?)";
            st=con.prepareStatement(sql);
            st.setInt(1,ItemManager.selectItem(itemName).getItemId());
            rs=st.executeQuery();
            if(rs.next() && rs.getInt(1) == 1){
                flag=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.release(con,st,rs);
        }
        return flag;
    }

    /*
    * 修改订单
    * 传入：
    * 订单编号orderId
    * 商品名itemName
    * 采购数量purchaseNum
    * */
    public static void updateOrder(int orderId, String itemName,int purchaseNum) {
        Connection con = null;
        PreparedStatement st = null;
        try {
            con = JDBCUtils.getConnection();
            con.setAutoCommit(false);
            String sql = "UPDATE `order` SET orderprice=?, purchasenum=? WHERE orderid=?";
            st = con.prepareStatement(sql);
            st.setDouble(1,ItemManager.selectItem(itemName).getItemPrice()*purchaseNum);
            st.setInt(2, purchaseNum);
            st.setInt(3,orderId);
            int i = st.executeUpdate();
            if (i > 0) {
                con.commit();
                System.out.println("修改订单成功");
            } else {
                System.out.println("系统中不存在该订单");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.release(con, st, null);
        }
    }
}
