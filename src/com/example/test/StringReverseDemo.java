package com.example.test;

import java.util.Scanner;

public class StringReverseDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入要反转的字符串（输入exit结束）：");

        // 循环读取输入，直到输入exit
        while (true) {
            String input = scanner.nextLine();
            // 退出条件
            if ("exit".equalsIgnoreCase(input)) {
                System.out.println("程序结束");
                break;
            }

            // 调用自定义反转方法
            String reversed = reverseStringWithStringBuilder(input);
            System.out.println("反转前：" + input);
            System.out.println("反转后：" + reversed);
            System.out.println("------------------------");
        }
        scanner.close();
    }

    /**
     * 自定义方法：使用StringBuilder实现字符串反转，不调用JDK自带的reverse()
     * @param str 待反转的原始字符串
     * @return 反转后的字符串
     */
    private static String reverseStringWithStringBuilder(String str) {
        // 边界处理：空串或null直接返回
        if (str == null || str.isEmpty()) {
            return str;
        }

        // 创建StringBuilder对象，初始容量为字符串长度，避免扩容开销
        StringBuilder sb = new StringBuilder(str.length());

        // 从字符串末尾向前遍历，逐个字符追加到StringBuilder中
        for (int i = str.length() - 1; i >= 0; i--) {
            sb.append(str.charAt(i));
        }

        // 转换为String并返回
        return sb.toString();
    }
}

