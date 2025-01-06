package manager;

import object.Item;
import utils.JDBCUtils;

import java.sql.*;
import java.util.ArrayList;

/*
 * itemManager类包含实现商品的增删改查的函数
 * */
public class ItemManager {
    /*创建商品
     * 传入：
     * 商品名name
     * 商品价格price
     * */
    public static void insertItem(String name, double price) {
        Connection con = null;
        PreparedStatement st = null;
        try {
            if (price < 0) {
                throw new IllegalNum();
            }
            if (selectItem(name) != null) {
                throw new IllegalName();
            }
            con = JDBCUtils.getConnection();
            con.setAutoCommit(false); // 关闭自动提交，开启事务管理
            String sql = "INSERT INTO item(itemname, itemprice) VALUES(?, ?)";
            st = con.prepareStatement(sql);
            st.setString(1, name);
            st.setDouble(2, price);
            int i = st.executeUpdate();
            if (i > 0) {
                con.commit(); // 提交事务
                System.out.println("创建商品成功");
            }
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // 回滚事务
                    System.out.println("事务回滚成功");
                } catch (SQLException rollbackEx) {
                    System.out.println("回滚失败：" + rollbackEx.getMessage());
                }
            }
            throw new RuntimeException(e);
        } catch (IllegalNum e) {
            System.out.println("输入的商品价格是非法价格");
        } catch (IllegalName e) {
            System.out.println("该商品已经存在");
        } finally {
            JDBCUtils.release(con, st, null); // 释放资源
        }
    }


    /*
     * 删除商品
     * 传入：
     * 商品名name
     * */
    public static void deleteItem(String name) {
        if (OrderManager.isInOrder(name)) {
            System.out.println("该商品正处在交易中，删除失败");
            return;
        }
        Connection con = null;
        PreparedStatement st = null;
        try {
            con = JDBCUtils.getConnection();
            con.setAutoCommit(false);
            String sql = "DELETE FROM item WHERE itemname=?";
            st = con.prepareStatement(sql);
            st.setString(1, name);
            int i = st.executeUpdate();
            if (i > 0) {
                con.commit();
                System.out.println("删除商品成功");
            } else {
                System.out.println("系统中不存在该商品");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.release(con, st, null);
        }
    }

    /*
     * 修改商品价格
     * 传入：
     * 商品名name
     * 商品新价格price
     * */
    public static void updateItem(String name, double price) {
        Connection con = null;
        PreparedStatement st = null;
        try {
            if (price < 0) {
                throw new IllegalNum();
            }
            con = JDBCUtils.getConnection();
            con.setAutoCommit(false);
            String sql = "UPDATE item SET itemprice=? WHERE itemname=?";
            st = con.prepareStatement(sql);
            st.setDouble(1, price);
            st.setString(2, name);
            int i = st.executeUpdate();
            if (i > 0) {
                con.commit();
                System.out.println("更新商品成功");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IllegalNum e) {
            System.out.println("输入的商品价格是非法价格");
        } finally {
            JDBCUtils.release(con, st, null);
        }
    }

    /*
     * 查找商品
     * 传入：
     * 商品名name
     * */
    public static Item selectItem(String name) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        Item item = null;
        try {
            con = JDBCUtils.getConnection();
            con.setAutoCommit(false);
            String sql = "SELECT itemid,itemname,itemprice FROM item WHERE itemname=?";
            st = con.prepareStatement(sql);
            st.setString(1, name);
            rs = st.executeQuery();
            if (rs.next()) {
                con.commit();
                item = new Item(rs.getInt("itemId"), rs.getString("itemName"), rs.getDouble("itemPrice"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.release(con, st, rs);
        }
        return item;
    }

    /*
     * 查找全部商品
     * */
    public static ArrayList<Item> selectAllItem() {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        ArrayList<Item> itemArrayList = new ArrayList<>();
        try {
            con = JDBCUtils.getConnection();
            con.setAutoCommit(false);
            String sql = "SELECT itemid,itemname,itemprice FROM item";
            st = con.prepareStatement(sql);
            rs = st.executeQuery();
            if (rs == null) {
                System.out.println("系统中还未存入商品");
            } else {
                while (rs.next()) {
                    itemArrayList.add(new Item(rs.getInt("itemId"), rs.getString("itemName"), rs.getDouble("itemPrice")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.release(con, st, rs);
        }
        return itemArrayList;
    }
}
