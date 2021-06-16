package com.lichunyang2.util2;

import com.lichunyang.util.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ChunyangLi
 * @create 2021-06-15-15:42
 */
public class CURDUtil {
    public static void update(Connection con, String sql, Object ...args) throws Exception{
        PreparedStatement ps = null;
        ps = con.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1, args[i]);
        }
        ps.execute();
        JDBCUtils.closeStatement(ps);

    }

    public static <T> List<T> QueryForGeneralTable(Connection con, Class<T> aClass, String sql, Object... args){
        PreparedStatement ps = null;
        ResultSet rs=null;
        List<T> lst= new ArrayList<>();

        try {
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
            JDBCUtils.closeResourses(null,ps,rs);
        }
        return lst;
    }
}
