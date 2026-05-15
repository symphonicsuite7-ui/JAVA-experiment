/**
 * 个人代码签名：黄昭展
 * AI Coding 工具：Manus AI (Version: 2026-05-15)
 */
package com.example.project4;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 等额本息计算类：每月还款额固定
 */
public class AverageCapitalPlusInterest {
    private BigDecimal loanMoney; // 贷款本金
    private BigDecimal yearRate;  // 年利率
    private int totalMonth;       // 还款总月数

    public AverageCapitalPlusInterest(BigDecimal loanMoney, BigDecimal yearRate, int totalMonth) {
        this.loanMoney = loanMoney;
        this.yearRate = yearRate;
        this.totalMonth = totalMonth;
    }

    /**
     * 计算每月还款额
     * 公式：月供 = [本金 * 月利率 * (1+月利率)^n] / [(1+月利率)^n - 1]
     */
    public BigDecimal calculateMonthlyPayment() {
        BigDecimal monthRate = yearRate.divide(new BigDecimal(12), 10, RoundingMode.HALF_UP);
        BigDecimal pow = monthRate.add(BigDecimal.ONE).pow(totalMonth);

        BigDecimal numerator = loanMoney.multiply(monthRate).multiply(pow);
        BigDecimal denominator = pow.subtract(BigDecimal.ONE);

        if (denominator.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;
        return numerator.divide(denominator, 2, RoundingMode.HALF_UP);
    }

    /**
     * 计算总利息
     */
    public BigDecimal calculateTotalInterest() {
        BigDecimal monthlyPayment = calculateMonthlyPayment();
        BigDecimal totalPayment = monthlyPayment.multiply(new BigDecimal(totalMonth));
        return totalPayment.subtract(loanMoney).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 计算指定月份的还款本金
     */
    public BigDecimal calculateMonthlyPrincipal(int monthIndex) {
        BigDecimal monthRate = yearRate.divide(new BigDecimal(12), 10, RoundingMode.HALF_UP);
        BigDecimal monthlyPayment = calculateMonthlyPayment();
        BigDecimal remainingPrincipal = loanMoney;
        BigDecimal principalThisMonth = BigDecimal.ZERO;

        for (int i = 1; i <= monthIndex; i++) {
            BigDecimal interestThisMonth = remainingPrincipal.multiply(monthRate).setScale(2, RoundingMode.HALF_UP);
            principalThisMonth = monthlyPayment.subtract(interestThisMonth);
            remainingPrincipal = remainingPrincipal.subtract(principalThisMonth);
        }
        return principalThisMonth.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 计算指定月份的还款利息
     */
    public BigDecimal calculateMonthlyInterest(int monthIndex) {
        BigDecimal monthRate = yearRate.divide(new BigDecimal(12), 10, RoundingMode.HALF_UP);
        BigDecimal monthlyPayment = calculateMonthlyPayment();
        BigDecimal remainingPrincipal = loanMoney;
        BigDecimal interestThisMonth = BigDecimal.ZERO;

        for (int i = 1; i <= monthIndex; i++) {
            interestThisMonth = remainingPrincipal.multiply(monthRate).setScale(2, RoundingMode.HALF_UP);
            BigDecimal principalThisMonth = monthlyPayment.subtract(interestThisMonth);
            remainingPrincipal = remainingPrincipal.subtract(principalThisMonth);
        }
        return interestThisMonth.setScale(2, RoundingMode.HALF_UP);
    }
}
