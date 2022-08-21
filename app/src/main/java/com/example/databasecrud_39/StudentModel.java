package com.example.databasecrud_39;

public class StudentModel {
    private String name;
    private int rollNmber;
    private boolean isEnroll;
    private int uproll;

    @Override
    public String toString() {

        return "Student Name:"+" "+ name +"\n"+
                "Roll No:"+" "+ rollNmber+"\n"+
                "IsEnrolled:"+" "+ isEnroll;
    }

    public StudentModel(String name, int rollNmber, boolean isEnroll) {
        this.name = name;
        this.rollNmber = rollNmber;
        this.isEnroll = isEnroll;
    }

    public StudentModel(String name, int rollNmber, boolean isEnroll,int uproll) {
        this.name = name;
        this.rollNmber = rollNmber;
        this.isEnroll = isEnroll;
        this.uproll=uproll;
    }

    public StudentModel(int rollNmber) {
        this.rollNmber = rollNmber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRollNmber() {
        return rollNmber;
    }

    public void setRollNmber(int rollNmber) {
        this.rollNmber = rollNmber;
    }

    public int getUpRoll() {
        return uproll;
    }

    public void setUpRoll(int rollNmber) {
        this.uproll = uproll;
    }

    public boolean isEnroll() {
        return isEnroll;
    }

    public void setEnroll(boolean enroll) {
        isEnroll = enroll;
    }

}
