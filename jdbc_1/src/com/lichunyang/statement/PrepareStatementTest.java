package com.lichunyang.statement;

import com.lichunyang.preparedstatement.GeneralQuery;
import com.lichunyang.util.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.Scanner;

/**
 * @author ChunyangLi
 * @create 2021-06-15-08:52
 */
public class PrepareStatementTest {
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);

        System.out.print("用户名：");
        String userName = scan.nextLine();
        System.out.print("密   码：");
        String password = scan.nextLine();

        // SELECT user,password FROM user_table WHERE USER = '1' or ' AND PASSWORD = '='1' or '1' = '1';
        String sql = "SELECT user,password FROM user_table WHERE USER = ? AND PASSWORD = ?";
//        Connection con = JDBCUtils.getConnection();
//        PreparedStatement ps = con.prepareStatement(sql);
//        ps.setString(1,userName);
//        ps.setString(2,password);
//        ResultSet rs = ps.executeQuery();
//        ResultSetMetaData rsmd = rs.getMetaData();
//        int columnCount = rsmd.getColumnCount();
//        while (rs.next()){
//            System.out.println(rs.getString(1));
//            System.out.println(rs.getString(2));
//        }

        //自己写的通用方法不包含String，因为不像自定义类，有属性
        List<User> users = GeneralQuery.QueryForGeneralTable(User.class, sql, userName, password);
        users.forEach(System.out::println);

    }
}
