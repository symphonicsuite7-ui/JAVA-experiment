/**
 * 个人代码签名：黄昭展
 * AI Coding 工具：Manus AI (Version: 2026-05-15)
 *
 * ============================================
 * 贷款计算器 - 图形界面主程序
 * ============================================
 * 基于 Swing 的 GUI 应用程序，提供两种还款方式的计算：
 *   1. 等额本息（每月还款额固定）
 *   2. 等额本金（每月还款本金固定，利息递减）
 *
 * 运行方式：直接运行 main 方法即可启动
 */
package com.example.project4;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 贷款计算器 GUI 主窗口类
 *
 * 继承自 JFrame，作为程序的主窗口。
 * 包含输入面板（贷款金额、利率、年限、还款方式）、
 * 结果展示区域和计算按钮。
 */
public class LoanCalculatorGUI extends JFrame {

    // ─────────────────────────────────────────────
    // GUI 组件（成员变量）
    // ─────────────────────────────────────────────

    /** 贷款本金输入框（单位：万元） */
    private JTextField loanMoneyField;

    /** 年利率输入框（单位：%） */
    private JTextField yearRateField;

    /** 贷款年限输入框（单位：年） */
    private JTextField loanYearsField;

    /** 等额本息 单选框 */
    private JRadioButton avgCapitalPlusInterestRadio;

    /** 等额本金 单选框 */
    private JRadioButton avgCapitalRadio;

    /** 结果显示区域（只读文本框） */
    private JTextArea resultArea;

    // ─────────────────────────────────────────────
    // 构造方法：初始化窗口和所有组件
    // ─────────────────────────────────────────────

    /**
     * 构造贷款计算器窗口
     *
     * 整体布局：
     *   ┌──────────────────────────────┐
     *   │      贷款信息录入（输入面板）     │  ← BorderLayout.NORTH
     *   ├──────────────────────────────┤
     *   │                              │
     *   │      计算结果（滚动文本框）      │  ← BorderLayout.CENTER
     *   │                              │
     *   ├──────────────────────────────┤
     *   │         [开始计算]            │  ← BorderLayout.SOUTH
     *   └──────────────────────────────┘
     */
    public LoanCalculatorGUI() {
        setTitle("贷款计算器 - 设计者：黄昭展");  // 窗口标题
        setSize(600, 450);                     // 窗口尺寸（宽×高）
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 点击关闭时退出程序
        setLocationRelativeTo(null);           // 窗口居中显示
        setLayout(new BorderLayout());         // 使用边界布局管理器

        // ── 1. 输入面板 ──
        // 使用 GridLayout(4行, 2列, 水平间距5px, 垂直间距5px)
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("贷款信息录入"));

        // 第1行：贷款本金
        inputPanel.add(new JLabel("  贷款本金 (万元): "));
        loanMoneyField = new JTextField("100");   // 默认值100万元
        inputPanel.add(loanMoneyField);

        // 第2行：年利率
        inputPanel.add(new JLabel("  年利率 (%): "));
        yearRateField = new JTextField("3.6");    // 默认值3.6%
        inputPanel.add(yearRateField);

        // 第3行：贷款年限
        inputPanel.add(new JLabel("  贷款年限 (年): "));
        loanYearsField = new JTextField("20");    // 默认值20年
        inputPanel.add(loanYearsField);

