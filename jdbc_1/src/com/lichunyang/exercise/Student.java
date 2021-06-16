package com.lichunyang.exercise;

/**
 * @author ChunyangLi
 * @create 2021-06-15-10:11
 */
public class Student {
    private int  flowId;
    private int type;
    private String IDCard;
    private String examCard;
    private String location;
    private String name;
    private int grade;

    public Student() {
    }

    public Student(int flowId, int type, String IDCard, String examCard, String location, String name, int grade) {
        this.flowId = flowId;
        this.type = type;
        this.IDCard = IDCard;
        this.examCard = examCard;
        this.location = location;
        this.name = name;
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student{" +
                "flowId=" + flowId +
                ", type=" + type +
                ", IDCard='" + IDCard + '\'' +
                ", examCard='" + examCard + '\'' +
                ", location='" + location + '\'' +
                ", name='" + name + '\'' +
                ", grade=" + grade +
                '}';
    }

    public void printInfo(){
        System.out.println("流水号: "+flowId);
        System.out.println("四/六级: "+type);
        System.out.println("身份证号: "+IDCard);
        System.out.println("准考证号: "+examCard);
        System.out.println("区域: "+location);
        System.out.println("学生名字: "+name);
        System.out.println("成绩: "+grade);
    }

    public int getFlowId() {
        return flowId;
    }

    public void setFlowId(int flowId) {
        this.flowId = flowId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIDCard() {
        return IDCard;
    }

    public void setIDCard(String IDCard) {
        this.IDCard = IDCard;
    }

    public String getExamCard() {
        return examCard;
    }

    public void setExamCard(String examCard) {
        this.examCard = examCard;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
