/**
 * 个人代码签名：黄昭展
 * AI Coding 工具：Manus AI (Version: 2026-05-08)
 */
package com.example.project1;

import java.util.Scanner;
/**
 * 实验1测试类：用户输入身份证号并输出结果
 */

public class TestPersonalInfo {
    public TestPersonalInfo() {
    }

    public static void main(String[] var0) {
        Scanner var1 = new Scanner(System.in);
        System.out.print("请输入您的身份证号码: ");
        String var2 = var1.nextLine();
        PersonalInfo var3 = new PersonalInfo(var2);
        var3.printInfo();
        var1.close();
    }
}
