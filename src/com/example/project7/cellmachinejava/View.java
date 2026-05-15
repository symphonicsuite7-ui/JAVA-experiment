/**
 * 个人代码签名：黄昭展
 * AI Coding 工具：Manus AI (Version: 2026-05-15)
 */
package com.example.project7.cellmachinejava;

import com.example.project7.Cell;
import com.example.project7.field.Field;

import javax.swing.*;
import java.awt.*;

/**
 * 可视化类：负责在 Swing 窗口中绘制细胞网格
 */
public class View extends JPanel {
    private static final long serialVersionUID = 1L;
    private Field field;
    private static final int GRID_SIZE = 15; // 单元格像素大小

    public View(Field field) {
        this.field = field;
    }

    /**
     * 重写绘制方法，根据细胞状态填充颜色
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int row = 0; row < field.getHeight(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Cell cell = field.getCell(row, col);
                if (cell != null && cell.isAlive()) {
                    g.setColor(Color.BLACK); // 活细胞：黑色
                } else {
                    g.setColor(Color.WHITE); // 死细胞：白色
                }
                g.fillRect(col * GRID_SIZE, row * GRID_SIZE, GRID_SIZE, GRID_SIZE);
                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(col * GRID_SIZE, row * GRID_SIZE, GRID_SIZE, GRID_SIZE); // 绘制边框
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(field.getWidth() * GRID_SIZE, field.getHeight() * GRID_SIZE);
    }
}
