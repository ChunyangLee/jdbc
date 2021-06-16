package com.lichunyang2.dao;

import com.lichunyang.bean.Customer;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * @author ChunyangLi
 * @create 2021-06-15-18:45
 *
 *      规范针对customers表的常用操作
 */
public interface CustomerDAO {
    /**
     *  将Customer对象cus添加乳数据库中
     * @param con
     * @param cus
     */
    void insert(Connection con, Customer cus);

    void deleteById(Connection con, int id);

    void update(Connection con, Customer cus);

    Customer getCustomerById(Connection con, int id);

    List<Customer> getAll(Connection con);

    Long getCount(Connection con);

    Date getMaxBirth(Connection con);

}
