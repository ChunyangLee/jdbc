package com.lichunyang.preparedstatement;

import com.lichunyang.bean.Customer;
import com.lichunyang.util.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ChunyangLi
 * @create 2021-06-14-17:39
 */
public class CustomersQuery {
    @Test
    public void testQuery2(){
        String sql = "select id,name,email from customers where id < ?";
//        List<Customer> list = QueryForCustomers(sql, "id", 5);
        List<Customer> list = QueryForCustomers(sql, 5);
        System.out.println(list);
    }

    //针对Customers表查询， 根据给定字段查找
    public static List<Customer> QueryForCustomers(String sql, Object... args){
        Connection con =null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<Customer> cusList=null;
        try {
             con = JDBCUtils.getConnection();
             ps = con.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            //相当于hasNext加上next
            Customer customer=null;
            Class<? extends Customer> aClass = Customer.class;
            Object o=null;
            while (rs.next() && (customer = new Customer())!=null){
                if(cusList==null){
                    cusList=new ArrayList<Customer>();
                }
                for (int i = 0; i < columnCount; i++) {
                    o = rs.getObject(i + 1);
                    //获取列的列名，是表中的列名，不是别名
                    String columnName = rsmd.getColumnName(i+1);
                    Field f = aClass.getDeclaredField(columnName);
                    f.setAccessible(true);
                    f.set(customer,o);
                }
                cusList.add(customer);
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.closeResourses(con,ps,rs);
        }
        return cusList;
    }

    //针对Customers表查询，根据id找，写死了。
    @Test
    public void testQuery1(){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = JDBCUtils.getConnection();
            String sql = "select id, name, email, birth from customers where ? =?";
            ps = con.prepareStatement(sql);
            ps.setObject(1,3);

            rs = ps.executeQuery();
            if(rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String email = rs.getString(3);
                Date birth = rs.getDate(4);
                Customer cus1 = new Customer(id, name, email, birth);
                System.out.println(cus1);
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.closeResourses(con,ps,rs);
        }


    }
}
