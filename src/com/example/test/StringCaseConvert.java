package com.example.test;

import java.util.Scanner;

public class StringCaseConvert {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入一个字符串：");
        String input = scanner.nextLine();
        scanner.close();

        // 边界处理：空串/空输入直接返回
        if (input == null || input.isEmpty()) {
            System.out.println("输入字符串为空");
            return;
        }
        String result = convertCase(input);
        System.out.println("转换后：" + result);
    }
    /**
     * 自定义方法：首字母转大写，其余字母转小写，不调用库方法
     * @param str 原始字符串
     * @return 转换后的字符串
     */
    private static String convertCase(String str) {
        char[] chars = str.toCharArray();
        // 1. 处理首字母：小写转大写
        if (isLowerCaseLetter(chars[0])) {
            chars[0] = (char) (chars[0] - 32); // 小写字母ASCII值比对应大写大32
        }
        // 2. 处理其余字符：大写转小写
        for (int i = 1; i < chars.length; i++) {
            if (isUpperCaseLetter(chars[i])) {
                chars[i] = (char) (chars[i] + 32);
            }
        }
        return new String(chars);
    }
    /**
     * 判断字符是否为小写英文字母（a-z）
     */
    private static boolean isLowerCaseLetter(char c) {
        return c >= 'a' && c <= 'z';
    }
    /**
     * 判断字符是否为大写英文字母（A-Z）
     */
    private static boolean isUpperCaseLetter(char c) {
        return c >= 'A' && c <= 'Z';
    }
}