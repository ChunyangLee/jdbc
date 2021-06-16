package com.lichunyang4.connectionpool;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author ChunyangLi
 * created on 2021-06-16-16:44
 */
public class DruidTest {
    @Test
    public void test() throws Exception {
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
        Properties pros = new Properties();
        pros.load(is);

        DataSource source = DruidDataSourceFactory.createDataSource(pros);
        System.out.println(source.getConnection());
    }
}
