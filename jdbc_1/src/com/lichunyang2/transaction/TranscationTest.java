package com.lichunyang2.transaction;

import com.lichunyang.preparedstatement.CURDTest;
import com.lichunyang.preparedstatement.GeneralQuery;
import com.lichunyang.util.JDBCUtils;
import com.lichunyang2.util2.CURDUtil;
import org.junit.Test;

import java.sql.Connection;

/**
 * @author ChunyangLi
 * @create 2021-06-15-15:31
 */
public class TranscationTest{
    @Test
    public void test2() throws Exception{
        Connection con = null;
        try {
            String sql1="update user_table set balance:= balance-100 where user='AA'";
            String sql2="update user_table set balance:= balance+100 where user='BB'";
            con = JDBCUtils.getConnection();
            con.setAutoCommit(false);
            CURDUtil.update(con,sql1);
            //模拟网络异常，下面的不执行了。
            System.out.println(100/0);
            CURDUtil.update(con,sql2);
            System.out.println("转账成功！");
            con.commit();
        } catch (Exception e) {
            //出异常，回滚操作
            System.out.println("转账失败，回滚！");
            con.rollback();
            e.printStackTrace();
        } finally {
            //针对使用数据库连接池的情况，关闭连接要还回去，因此还得设置回去
            con.setAutoCommit(true);
            con.close();
        }

    }
    //转账测试,未考虑事务，会出现问题，引出事务
    @Test
    public void test1(){
        String sql1="update user_table set balance:= balance-100 where user='AA'";
        String sql2="update user_table set balance:= balance+100 where user='BB'";
        CURDTest.GeneralOperation(sql1);
        //模拟网络异常，下面的不执行了。
        System.out.println(100/0);

        CURDTest.GeneralOperation(sql2);

    }

}
