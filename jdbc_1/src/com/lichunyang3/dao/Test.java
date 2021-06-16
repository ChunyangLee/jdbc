package com.lichunyang3.dao;

import com.lichunyang.bean.Customer;
import com.lichunyang.util.JDBCUtils;

import java.sql.Connection;
import java.sql.Date;

/**
 * @author ChunyangLi
 * @create 2021-06-15-19:17
 */
public class Test {
   private CustomerDAOImpl dao = new CustomerDAOImpl();

    @org.junit.Test
    public void test1(){
        Connection con = JDBCUtils.getConnection();
        dao.insert(con,new Customer(1,"于小飞","xiaofei@qq.com",new Date(23425453543L)));
        JDBCUtils.closeConnection(con);
    }

}
