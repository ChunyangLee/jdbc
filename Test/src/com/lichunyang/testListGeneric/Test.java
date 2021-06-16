package com.lichunyang.testListGeneric;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ChunyangLi
 * @create 2021-06-14-23:11
 */
public class Test {
    @org.junit.Test
    public void test3(){
        Person p = new Person();
        Creature c = new Creature();
        Student s = new Student();
        ArrayList<Person> arrP = new ArrayList<>();
        arrP.add(p);
        arrP.add(s);

    }

    @org.junit.Test
    public void test(){
        //List<?> 只能是Person才行
        List<? extends Creature> list = new ArrayList<Creature>();
        List<? extends Creature> list2 = new ArrayList<Person>();
        List<? extends Creature> list3 = new ArrayList<Student>();
        //因为不能确定list是什么，不能添加
//        list.add(new Creature());  //编译不过
//        list2.add(new Person());   //编译不过
//        list3.add(new Student());   //编译不过
        //可以查看
        Creature creature = list.get(0);
        Creature creature2 = list2.get(0);
        Creature creature3 = list3.get(0);
    }
    @org.junit.Test
    public void test2(){
        List<? super Student> list = new ArrayList<Student>();
        List<? super Person> list2 = new ArrayList<Person>();
        List<? super Student> list3 = new ArrayList<Creature>();

//        list.add(new Creature());  //编译不过
        list2.add(new Person());
        list2.add(new Student());
        list3.add(new Student());
    }

}