        // 第4行：还款方式（单选按钮）
        inputPanel.add(new JLabel("  还款方式: "));
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));  // 左对齐排列
        ButtonGroup repaymentMethodGroup = new ButtonGroup();  // 按钮组：保证只能选其一
        avgCapitalPlusInterestRadio = new JRadioButton("等额本息", true);  // 默认选中
        avgCapitalRadio = new JRadioButton("等额本金");
        repaymentMethodGroup.add(avgCapitalPlusInterestRadio);
        repaymentMethodGroup.add(avgCapitalRadio);
        radioPanel.add(avgCapitalPlusInterestRadio);
        radioPanel.add(avgCapitalRadio);
        inputPanel.add(radioPanel);

        add(inputPanel, BorderLayout.NORTH);  // 输入面板放在窗口上方

        // ── 2. 结果显示区域 ──
        resultArea = new JTextArea();
        resultArea.setEditable(false);                                          // 只读
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 13));             // 等宽字体，对齐数字
        JScrollPane scrollPane = new JScrollPane(resultArea);                   // 支持滚动
        add(scrollPane, BorderLayout.CENTER);   // 结果区域放在窗口中间

        // ── 3. 计算按钮 ──
        JButton calculateButton = new JButton("开始计算");
        // 点击按钮时触发 calculateLoan() 方法（Lambda 表达式写法）
        calculateButton.addActionListener(e -> calculateLoan());
        add(calculateButton, BorderLayout.SOUTH);  // 按钮放在窗口下方
    }

    private void calculateLoan() {
        try {
            // ── 1. 读取并转换输入数据 ──
            // 本金：万元 → 元（乘以10000）
            BigDecimal loanMoney = new BigDecimal(loanMoneyField.getText())
                    .multiply(new BigDecimal("10000"));

            // 年利率：百分比 → 小数（除以100），保留10位小数精度
            BigDecimal yearRate = new BigDecimal(yearRateField.getText())
                    .divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP);

            // 年限 → 总月数
            int loanYears = Integer.parseInt(loanYearsField.getText());
            int totalMonth = loanYears * 12;

            // ── 2. 组装结果字符串 ──
            StringBuilder result = new StringBuilder();
            result.append("AI 工具: Manus AI (Version: 2026-05-15)\n");
            result.append("代码签名: 黄昭展\n");
            result.append("--------------------------------------------------\n");

            // ── 3. 根据选择的还款方式分别计算 ──
            if (avgCapitalPlusInterestRadio.isSelected()) {
                // ── 等额本息 ──
                AverageCapitalPlusInterest acpi = new AverageCapitalPlusInterest(loanMoney, yearRate, totalMonth);
                BigDecimal monthlyPayment = acpi.calculateMonthlyPayment();   // 月供
                BigDecimal totalInterest = acpi.calculateTotalInterest();     // 总利息

                result.append("【等额本息还款方式】\n");
                result.append("每月固定还款额: ").append(monthlyPayment).append(" 元\n");
                result.append("累计总利息: ").append(totalInterest).append(" 元\n");
                result.append("\n前5个月还款明细:\n");
                // 显示前5个月的逐月明细（或总月数，取较小值）
                for (int i = 1; i <= Math.min(totalMonth, 5); i++) {
                    BigDecimal principal = acpi.calculateMonthlyPrincipal(i);   // 当月本金
                    BigDecimal interest = acpi.calculateMonthlyInterest(i);     // 当月利息
                    result.append(String.format("第 %d 月: 本金 %s, 利息 %s, 总计 %s\n",
                            i, principal, interest, principal.add(interest)));
                }

            } else {
                // ── 等额本金 ──
                AverageCapital ac = new AverageCapital(loanMoney, yearRate, totalMonth);
                BigDecimal totalInterest = ac.calculateTotalInterest();  // 总利息

                result.append("【等额本金还款方式】\n");
                result.append("累计总利息: ").append(totalInterest).append(" 元\n");
                result.append("\n前5个月还款明细 (每月递减):\n");
                // 显示前5个月的逐月明细
                for (int i = 1; i <= Math.min(totalMonth, 5); i++) {
                    BigDecimal monthlyPrincipal = ac.calculateMonthlyPrincipal();  // 固定本金
                    BigDecimal monthlyInterest = ac.calculateMonthlyInterest(i);   // 当月利息
                    BigDecimal monthlyPayment = ac.calculateMonthlyPayment(i);    // 当月总额
                    result.append(String.format("第 %d 月: 本金 %s, 利息 %s, 总计 %s\n",
                            i, monthlyPrincipal, monthlyInterest, monthlyPayment));
                }
            }

            // 将计算结果显示到文本区域
            resultArea.setText(result.toString());

        } catch (Exception ex) {
            // 任何输入解析错误（数字格式错误等）都会弹出提示框
            JOptionPane.showMessageDialog(
                    this,
                    "输入数据有误，请检查！",
                    "错误",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ─────────────────────────────────────────────
    // 程序入口
    // ─────────────────────────────────────────────

    /**
     * 程序入口 main 方法
     *
     * 使用 SwingUtilities.invokeLater() 确保 GUI 创建在
     * 事件分发线程（EDT）中执行，这是 Swing 程序的规范写法，
     * 可以避免线程安全问题。
     */
    public static void main(String[] args) {
        // 在事件分发线程中启动 GUI
        SwingUtilities.invokeLater(() -> new LoanCalculatorGUI().setVisible(true));
    }
}
