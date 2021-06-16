package com.lichunyang3.dao;

import com.lichunyang.util.JDBCUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ChunyangLi
 * @create 2021-06-15-18:12
 *
 *        在com.lichunyang2中的DAO没有使用泛型，这里使用泛型，
 *         实例化子类CustomerDAOImpl时，传递泛型参数T=Customer，
 */
public abstract class BaseDAO<T> {
    private Class<T> aClass;

    //this还是子类的对象，造的是子类的对象，
    {
        Type gs = this.getClass().getGenericSuperclass();
        Type[] actualTypeArguments = ((ParameterizedType) gs).getActualTypeArguments();
        Type actualTypeArgument = actualTypeArguments[0];
        aClass = (Class<T>) actualTypeArgument;
    }

    /**
     *      查询具体某个值， 生日，数目等
     * @param con
     * @param sql
     * @param args
     * @param <E>
     * @return
     * @throws Exception
     */
    public <E> E getValue(Connection con, String sql,Object... args) throws Exception {
        PreparedStatement ps = con.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i+1,args[i]);
        }
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            return (E) rs.getObject(1);
        }
        JDBCUtils.closeResourses(null,ps,rs);
        return null;
    }

    /**
     *  通用增，删，改操作。
     * @param con
     * @param sql
     * @param args
     * @throws Exception
     */
    public void update(Connection con, String sql, Object ...args) throws Exception{
        PreparedStatement ps = null;
        ps = con.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1, args[i]);
        }
        ps.execute();
        JDBCUtils.closeStatement(ps);

    }

    /**
     *  通用查询操作
     * @param con
     * @param sql
     * @param args
     * @return
     */
    public List<T> QueryForGeneralTable(Connection con, String sql, Object... args){
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

    public  T getInstance(Connection con, String sql, Object... args){
        PreparedStatement ps = null;
        ResultSet rs=null;
        T t=null;
        try {
            ps = con.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            String columnName;

            if (rs.next() && (t=aClass.newInstance())!=null){
                for (int i = 0; i < columnCount; i++) {
                    columnName = rsmd.getColumnLabel(i+1);
                    Field f = aClass.getDeclaredField(columnName);
                    f.setAccessible(true);
                    f.set(t,rs.getObject(i+1));
                }
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.closeResourses(null,ps,rs);
        }
        return t;
    }
}
