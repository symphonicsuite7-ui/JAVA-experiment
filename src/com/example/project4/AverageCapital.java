/**
 * 个人代码签名：黄昭展
 * AI Coding 工具：Manus AI (Version: 2026-05-15)
 */
package com.example.project4;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 等额本金计算类：每月还款本金固定，利息递减
 */
public class AverageCapital {
    private BigDecimal loanMoney; // 贷款本金
    private BigDecimal yearRate;  // 年利率
    private int totalMonth;       // 还款总月数

    public AverageCapital(BigDecimal loanMoney, BigDecimal yearRate, int totalMonth) {
        this.loanMoney = loanMoney;
        this.yearRate = yearRate;
        this.totalMonth = totalMonth;
    }

    /**
     * 计算每月应还本金（固定值）
     */
    public BigDecimal calculateMonthlyPrincipal() {
        return loanMoney.divide(new BigDecimal(totalMonth), 2, RoundingMode.HALF_UP);
    }

    /**
     * 计算指定月份应还利息
     */
    public BigDecimal calculateMonthlyInterest(int monthIndex) {
        BigDecimal monthRate = yearRate.divide(new BigDecimal(12), 10, RoundingMode.HALF_UP);
        BigDecimal repaidPrincipal = calculateMonthlyPrincipal().multiply(new BigDecimal(monthIndex - 1));
        BigDecimal remainingPrincipal = loanMoney.subtract(repaidPrincipal);
        return remainingPrincipal.multiply(monthRate).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 计算指定月份总还款额
     */
    public BigDecimal calculateMonthlyPayment(int monthIndex) {
        return calculateMonthlyPrincipal().add(calculateMonthlyInterest(monthIndex)).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 计算总利息
     */
    public BigDecimal calculateTotalInterest() {
        BigDecimal totalInterest = BigDecimal.ZERO;
        for (int i = 1; i <= totalMonth; i++) {
            totalInterest = totalInterest.add(calculateMonthlyInterest(i));
        }
        return totalInterest.setScale(2, RoundingMode.HALF_UP);
    }
}
