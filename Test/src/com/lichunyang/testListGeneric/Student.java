package com.lichunyang.testListGeneric;

/**
 * @author ChunyangLi
 * @create 2021-06-14-23:11
 */
public class Student extends Person{
    private int grade;

    public Student(){}

    public Student(int grade) {
        this.grade = grade;
    }

    public Student(String name, int grade) {
        super(name);
        this.grade = grade;
    }

    public Student(String gender, int age, String name, int grade) {
        super(gender, age, name);
        this.grade = grade;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
