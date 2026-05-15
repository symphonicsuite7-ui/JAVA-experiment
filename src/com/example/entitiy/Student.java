package com.example.entitiy;

public class Student {
    private String name;
    private int age;
    private String id;

    // 无参构造方法
    public Student() {}

    // 全参构造方法
    public Student(String name, int age, String id) {
        this.name = name;
        this.age = age;
        this.id = id;
    }

    @Override
    public String toString() {
        return "大家好，我的名字叫" + name + "，今年" + age + "岁，我的学号是" + id + "。\n我来自中南民族大学计算机科学学院 2024 级软件工程专业。";
    }
}
