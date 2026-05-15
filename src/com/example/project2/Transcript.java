package com.example.project2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Transcript {
    public int getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
        // 创建课程列表
        List<Course> courseList = new ArrayList<>(courseNumber);

        Scanner scanner = new Scanner(System.in);

        double totalScore = 0;
        // 输入课程信息
        for (int i = 0; i < courseNumber; i++) {
            Course course = new Course();
            System.out.println("请输入第" + (i + 1) + "门课程信息：");
            System.out.print("课程名称：");
            course.setName(scanner.next());
            System.out.print("课程成绩：");
            double sc = scanner.nextDouble();
            if (sc < 0 || sc > 100){
            System.out.println("输入的课程成绩无效，请重新输入：");
            course.setScore(-1);
            courseList.add(course);
            continue;
            }
            course.setScore(sc);
            // 计算平均分
            totalScore += sc;
            this.averageScore = totalScore / courseNumber;

            courseList.add(course);
            // 输出课程信息
            for (Course course1 : courseList){
                System.out.println("课程名称：" + course1.getName() + " 课程成绩：" + course1.getScore());
            }
            System.out.println("平均分：" + this.averageScore);
        }


    }


    private int courseNumber;


    private double averageScore;


}