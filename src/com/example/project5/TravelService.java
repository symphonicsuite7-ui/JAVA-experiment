/**
 * 个人代码签名：黄昭展
 * AI Coding 工具：Manus AI (Version: 2026-05-15)
 */
package com.example.project5;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * 出行服务类：根据日期和车牌号判断限行
 */
public class TravelService {

    /**
     * 判断当前系统日期是否为奇数日
     */
    public boolean isOddDay() {
        LocalDate today = LocalDate.now();
        return today.getDayOfMonth() % 2 != 0;
    }

    /**
     * 从车牌号字符串中提取最后一位数字
     */
    public int getLastDigitOfLicensePlate(String licensePlate) {
        if (licensePlate == null || licensePlate.isEmpty()) return -1;
        // 从后往前遍历找到第一个数字
        for (int i = licensePlate.length() - 1; i >= 0; i--) {
            char c = licensePlate.charAt(i);
            if (Character.isDigit(c)) {
                return Character.getNumericValue(c);
            }
        }
        return -1;
    }

    /**
     * 校验通行权限的核心逻辑
     */
    public String checkPassage(String licensePlate) {
        boolean isTodayOdd = isOddDay();
        int lastDigit = getLastDigitOfLicensePlate(licensePlate);

        if (lastDigit == -1) return "车牌号中未包含数字，无法识别。";

        boolean isLicensePlateOdd = (lastDigit % 2 != 0);

        // 匹配逻辑：奇数日允许奇数车牌，偶数日允许偶数车牌
        if (isTodayOdd == isLicensePlateOdd) {
            return "今天是" + (isTodayOdd ? "奇数" : "偶数") + "日，您的车牌符合规则，允许通行。";
        } else {
            return "抱歉，今日限行规则为" + (isTodayOdd ? "单号" : "双号") + "通行。您的车辆不允许通行，否则扣3分，罚款200元。";
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TravelService service = new TravelService();

        System.out.println("=== 城市高速环线出行检查系统 (签名: 黄昭展) ===");
        System.out.print("请输入您的车牌号: ");
        String licensePlate = scanner.nextLine();

        System.out.println(service.checkPassage(licensePlate));
        scanner.close();
    }
}
