package com.lichunyang.conncetion;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author ChunyangLi
 * @create 2021-06-13-15:29
 */
public class ConnectionTest {
    @Test
    public void test(){
        Properties properties = System.getProperties();
        System.out.println(properties);

        System.out.println(System.getProperty("jdbc.drivers"));
    }
    @Test
    public void connection() throws Exception{
        //读取配置文件
        InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties pro = new Properties();
        pro.load(is);
        String driverClass = pro.getProperty("driverClass");
        String url=pro.getProperty("url");

        //加载驱动
        Class.forName(driverClass);

        //获取连接
        Connection con = DriverManager.getConnection(url, pro);
        System.out.println(con);
    }

    @Test
    //DriverManager 替换Driver
    public void testConnection4() throws Exception{
        //注册驱动, 加载类自动运行，静态代码块，在里面注册了， 见Driver源码，
        Class c1 = Class.forName("com.mysql.jdbc.Driver");
        //获取连接
        String url = "jdbc:mysql://localhost:3306/test" ;
        Properties pro = new Properties();
        pro.setProperty("user","root");
        pro.setProperty("password","rishunnyou999");
        //必须设置用户使用的编码集,否则出异常
        pro.setProperty("characterEncoding","utf-8");
        Connection con1 = DriverManager.getConnection(url, pro);
        //Unknown initial character set
        //必须指定字符集,
        System.out.println(con1);
    }

    @Test
    //DriverManager 替换Driver
    public void testConnection3() throws Exception{
        //注册驱动
        Class c1 = Class.forName("com.mysql.jdbc.Driver");
        Driver d1 = (Driver)c1.newInstance();
        DriverManager.registerDriver(d1);

        //获取连接
        String url = "jdbc:mysql://localhost:3306/test" ;
        Properties pro = new Properties();
        pro.setProperty("user","root");
        pro.setProperty("password","rishunnyou999");
        //必须设置用户使用的编码集,否则出异常
        pro.setProperty("characterEncoding","utf-8");
        Connection con1 = DriverManager.getConnection(url, pro);
        //Unknown initial character set
        //必须指定字符集,
        System.out.println(con1);
    }

    @Test
    public void testConnection2() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        //反射实现获取Driver实现类对象，避免出现厂商API
        Class c1 = Class.forName("com.mysql.jdbc.Driver");
        Driver d1  = (Driver) c1.newInstance();
        // 后面都一样了，
        String s = "jdbc:mysql://localhost:3306/test";
        //jdbc:mysql是协议
        //ip地址:端口号
        Properties pro = new Properties();
        pro.setProperty("user","root");
        pro.setProperty("password","rishunnyou999");
        //必须设置用户使用的编码集,否则出异常
        pro.setProperty("characterEncoding","utf-8");

        Connection con1 = d1.connect(s, pro);
        System.out.println(con1);
    }

//    @Test
//    public void testConnection1() throws SQLException {
//        Driver driver = new com.mysql.cj.jdbc.Driver();
//
//        String s = "jdbc:mysql://localhost:3306/test";
//        //jdbc:mysql是协议
//        //ip地址:端口号
//        Properties pro = new Properties();
//        pro.setProperty("user","root");
//        pro.setProperty("password","rishunnyou999");
//        //必须设置用户使用的编码集,否则出异常
//        pro.setProperty("characterEncoding","utf-8");
//
//        Connection c1 = driver.connect(s, pro);
//        System.out.println(c1);
//    }
}
