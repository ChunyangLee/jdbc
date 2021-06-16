package com.lichunyang.preparedstatement;

import com.lichunyang.bean.Order;
import com.lichunyang.util.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ChunyangLi
 * @create 2021-06-14-22:12
 */
public class OrderQuery {
    @Test
    public void test(){
        String sql="select order_id orderId, order_name orderName, order_date orderDate from `order` where order_id<?";
        System.out.println(QueryForOrder(sql, 3));
    }

    public static List<Order> QueryForOrder(String sql, Object...args){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs=null;
        List<Order> lst= new ArrayList<>();

        try {
            con = JDBCUtils.getConnection();
            ps = con.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            String columnName;
            Class<Order> orderClass = Order.class;
            Order or;
            while (rs.next() && (or=new Order())!=null){
                for (int i = 0; i < columnCount; i++) {
                    columnName = rsmd.getColumnLabel(i+1);
                    Field f = orderClass.getDeclaredField(columnName);
                    f.setAccessible(true);
                    f.set(or,rs.getObject(i+1));
                }
                lst.add(or);
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.closeResourses(con,ps,rs);
        }
        return lst;
    }

}
