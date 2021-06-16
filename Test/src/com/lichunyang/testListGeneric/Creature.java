package com.lichunyang.testListGeneric;

/**
 * @author ChunyangLi
 * @create 2021-06-14-23:08
 */
public class Creature {
    private String gender;
    private int age;

    public Creature() {
    }

    public Creature(String gender, int age) {
        this.gender = gender;
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
