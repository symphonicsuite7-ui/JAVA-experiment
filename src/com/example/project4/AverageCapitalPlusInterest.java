/**
 * 个人代码签名：黄昭展
 * AI Coding 工具：Manus AI (Version: 2026-05-15)
 *
 * ============================================
 * 等额本息 还款方式计算类
 * ============================================
 * 等额本息是指在还款期内，每月偿还同等数额的贷款（包括本金和利息）。
 *
 * 特点：
 *   - 每月还款额固定，方便记忆和规划
 *   - 前期还款中利息占比大，本金占比小
 *   - 后期利息占比小，本金占比大
 *   - 总利息比等额本金方式多
 *
 * 适用人群：收入稳定的借款人
 */
package com.example.project4;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 等额本息计算类
 *
 * 核心公式（月供计算）：
 *
 *   月供 = [本金 × 月利率 × (1 + 月利率)^n] / [(1 + 月利率)^n - 1]
 *
 * 其中：
 *   n = 还款总月数
 *   月利率 = 年利率 ÷ 12
 */
public class AverageCapitalPlusInterest {

    // ─────────────────────────────────────────────
    // 成员变量
    // ─────────────────────────────────────────────

    /** 贷款本金（单位：元） */
    private BigDecimal loanMoney;

    /** 年利率（例如 0.036 表示 3.6%） */
    private BigDecimal yearRate;

    /** 还款总月数 = 贷款年限 × 12 */
    private int totalMonth;

    // ─────────────────────────────────────────────
    // 构造方法
    // ─────────────────────────────────────────────

    /**
     * @param loanMoney  贷款本金（元）
     * @param yearRate   年利率（如 0.036 代表 3.6%）
     * @param totalMonth 还款总月数
     */
    public AverageCapitalPlusInterest(BigDecimal loanMoney, BigDecimal yearRate, int totalMonth) {
        this.loanMoney = loanMoney;
        this.yearRate = yearRate;
        this.totalMonth = totalMonth;
    }

    // ─────────────────────────────────────────────
    // 核心计算方法
    // ─────────────────────────────────────────────

    /**
     * 计算每月固定还款额（月供）
     *
     * 推导过程：
     *   设月利率为 r，总月数为 n，本金为 P
     *   月供 M = [P × r × (1+r)^n] / [(1+r)^n - 1]
     *
     * 步骤：
     *   1. 年利率 ÷ 12 → 月利率 r
     *   2. 计算 (1 + r)^n
     *   3. 代入公式计算月供
     *
     * @return 每月应还金额（保留两位小数）
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
     * 计算总利息
     *
     * 公式：总利息 = 月供 × 总月数 - 本金
     *
     * @return 总利息（保留两位小数）
     */
    public BigDecimal calculateTotalInterest() {
        BigDecimal monthlyPayment = calculateMonthlyPayment();          // 每月固定还款额
        BigDecimal totalPayment = monthlyPayment.multiply(new BigDecimal(totalMonth));  // 还款总额
        return totalPayment.subtract(loanMoney).setScale(2, RoundingMode.HALF_UP);      // 总利息 = 总额 - 本金
    }

    /**
     * 计算指定月份中的 本金部分
     *
     * 原理：
     *   每月还款额 = 当月利息 + 当月本金
     *   当月利息 = 剩余本金 × 月利率
     *   当月本金 = 月供 - 当月利息
     *
     * 由于等额本息每月还款额固定，随着剩余本金逐月减少，
     * 每月利息递减，每月本金递增。
     *
     * @param monthIndex 第几个月（从 1 开始）
     * @return 该月偿还的本金金额
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

    /**
     * 计算指定月份中的 利息部分
     *
     * 原理同 calculateMonthlyPrincipal，只返回利息部分
     *
     * @param monthIndex 第几个月（从 1 开始）
     * @return 该月偿还的利息金额
     */
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
