/**
 * 个人代码签名：黄昭展
 * AI Coding 工具：Manus AI (Version: 2026-05-15)
 */
package com.example.project4;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *   月供 = [本金 × 月利率 × (1 + 月利率)^n] / [(1 + 月利率)^n - 1]
 *   n = 还款总月数
 *   月利率 = 年利率 ÷ 12
 */
public class AverageCapitalPlusInterest {
    private BigDecimal loanMoney;
    private BigDecimal yearRate;
    private int totalMonth;

    public AverageCapitalPlusInterest(BigDecimal loanMoney, BigDecimal yearRate, int totalMonth) {
        this.loanMoney = loanMoney;
        this.yearRate = yearRate;
        this.totalMonth = totalMonth;
    }

    /**

     *   设月利率为 r，总月数为 n，本金为 P
     *   月供 M = [P × r × (1+r)^n] / [(1+r)^n - 1]
     *
     * 步骤：
     *   1. 年利率 ÷ 12 → 月利率 r
     *   2. 计算 (1 + r)^n
     *   3. 代入公式计算月供
     */
    public BigDecimal calculateMonthlyPayment() {
        // 1. 年利率 ÷ 12 = 月利率（保留10位小数保证精度）
        BigDecimal monthRate = yearRate.divide(new BigDecimal(12), 10, RoundingMode.HALF_UP);

        // 2. 计算 (1 + 月利率)^总月数，即复利因子
        BigDecimal pow = monthRate.add(BigDecimal.ONE).pow(totalMonth);

        // 3. 分子 = 本金 × 月利率 × 复利因子
        BigDecimal numerator = loanMoney.multiply(monthRate).multiply(pow);

        // 4. 分母 = 复利因子 - 1
        BigDecimal denominator = pow.subtract(BigDecimal.ONE);

        // 防除零：如果分母为0（理论上不会发生），返回0
        if (denominator.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;

        // 5. 月供 = 分子 ÷ 分母（保留两位小数，四舍五入）
        return numerator.divide(denominator, 2, RoundingMode.HALF_UP);
    }

    /**
     * 公式：总利息 = 月供 × 总月数 - 本金
     */
    public BigDecimal calculateTotalInterest() {
        BigDecimal monthlyPayment = calculateMonthlyPayment();          // 每月固定还款额
        BigDecimal totalPayment = monthlyPayment.multiply(new BigDecimal(totalMonth));  // 还款总额
        return totalPayment.subtract(loanMoney).setScale(2, RoundingMode.HALF_UP);      // 总利息 = 总额 - 本金
    }

    /**
     *   每月还款额 = 当月利息 + 当月本金
     *   当月利息 = 剩余本金 × 月利率
     *   当月本金 = 月供 - 当月利息
     */
    public BigDecimal calculateMonthlyPrincipal(int monthIndex) {
        BigDecimal monthRate = yearRate.divide(new BigDecimal(12), 10, RoundingMode.HALF_UP);
        BigDecimal monthlyPayment = calculateMonthlyPayment();  // 固定月供
        BigDecimal remainingPrincipal = loanMoney;              // 剩余本金（初始为全部本金）
        BigDecimal principalThisMonth = BigDecimal.ZERO;

        // 逐月模拟：从第1个月循环到目标月份，每次扣除当月本金
        for (int i = 1; i <= monthIndex; i++) {
            // 当月利息 = 剩余本金 × 月利率
            BigDecimal interestThisMonth = remainingPrincipal.multiply(monthRate).setScale(2, RoundingMode.HALF_UP);
            // 当月本金 = 月供 - 当月利息
            principalThisMonth = monthlyPayment.subtract(interestThisMonth);
            // 更新剩余本金（减去已还本金）
            remainingPrincipal = remainingPrincipal.subtract(principalThisMonth);
        }
        return principalThisMonth.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateMonthlyInterest(int monthIndex) {
        BigDecimal monthRate = yearRate.divide(new BigDecimal(12), 10, RoundingMode.HALF_UP);
        BigDecimal monthlyPayment = calculateMonthlyPayment();
        BigDecimal remainingPrincipal = loanMoney;
        BigDecimal interestThisMonth = BigDecimal.ZERO;

        // 逐月模拟到目标月份
        for (int i = 1; i <= monthIndex; i++) {
            // 当月利息 = 剩余本金 × 月利率
            interestThisMonth = remainingPrincipal.multiply(monthRate).setScale(2, RoundingMode.HALF_UP);
            // 当月本金 = 月供 - 当月利息
            BigDecimal principalThisMonth = monthlyPayment.subtract(interestThisMonth);
            // 扣除当月本金，得到新的剩余本金
            remainingPrincipal = remainingPrincipal.subtract(principalThisMonth);
        }
        return interestThisMonth.setScale(2, RoundingMode.HALF_UP);
    }
}
