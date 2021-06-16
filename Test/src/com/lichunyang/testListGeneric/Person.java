package com.lichunyang.testListGeneric;

/**
 * @author ChunyangLi
 * @create 2021-06-14-23:09
 */
public class Person extends Creature{
       private String name;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    public Person(String gender, int age, String name) {
        super(gender, age);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
