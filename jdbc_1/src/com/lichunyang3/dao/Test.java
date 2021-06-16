package com.lichunyang3.dao;

import com.lichunyang.bean.Customer;
import com.lichunyang.util.JDBCUtils;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * @author ChunyangLi
 * @create 2021-06-15-19:17
 */
public class Test {
   private CustomerDAOImpl dao = new CustomerDAOImpl();

    @org.junit.Test
    public void test1(){
        Connection con = null;
        try {
            con = JDBCUtils.getConnectionByDBCP();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        dao.insert(con,new Customer(1,"于小飞","xiaofei@qq.com",new Date(23425453543L)));
        List<Customer> list = dao.getAll(con);
        list.forEach(System.out::println);
        JDBCUtils.closeConnection(con);
    }


}
