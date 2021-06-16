package com.lichunyang.preparedstatement;

import com.lichunyang.bean.Order;
import com.lichunyang.util.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ChunyangLi
 * @create 2021-06-14-22:44
 */
public class GeneralQuery {
    @Test
    public void test(){
        String sql="select order_id orderId, order_name orderName, order_date orderDate from `order` where order_id<?";
        List<Order> orders = QueryForGeneralTable(Order.class, sql, 3);
        orders.forEach(System.out::println);

    }

    public static <T> List<T> QueryForGeneralTable(Class<T> aClass, String sql, Object... args){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs=null;
        List<T> lst= new ArrayList<>();

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
            T t;
            while (rs.next() && (t=aClass.newInstance())!=null){
                for (int i = 0; i < columnCount; i++) {
                    columnName = rsmd.getColumnLabel(i+1);
                    Field f = aClass.getDeclaredField(columnName);
                    f.setAccessible(true);
                    f.set(t,rs.getObject(i+1));
                }
                lst.add(t);
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.closeResourses(con,ps,rs);
        }
        return lst;
    }

}
