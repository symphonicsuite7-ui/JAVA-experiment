/**
 * 个人代码签名：黄昭展
 * AI Coding 工具：Manus AI (Version: 2026-05-15)
 *
 * ============================================
 * 等额本金 还款方式计算类
 * ============================================
 * 等额本金是指在还款期内，把贷款总额等分，每月偿还相同数额的本金，
 * 以及剩余贷款在该月产生的利息。
 *
 * 特点：
 *   - 每月还款总额逐月递减（因为利息逐月减少）
 *   - 每月偿还的本金固定不变
 *   - 总利息比等额本息少
 *   - 前期还款压力较大
 *
 * 适用人群：收入较高或有提前还款计划的借款人
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
    public AverageCapital(BigDecimal loanMoney, BigDecimal yearRate, int totalMonth) {
        this.loanMoney = loanMoney;
        this.yearRate = yearRate;
        this.totalMonth = totalMonth;
    }

    // ─────────────────────────────────────────────
    // 核心计算方法
    // ─────────────────────────────────────────────

    /**
     * 计算每月应还本金（固定值）
     *
     * 公式：每月本金 = 贷款总额 ÷ 总月数
     *
     * 等额本金的核心特征：每月偿还的本金金额是固定的，
     * 不会随月份变化。
     *
     * @return 每月固定偿还的本金（保留两位小数）
     */
    public BigDecimal calculateMonthlyPrincipal() {
        return loanMoney.divide(new BigDecimal(totalMonth), 2, RoundingMode.HALF_UP);
    }

    /**
     * 计算指定月份的应还利息
     *
     * 推导：
     *   第 n 个月的剩余本金 = 总本金 - 已还本金
     *                      = 总本金 - (每月本金 × (n-1))
     *
     *   第 n 个月的利息 = 剩余本金 × 月利率
     *
     * 由于剩余本金逐月减少，利息也逐月减少。
     *
     * @param monthIndex 第几个月（从 1 开始）
     * @return 该月应还的利息
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
     * 计算指定月份的总还款额
     *
     * 公式：月还款额 = 固定本金 + 当月利息
     *
     * 因为每月本金固定，利息递减，所以每月还款额逐月递减。
     *
     * @param monthIndex 第几个月（从 1 开始）
     * @return 该月应还总金额（本金 + 利息）
     */
    public BigDecimal calculateMonthlyPayment(int monthIndex) {
        return calculateMonthlyPrincipal()
                .add(calculateMonthlyInterest(monthIndex))
                .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 计算总利息
     *
     * 公式：总利息 = 第1月利息 + 第2月利息 + ... + 第n月利息
     *
     * 即逐月累加每个月的利息金额。
     * 由于等额本金每月利息是等差数列递减，
     * 也可以用等差数列求和公式计算，但这里用循环累加更直观。
     *
     * @return 总利息（保留两位小数）
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
