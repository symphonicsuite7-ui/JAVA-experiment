/**
 * 个人代码签名：黄昭展
 * AI Coding 工具：Manus AI (Version: 2026-05-08)
 */
package com.example.project2;
import java.util.*;

public class TestTranscript {
    public TestTranscript() {
    }

    public static void main(String[] var0) {
        Transcript var1 = new Transcript();
        var1.inputScoresFromConsole();
        var1.printSortedScores();
        System.out.println("平均分: " + var1.calculateAverageScore());
    }
}
