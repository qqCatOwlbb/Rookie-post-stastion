package utils;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtils {

    private static String driver=null;
    private static String url=null;
    private static String username=null;
    private static String password=null;

    static{
        try{
            InputStream in= JDBCUtils.class.getClassLoader().getResourceAsStream("utils/db.properties");
            if (in == null) {
                throw new RuntimeException("配置文件未找到！");
            }
            Properties properties=new Properties();
            properties.load(in);
            driver=properties.getProperty("driver");
            url=properties.getProperty("url");
            username=properties.getProperty("username");
            password=properties.getProperty("password");
            Class.forName(driver);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,username,password);
    }

    public static void release(Connection con, Statement st, ResultSet rs){
        if(con!=null){
            try{
                con.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        if(st!=null){
            try{
                st.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        if(rs!=null){
            try{
                rs.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
}
