/**
 * 个人代码签名：黄昭展
 * AI Coding 工具：Manus AI (Version: 2026-05-15)
 */
package com.example.project7.field;

import com.example.project7.Cell;

import java.util.ArrayList;

/**
 * 细胞容器类：管理二维网格中的所有细胞
 */
public class Field {
    private int width;
    private int height;
    private Cell[][] field;

    public Field(int width, int height) {
        this.width = width;
        this.height = height;
        field = new Cell[height][width];
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public Cell getCell(int row, int col) {
        if (row >= 0 && row < height && col >= 0 && col < width) {
            return field[row][col];
        }
        return null;
    }

    public void setCell(int row, int col, Cell cell) {
        if (row >= 0 && row < height && col >= 0 && col < width) {
            field[row][col] = cell;
        }
    }

    /**
     * 获取指定坐标周围的8个邻居细胞
     */
    public ArrayList<Cell> getNeighbours(int row, int col) {
        ArrayList<Cell> neighbours = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue; // 排除自身
                int r = row + i;
                int c = col + j;
                if (r >= 0 && r < height && c >= 0 && c < width) {
                    neighbours.add(field[r][c]);
                }
            }
        }
        return neighbours;
    }
}
