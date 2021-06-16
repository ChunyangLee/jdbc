package com.lichunyang.teststring;

import org.junit.Test;

import java.util.HashSet;
import java.util.Objects;

/**
 * @author ChunyangLi
 * @create 2021-06-14-09:41
 */
public class Demo1 {
    @Test
    public void test(){
        //破坏了set的唯一性， set只保证添加时候唯一， 但是加进去修改不能保证
        HashSet<User> h1 = new HashSet<User>();
        User u1 = new User("Tom", "1");
        User u2 = new User("Mot", "1");
        h1.add(u1);
        h1.add(u2);
        System.out.println(h1);
        u2.setUser("Tom");
        System.out.println(h1);
        System.out.println(u1.hashCode() == u2.hashCode()); //true
        System.out.println(u1.equals(u2)); //true

        User u3 = new User("Mot", "1");  //添加成功
        User u4 = new User("Tom", "1");  //添加不了
        h1.add(u3);
        h1.add(u4);
        System.out.println(h1);

    }
    @Test
    public void test2(){
        String s = "abc" ;
        s="aaa";
        System.out.println(s);
    }
}

class User {

    private String user;
    private String password;

    public User() {
    }

    public User(String user, String password) {
        super();
        this.user = user;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User [user=" + user + ", password=" + password + "]";
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user1 = (User) o;
        return Objects.equals(user, user1.user) && Objects.equals(password, user1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, password);
    }
}
