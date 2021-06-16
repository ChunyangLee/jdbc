package com.lichunyang4.connectionpool;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.dbcp.DataSourceConnectionFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author ChunyangLi
 * created on 2021-06-16-16:02
 */
public class DBCPTest {

    @Test
    public void test() throws Exception {
//        DataSource source=new BasicDataSource();
//        //和c3p0一样，也要设置连接属性，
//        //...
//        System.out.println(source.getConnection());

        //通过配置文件
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("dbcp.properties");
        Properties pros=new Properties();
        pros.load(is);
        DataSource source = BasicDataSourceFactory.createDataSource(pros);

        System.out.println(source.getConnection());


    }

}
