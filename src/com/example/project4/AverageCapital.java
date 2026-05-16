/**
 * 个人代码签名：黄昭展
 * AI Coding 工具：Manus AI (Version: 2026-05-15)
 */
package com.example.project4;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 等额本金计算类
 *
 * 核心公式：
 *
 *   每月应还本金 = 本金 ÷ 总月数（固定值）
 *   每月利息     = 剩余本金 × 月利率
 *   每月还款额   = 每月应还本金 + 每月利息（逐月递减）
 *
 * 其中：
 *   月利率 = 年利率 ÷ 12
 *   第 n 个月的剩余本金 = 本金 - (每月应还本金 × (n-1))
 */
public class AverageCapital {
    private BigDecimal loanMoney;
    private BigDecimal yearRate;
    private int totalMonth;
    /**
     * @param loanMoney  贷款本金（元）
     * @param yearRate   年利率（如 0.036 代表 3.6%）
     * @param totalMonth 还款总月数
     */
    public AverageCapital(BigDecimal loanMoney, BigDecimal yearRate, int totalMonth) {
        this.loanMoney = loanMoney;
        this.yearRate = yearRate;
        this.totalMonth = totalMonth;
    }
    /**
     * 公式：每月本金 = 贷款总额 ÷ 总月数
     */
    public BigDecimal calculateMonthlyPrincipal() {
        return loanMoney.divide(new BigDecimal(totalMonth), 2, RoundingMode.HALF_UP);
    }
    /**
     *   第 n 个月的剩余本金 = 总本金 - 已还本金
     *                      = 总本金 - (每月本金 × (n-1))
     *   第 n 个月的利息 = 剩余本金 × 月利率
     */
    public BigDecimal calculateMonthlyInterest(int monthIndex) {
        // 年利率 ÷ 12 = 月利率（保留10位小数保证精度）
        BigDecimal monthRate = yearRate.divide(new BigDecimal(12), 10, RoundingMode.HALF_UP);
        // 已还本金 = 每月固定本金 × (已过月数)
        // 注意：monthIndex 月时，已过去 monthIndex - 1 个月
        BigDecimal repaidPrincipal = calculateMonthlyPrincipal().multiply(new BigDecimal(monthIndex - 1));
        // 剩余本金 = 总本金 - 已还本金
        BigDecimal remainingPrincipal = loanMoney.subtract(repaidPrincipal);
        // 当月利息 = 剩余本金 × 月利率
        return remainingPrincipal.multiply(monthRate).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 公式：月还款额 = 固定本金 + 当月利息
     */
    public BigDecimal calculateMonthlyPayment(int monthIndex) {
        return calculateMonthlyPrincipal()
                .add(calculateMonthlyInterest(monthIndex))
                .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 公式：总利息 = 第1月利息 + 第2月利息 + ... + 第n月利息
     */
    public BigDecimal calculateTotalInterest() {
        BigDecimal totalInterest = BigDecimal.ZERO;
        // 逐月累加利息
        for (int i = 1; i <= totalMonth; i++) {
            totalInterest = totalInterest.add(calculateMonthlyInterest(i));
        }
        return totalInterest.setScale(2, RoundingMode.HALF_UP);
    }
}
