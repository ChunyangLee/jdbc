package com.lichunyang5.dbutils;

import com.lichunyang.bean.Customer;
import com.lichunyang.util.JDBCUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ChunyangLi
 * created on 2021-06-16-17:19
 */
public class QueryRunnerTest {

    //不同的Handler针对不同的结果集，
    //ScalarHandler 返回的结果可能不是对象，而是某个值如long， birth等
    //每个Hander实现类，对象表中的一条或多条数据
    @Test
    public void test2() throws Exception {
        QueryRunner qr = new QueryRunner();
        Connection con = JDBCUtils.getConnectionByDBCP();

        String sql="select * from customers where id <?";
        BeanListHandler<Customer> blh = new BeanListHandler<Customer>(Customer.class);
        List<Customer> list = qr.query(con, sql, blh, 5);
        list.forEach(System.out::println);
    }

    @Test
    public void test() throws Exception {
        QueryRunner qr = new QueryRunner();
        Connection con = JDBCUtils.getConnectionByDBCP();
        String sql = "insert into customers(name, email, birth) values(?,?,?)";
        int count= qr.update(con,sql,"蔡徐坤","6789@qq.com","1995-6-7");
        System.out.println("插入了"+count+"条数据！");
        JDBCUtils.closeConnection(con);
    }

    //自定义Handler
    //其实query，需要穿一个接口，要处理结果集ResultSet
    @Test
    public void test3() throws Exception{
        QueryRunner qr = new QueryRunner();
        Connection con = JDBCUtils.getConnectionByDBCP();

        String sql="select * from customers where id <?";
//        ResultSetHandler<Object> rsh = rs->{rs.next();return rs.getObject(1);};

        Object o = qr.query(con, sql, rs->{rs.next();return rs.getObject(1);}, 5);
        System.out.println(o);
        DbUtils.closeQuietly(con);
    }
    @Test
    public void test4() throws Exception{
        QueryRunner qr = new QueryRunner();
        Connection con = JDBCUtils.getConnectionByDBCP();

        String sql="select * from customers where id <?";
        ResultSetHandler<List<Customer>> rsh = rs->{
            ArrayList<Customer> lists = new ArrayList<>();
            while (rs.next()){
                int id = rs.getInt(1);
                lists.add(new Customer(id));
            }
            return lists;
        };

//        List<Customer> o = qr.query(con, sql, rsh, 5);

        MyBeanListHandler<Customer> mb = new MyBeanListHandler<>(Customer.class);
        List<Customer> o = qr.query(con, sql, mb::handle, 5);

        System.out.println(o);
    }

    //若想用lambda，方法引用，则必须处理异常，lambda和方法引用中不能抛异常
    //BeanListHandler中的handle就不行
    class MyBeanListHandler<T> {
        //获得当前类的泛型的方法。
        private final Class<T> aClass;

        public MyBeanListHandler(Class<T> aClass) {
            this.aClass = aClass;
        }

        public List<T> handle(ResultSet rs) {
            ArrayList<T> lists = null;
            try {
                lists = new ArrayList<>();
                while (rs.next()){
                    int id = rs.getInt(1);
                    lists.add(aClass.newInstance());
                }
            } catch (Exception throwables) {
                throwables.printStackTrace();
            }
            return lists;
        }
    }
}

