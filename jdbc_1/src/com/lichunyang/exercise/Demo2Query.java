package com.lichunyang.exercise;

import com.lichunyang.preparedstatement.GeneralQuery;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * @author ChunyangLi
 * @create 2021-06-15-10:13
 */
public class Demo2Query {
    public static void main(String[] args) {
        System.out.println("请选择要输入的类型:");
        System.out.println("a: 准考证号");
        System.out.println("b: 身份证号");

        char c= readKeyBoard(1,false).toLowerCase().charAt(0);
        String str=null;
        String sql="select FlowId flowId, Type type, IDCard, ExamCard examCard, StudentName name, Location location, Grade grade from examstudent where ";

        switch (c){
            case 'a':
                System.out.println("请输入准考证号:");
                 str = readKeyBoard(23,false);
                sql=sql+"ExamCard=?";
                break;
            case 'b':
                System.out.println("请输入身份证号:");
                 str = readKeyBoard(23,false);
                sql=sql+"IDCard=?";
                break;
            default:
                System.out.println("输入有误，重新进入程序！");
                System.exit(2);
        }
        List<Student> students = GeneralQuery.QueryForGeneralTable(Student.class, sql, str);
        System.out.println("=========查询结果==========");
//        students.forEach(student -> student.printInfo());
        students.forEach(Student::printInfo);
    }

    public static String readKeyBoard(int limit, boolean blankReturn){
        Scanner sc = new Scanner(System.in);
        String str=null;
        while (sc.hasNextLine() &&(str=sc.nextLine())!=null){
            if(str.length()==0 && blankReturn)
                break;
            else if(str.length()==0 && !blankReturn)
                continue;
            else if(str.length()>limit ){
                System.out.println("输入字符过长，请重新输入！");
            }else{
                break;
            }
        }
        return str;
    }
}
