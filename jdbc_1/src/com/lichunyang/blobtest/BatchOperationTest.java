package com.lichunyang.blobtest;

import com.lichunyang.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.Duration;
import java.time.Instant;

/**
 * @author ChunyangLi
 * @create 2021-06-15-12:03
 *
 *      主要是批量插入, 插入2W条，看时间效率
 *      create table goods(
 *         id int auto_increment,
 *         name varchar(25),
 *         primary key(id)
 *      );
 *      方式一： Statement 被舍弃了
 *      方式二： PrepareStatement
 *      方式三:  rewriteBatchedStatement=true,
 *              addBatch, executeBatch, clearBatch
 *      方式四： 设置非自动提交， 都execute完最后提交
 *
 */
public class BatchOperationTest {
    @Test
    public void test3() throws Exception{
        Instant in1 = Instant.now();
        Connection con = JDBCUtils.getConnection();
        con.setAutoCommit(false);

        String sql="insert into goods(name) values(?)";
        PreparedStatement ps = con.prepareStatement(sql);
        for (int i = 1; i <= 1000000; i++) {
            ps.setString(1,"name_"+(i));
            //加入到batch里
            ps.addBatch();
            if(i%500==0) {
                ps.executeBatch();
                ps.clearBatch();
            }
        }
        con.commit();
        JDBCUtils.closeResourses(con,ps);
        Instant in2 = Instant.now();
        System.out.println("花费的时间为: "+ Duration.between(in1, in2).toMillis()); // 13s
    }

    @Test
    public void test2() throws Exception{
        Instant in1 = Instant.now();
        Connection con = JDBCUtils.getConnection();
        String sql="insert into goods(name) values(?)";
        PreparedStatement ps = con.prepareStatement(sql);
        for (int i = 0; i < 20000; i++) {
            ps.setString(1,"name_"+(i+1));
            //加入到batch里
            ps.addBatch();
            if(i%500==0) {
                ps.executeBatch();
                ps.clearBatch();
            }
            if(i==19999){
                ps.executeBatch();
            }
        }
        JDBCUtils.closeResourses(con,ps);
        Instant in2 = Instant.now();
        System.out.println("花费的时间为: "+ Duration.between(in1, in2).getSeconds()); // 1s
    }

    @Test
    public void test1() throws Exception{
        Instant in1 = Instant.now();
        Connection con = JDBCUtils.getConnection();
        String sql="insert into goods(name) values(?)";
        PreparedStatement ps = con.prepareStatement(sql);
        for (int i = 0; i < 20000; i++) {
            ps.setString(1,"name_"+(i+1));
            ps.execute();
        }
        JDBCUtils.closeResourses(con,ps);
        Instant in2 = Instant.now();
        System.out.println("花费的时间为: "+ Duration.between(in1, in2).getSeconds()); // 21s
    }

}
