/**
 * 个人代码签名：黄昭展
 * AI Coding 工具：Manus AI (Version: 2026-05-08)
 */
package com.example.project2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * 成绩单类：负责成绩的录入、排序和平均分计算
 */
public class Transcript {
    private Map<String, Integer> courseScores; // 存储科目和成绩的映射

    public Transcript() {
        courseScores = new HashMap<>();
    }

    /**
     * 添加成绩，校验百分制有效性
     */
    public void addCourseScore(String courseName, int score) {
        if (score >= 0 && score <= 100) {
            courseScores.put(courseName, score);
        } else {
            System.out.println("无效的成绩：" + courseName + " - " + score + "。成绩必须在0-100之间。");
        }
    }

    /**
     * 计算所有有效科目的平均分
     */
    public double calculateAverageScore() {
        if (courseScores.isEmpty()) {
            return 0.0;
        }
        int totalScore = 0;
        for (int score : courseScores.values()) {
            totalScore += score;
        }
        return (double) totalScore / courseScores.size();
    }

    /**
     * 按成绩从大到小排序并输出
     */
    public void printSortedScores() {
        List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(courseScores.entrySet());

        // 使用自定义比较器进行降序排序
        Collections.sort(sortedList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        System.out.println("各科成绩排序：");
        for (Map.Entry<String, Integer> entry : sortedList) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    /**
     * 从控制台自由输入成绩
     */
    public void inputScoresFromConsole() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入各科成绩 (例如: Java 95, 输入 'end' 结束):");
        while (true) {
            System.out.print("科目和成绩: ");
            String line = scanner.nextLine();
            if (line.equalsIgnoreCase("end")) {
                break;
            }
            String[] parts = line.split("\\s+");
            if (parts.length == 2) {
                try {
                    String courseName = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    addCourseScore(courseName, score);
                } catch (NumberFormatException e) {
                    System.out.println("输入格式错误，请重新输入。例如: Java 95");
                }
            } else {
                System.out.println("输入格式错误，请重新输入。例如: Java 95");
            }
        }
    }
}
