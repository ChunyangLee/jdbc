package com.lichunyang.exercise;

import com.lichunyang.util.JDBCUtils;

import javax.sound.midi.Soundbank;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author ChunyangLi
 * @create 2021-06-15-09:47
 */
public class Demo1 {
    public static void main(String[] args) {
//        Type: IDCard: ExamCard: StudentName: Location: Grade:
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入Type: ");
        int type = sc.nextInt();
        System.out.print("请输入IDCard: ");
        String idCard = sc.next();
        System.out.print("请输入ExamCard: ");
        String examCard = sc.next();
        System.out.print("请输入StudentName: ");
        String studentName = sc.next();
        System.out.print("请输入Location: ");
        String location = sc.next();
        System.out.print("请输入Grade: ");
        int grade = sc.nextInt();
        String sql="insert into examstudent(Type,IDCard, ExamCard, StudentName, Location, Grade) values(?,?,?,?,?,?)";
        int i = GeneralOperation(sql,type,idCard,examCard,studentName,location,grade);
        System.out.println("成功插入"+i+"条数据!");
    }

    public static int GeneralOperation(String sql, Object ...args){
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = JDBCUtils.getConnection();
            ps = con.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
//            ps.execute();
            return ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.closeStatement(ps);
            JDBCUtils.closeConnection(con);
        }
        return 0;
    }
}

