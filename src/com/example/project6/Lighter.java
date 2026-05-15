/**
 * 个人代码签名：黄昭展
 * AI Coding 工具：Manus AI (Version: 2026-05-15)
 */
package com.example.project6;

/**
 * 信号灯类：表示路口的红绿灯状态
 */
public class Lighter {
    private boolean isGreen; // 状态：true为绿灯，false为红灯
    private String direction; // 方向名称

    public Lighter(String direction) {
        this.direction = direction;
        this.isGreen = false;
    }

    public void turnGreen() {
        isGreen = true;
        System.out.println("[信号灯] " + direction + " 变为绿色。");
    }

    public void turnRed() {
        isGreen = false;
        System.out.println("[信号灯] " + direction + " 变为红色。");
    }

    public boolean isGreen() {
        return isGreen;
    }
}
