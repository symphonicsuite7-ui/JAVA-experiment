/**
 * 个人代码签名：黄昭展
 * AI Coding 工具：Manus AI (Version: 2026-05-15)
 */
package com.example.project4;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 贷款计算器 GUI 类：提供用户交互界面
 */
public class LoanCalculatorGUI extends JFrame {

    private JTextField loanMoneyField;
    private JTextField yearRateField;
    private JTextField loanYearsField;
    private JRadioButton avgCapitalPlusInterestRadio;
    private JRadioButton avgCapitalRadio;
    private JTextArea resultArea;

    public LoanCalculatorGUI() {
        setTitle("贷款计算器 - 设计者：黄昭展");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 输入面板：使用网格布局排列输入框
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("贷款信息录入"));

        inputPanel.add(new JLabel("  贷款本金 (万元): "));
        loanMoneyField = new JTextField("100");
        inputPanel.add(loanMoneyField);

        inputPanel.add(new JLabel("  年利率 (%): "));
        yearRateField = new JTextField("3.6");
        inputPanel.add(yearRateField);

        inputPanel.add(new JLabel("  贷款年限 (年): "));
        loanYearsField = new JTextField("20");
        inputPanel.add(loanYearsField);

        inputPanel.add(new JLabel("  还款方式: "));
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ButtonGroup repaymentMethodGroup = new ButtonGroup();
        avgCapitalPlusInterestRadio = new JRadioButton("等额本息", true);
        avgCapitalRadio = new JRadioButton("等额本金");
        repaymentMethodGroup.add(avgCapitalPlusInterestRadio);
        repaymentMethodGroup.add(avgCapitalRadio);
        radioPanel.add(avgCapitalPlusInterestRadio);
        radioPanel.add(avgCapitalRadio);
        inputPanel.add(radioPanel);

        add(inputPanel, BorderLayout.NORTH);

        // 结果显示区域
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.CENTER);

        // 计算按钮
        JButton calculateButton = new JButton("开始计算");
        calculateButton.addActionListener(e -> calculateLoan());
        add(calculateButton, BorderLayout.SOUTH);
    }

    /**
     * 执行贷款计算逻辑并更新UI
     */
    private void calculateLoan() {
        try {
            // 数据转换：万元转元，百分比转小数
            BigDecimal loanMoney = new BigDecimal(loanMoneyField.getText()).multiply(new BigDecimal("10000"));
            BigDecimal yearRate = new BigDecimal(yearRateField.getText()).divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP);
            int loanYears = Integer.parseInt(loanYearsField.getText());
            int totalMonth = loanYears * 12;

            StringBuilder result = new StringBuilder();
            result.append("AI 工具: Manus AI (Version: 2026-05-15)\n");
            result.append("代码签名: 黄昭展\n");
            result.append("--------------------------------------------------\n");

            if (avgCapitalPlusInterestRadio.isSelected()) {
                AverageCapitalPlusInterest acpi = new AverageCapitalPlusInterest(loanMoney, yearRate, totalMonth);
                BigDecimal monthlyPayment = acpi.calculateMonthlyPayment();
                BigDecimal totalInterest = acpi.calculateTotalInterest();

                result.append("【等额本息还款方式】\n");
                result.append("每月固定还款额: ").append(monthlyPayment).append(" 元\n");
                result.append("累计总利息: ").append(totalInterest).append(" 元\n");
                result.append("\n前5个月还款明细:\n");
                for (int i = 1; i <= Math.min(totalMonth, 5); i++) {
                    BigDecimal principal = acpi.calculateMonthlyPrincipal(i);
                    BigDecimal interest = acpi.calculateMonthlyInterest(i);
                    result.append(String.format("第 %d 月: 本金 %s, 利息 %s, 总计 %s\n", i, principal, interest, principal.add(interest)));
                }

            } else {
                AverageCapital ac = new AverageCapital(loanMoney, yearRate, totalMonth);
                BigDecimal totalInterest = ac.calculateTotalInterest();

                result.append("【等额本金还款方式】\n");
                result.append("累计总利息: ").append(totalInterest).append(" 元\n");
                result.append("\n前5个月还款明细 (每月递减):\n");
                for (int i = 1; i <= Math.min(totalMonth, 5); i++) {
                    BigDecimal monthlyPrincipal = ac.calculateMonthlyPrincipal();
                    BigDecimal monthlyInterest = ac.calculateMonthlyInterest(i);
                    BigDecimal monthlyPayment = ac.calculateMonthlyPayment(i);
                    result.append(String.format("第 %d 月: 本金 %s, 利息 %s, 总计 %s\n", i, monthlyPrincipal, monthlyInterest, monthlyPayment));
                }
            }
            resultArea.setText(result.toString());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "输入数据有误，请检查！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoanCalculatorGUI().setVisible(true));
    }
}
