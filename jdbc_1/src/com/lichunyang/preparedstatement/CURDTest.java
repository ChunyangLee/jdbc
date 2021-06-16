package com.lichunyang.preparedstatement;

import com.lichunyang.util.JDBCUtils;
import org.junit.Test;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Properties;

/**
 * @author ChunyangLi
 * @create 2021-06-14-11:19

 * 增删改操作，
 */
public class CURDTest {
    @Test
    public void test(){
        String sql = "insert into customers(name, email, birth) values(?,?,?)";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parse = dtf.parse("1995-04-21 00:00:00", LocalDateTime::from);
        Date date = new Date(parse.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        CURDTest.GeneralOperation(sql,"哪吒","nezha@gmail.com",date);
    }

    public static void GeneralOperation(String sql, Object ...args){
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = JDBCUtils.getConnection();
            ps = con.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.closeStatement(ps);
            JDBCUtils.closeConnection(con);
        }

    }

    @Test
    public void testUpdate() {
        //1. 获取连接
        Connection con=null;
        PreparedStatement ps=null;
        try {
            con = JDBCUtils.getConnection();
            //2. 预编译sql语句，返回PrepareStatement实例
            String sql = "update customers set name=? where id=?";
             ps = con.prepareStatement(sql);
            //3. 填充占位符
            ps.setString(1, "王峰");
            ps.setInt(2, 20);
            //4. 执行，且关闭资源
            ps.execute();
            System.out.println("修改成功！");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.closeStatement(ps);
            JDBCUtils.closeConnection(con);
        }
    }

    @Test
    //插入操作，
    public void testInsert() {
        //读取配置文件
        PreparedStatement ps = null;
        Connection con = null;
        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
            Properties pro = new Properties();
            pro.load(is);
            String driverClass = pro.getProperty("driverClass");
            String url = pro.getProperty("url");
            //加载驱动
            Class.forName(driverClass);
            //1. 获取连接
            con = DriverManager.getConnection(url, pro);
            System.out.println(con);

            //insert
            //2. 预编译sql语句，返回实例
            String sql = "insert into customers(name, email, birth) values(?,?,?)";
            ps = con.prepareStatement(sql);
            //填充占位符
            ps.setString(1, "哪吒");
            ps.setString(2, "nezha@gmail.com");
            //日期转换
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            TemporalAccessor parse = dtf.parse("1000-01-01 00:00:00");
            long aLong = parse.getLong(ChronoField.MILLI_OF_SECOND);

            ps.setDate(3, new Date(aLong));
            //3. 执行操作
            ps.execute();
            System.out.println("插入成功！");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close(); //4. 关闭资源
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                if (con != null) con.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }


}
