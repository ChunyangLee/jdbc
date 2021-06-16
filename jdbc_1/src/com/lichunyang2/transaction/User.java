package com.lichunyang2.transaction;

/**
 * @author ChunyangLi
 * @create 2021-06-15-17:42
 */
public class User {
    private String user;
    private String password;
    private double balance;

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                '}';
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public User(String user, String password, double balance) {
        this.user = user;
        this.password = password;
        this.balance = balance;
    }
}
