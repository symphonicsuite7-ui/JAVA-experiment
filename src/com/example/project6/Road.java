/**
 * 个人代码签名：黄昭展
 * AI Coding 工具：Manus AI (Version: 2026-05-15)
 */
package com.example.project6;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 道路类：管理车道上的车辆队列
 */
public class Road {
    private String name;
    private Queue<String> cars; // 使用队列模拟车辆排队

    public Road(String name) {
        this.name = name;
        this.cars = new LinkedList<>();
    }

    public void addCar(String carId) {
        cars.offer(carId);
        System.out.println("车辆 " + carId + " 进入 " + name);
    }

    public String removeCar() {
        return cars.poll();
    }

    public boolean hasCar() {
        return !cars.isEmpty();
    }

    public String getName() {
        return name;
    }
}
