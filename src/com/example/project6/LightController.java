/**
 * 个人代码签名：黄昭展
 * AI Coding 工具：Manus AI (Version: 2026-05-15)
 */
package com.example.project6;

/**
 * 信号灯控制系统类：通过线程定时切换红绿灯
 */
public class LightController extends Thread {
    private Lighter nsLighter; // 南北向灯
    private Lighter ewLighter; // 东西向灯
    private long duration = 5000; // 切换间隔 5秒

    public LightController(Lighter nsLighter, Lighter ewLighter) {
        this.nsLighter = nsLighter;
        this.ewLighter = ewLighter;
        // 初始状态
        this.nsLighter.turnGreen();
        this.ewLighter.turnRed();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(duration);
                // 逻辑：交替切换两个方向的状态
                if (nsLighter.isGreen()) {
                    nsLighter.turnRed();
                    ewLighter.turnGreen();
                } else {
                    ewLighter.turnRed();
                    nsLighter.turnGreen();
                }
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
