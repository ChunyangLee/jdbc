package com.lichunyang4.connectionpool;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author ChunyangLi
 * created on 2021-06-15-21:49
 */
public class C3P0Test {

    //方式一: 硬编码，不用配置文件
    @Test
    public void test() throws Exception{
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass( "com.mysql.cj.jdbc.Driver" ); //loads the jdbc driver
        cpds.setJdbcUrl( "jdbc:mysql://localhost:3306/test?useUnicode=true&useSSL=false&serverTimezone=Asia/Shanghai&rewriteBatchedStatements=true" );
        cpds.setUser("root");
        cpds.setPassword("rishunnyou999");

        Connection con = cpds.getConnection();
        System.out.println(con);
    }
    @Test
    public void test2() throws SQLException {
        ComboPooledDataSource cpds = new ComboPooledDataSource("myC3p0");
        Connection connection = cpds.getConnection();
        System.out.println(connection);
    }

}
