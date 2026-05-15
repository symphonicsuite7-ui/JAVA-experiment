/**
 * 个人代码签名：黄昭展
 * AI Coding 工具：Manus AI (Version: 2026-05-15)
 */
package com.example.project6;

import java.util.Random;

/**
 * 交通管理系统主类：模拟十字路口的运行逻辑
 */
public class TrafficSystem {

    public static void main(String[] args) {
        System.out.println("=== 交通灯管理模拟系统 (签名: 黄昭展) ===");
        
        // 1. 初始化组件
        Lighter nsLighter = new Lighter("南北向");
        Lighter ewLighter = new Lighter("东西向");

        Road nsRoad = new Road("南北直行道");
        Road ewRoad = new Road("东西直行道");
        Road leftRoad = new Road("左转车道");

        // 2. 启动信号灯控制线程
        LightController controller = new LightController(nsLighter, ewLighter);
        controller.setDaemon(true); // 设置为守护线程
        controller.start();

        Random random = new Random();
        int carCounter = 1;

        // 3. 循环模拟车辆通行
        while (true) {
            try {
                // 模拟随机生成车辆进入车道
                if (random.nextInt(10) < 6) {
                    String carId = "Car-" + carCounter++;
                    int r = random.nextInt(3);
                    if (r == 0) nsRoad.addCar(carId);
                    else if (r == 1) ewRoad.addCar(carId);
                    else leftRoad.addCar(carId);
                }

                // 根据信号灯状态放行车辆
                if (nsLighter.isGreen()) {
                    if (nsRoad.hasCar()) {
                        System.out.println(">>> 放行: " + nsRoad.removeCar() + " 通过南北路口");
                    }
                } else if (ewLighter.isGreen()) {
                    if (ewRoad.hasCar()) {
                        System.out.println(">>> 放行: " + ewRoad.removeCar() + " 通过东西路口");
                    }
                    // 模拟东西向绿灯时允许左转
                    if (leftRoad.hasCar()) {
                        System.out.println(">>> 左转: " + leftRoad.removeCar() + " 通过路口");
                    }
                }

                // 模拟右转车辆（不受信号灯控制）
                if (random.nextInt(10) < 2) {
                    System.out.println(">>> 右转: Car-R" + random.nextInt(100) + " 直接通过 (不受控)");
                }

                Thread.sleep(800); // 模拟时间流逝

            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
