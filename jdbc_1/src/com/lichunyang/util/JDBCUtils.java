package com.lichunyang.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author ChunyangLi
 * @create 2021-06-14-15:10
 */
public class JDBCUtils {
    /**
     * 获得连接
     * @return
     */
    public static Connection getConnection(){
        Connection con=null;
        try {
            //读取配置文件
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
            Properties pro = new Properties();
            pro.load(is);
            String driverClass = pro.getProperty("driverClass");
            String url=pro.getProperty("url");
            //加载驱动
            Class.forName(driverClass);
            //1. 获取连接
            con = DriverManager.getConnection(url, pro);
            System.out.println("成功获取连接！");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
           // closeConnection(con);
        }
        return con;
    }

    public static void closeConnection(Connection con){
        try {
            if (con!=null) con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void closeStatement(Statement ps){
        try {
            if (ps!=null) ps.close(); //4. 关闭资源
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void closeResourses(Connection con, Statement ps){
        closeStatement(ps);
        closeConnection(con);
    }

    public static void closeResourses(Connection con, Statement ps, ResultSet rs){
        closeResourses(con,ps);
        try {
            if(rs!=null) rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
