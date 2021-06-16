package com.lichunyang2.transaction;

import com.lichunyang.util.JDBCUtils;
import com.lichunyang2.util2.CURDUtil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author ChunyangLi
 * @create 2021-06-15-17:42
 *
 *      T2修改后，T1查看还是改之前的，T2执行完，T1查看还是之前的，
 *      因为T2没有提交，也没有关闭连接（关闭连接自动提交）
 */
public class isolationleveltest {
    @Test
    public void test2() throws Exception {
        Connection con = JDBCUtils.getConnection();
        con.setAutoCommit(false);

        String sql="update user_table set balance=1000 where user=?";
        CURDUtil.update(con,sql,"CC");

        Thread.sleep(16000);
    }

    @Test
    public void test1() throws Exception {
        Connection con = JDBCUtils.getConnection();
        con.setAutoCommit(false);

        con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

        String sql="select * from user_table where user=?";
        List<User> ulist = CURDUtil.QueryForGeneralTable(con, User.class, sql, "CC");
        System.out.println(ulist);
    }
}
