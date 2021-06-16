package com.lichunyang3.dao;

import com.lichunyang.bean.Customer;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * @author ChunyangLi
 * @create 2021-06-15-18:54
 *
 *
 */
public class CustomerDAOImpl extends BaseDAO<Customer> implements CustomerDAO {

    @Override
    public void insert(Connection con, Customer cus) {
        String sql="insert into customers(name, email, birth) values(?,?,?)";
        try {
            update(con,sql,cus.getName(),cus.getEmail(),cus.getBirth());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Connection con, int id) {
        String sql = "delete from customers where id=?";
        try {
            update(con,sql,id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Connection con, Customer cus) {
        String sql = "update customers set name=?, email=?,birth=? where id=?";
        try {
            update(con,sql,cus.getName(),cus.getEmail(),cus.getBirth(),cus.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Customer getCustomerById(Connection con, int id) {
        String sql="select id, name, email, birth from customers there id =?";
        Customer cust = getInstance(con, sql, id);
        return cust;
    }

    @Override
    public List<Customer> getAll(Connection con) {
        String sql="select id, name, email, birth from customers";
        List<Customer> customers = QueryForGeneralTable(con, sql);
        return customers;
    }

    @Override
    public Long getCount(Connection con) {
        String sql="select count(*) from customers";
        Long value = null;
        try {
            value = getValue(con, sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    @Override
    public Date getMaxBirth(Connection con) {
        String sql="SELECT max(birth) FROM customers";
        Date value = null;
        try {
            value = getValue(con,sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}
