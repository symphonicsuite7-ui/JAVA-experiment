/**
 * 个人代码签名：黄昭展
 * AI Coding 工具：Manus AI (Version: 2026-05-15)
 */
package com.example.project7.cellmachinejava;


import com.example.project7.Cell;
import com.example.project7.field.Field;

import javax.swing.*;
import java.util.ArrayList;

/**
 * 细胞模拟器测试类：生命游戏的主控制器
 */
public class CellMachine {
    public static void main(String[] args) {
        com.example.project7.field.Field field = new Field(30, 30);

        // 初始化：填充网格并随机激活部分细胞
        for (int row = 0; row < field.getHeight(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Cell cell = new Cell();
                field.setCell(row, col, cell);
                if (Math.random() < 0.2) { // 20% 初始激活率
                    cell.reborn();
                }
            }
        }

        // GUI 设置
        View view = new View(field);
        JFrame frame = new JFrame("生命游戏细胞模拟器 - 黄昭展");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(view);
        frame.pack();
        frame.setVisible(true);

        // 迭代演化循环
        for (int i = 0; i < 100; i++) {
            // 核心算法逻辑：
            // 1. 活细胞周围活邻居 < 2 或 > 3 时死亡
            // 2. 活细胞周围活邻居为 2 或 3 时保持存活
            // 3. 死细胞周围活邻居正好为 3 时复活
            
            // 为了保证同步更新，先记录所有细胞的邻居数
            int[][] liveCounts = new int[field.getHeight()][field.getWidth()];
            for (int row = 0; row < field.getHeight(); row++) {
                for (int col = 0; col < field.getWidth(); col++) {
                    ArrayList<Cell> neighbours = field.getNeighbours(row, col);
                    int count = 0;
                    for (Cell c : neighbours) {
                        if (c.isAlive()) count++;
                    }
                    liveCounts[row][col] = count;
                }
            }

            // 根据记录的邻居数统一更新状态
            for (int row = 0; row < field.getHeight(); row++) {
                for (int col = 0; col < field.getWidth(); col++) {
                    Cell cell = field.getCell(row, col);
                    int count = liveCounts[row][col];
                    
                    if (cell.isAlive()) {
                        if (count < 2 || count > 3) cell.die();
                    } else {
                        if (count == 3) cell.reborn();
                    }
                }
            }

            System.out.println("第 " + (i + 1) + " 代更新完成 (UPDATE)");
            view.repaint(); // 触发界面重绘
            try {
                Thread.sleep(200); // 暂停200毫秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
