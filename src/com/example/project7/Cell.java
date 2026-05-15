/**
 * 个人代码签名：黄昭展
 * AI Coding 工具：Manus AI (Version: 2026-05-15)
 */
package com.example.project7;

/**
 * 细胞类：代表生命游戏中的一个基本单元
 */
public class Cell {
    private boolean isAlive; // 存活状态

    public Cell() {
        this.isAlive = false;
    }

    public void reborn() {
        this.isAlive = true;
    }

    public void die() {
        this.isAlive = false;
    }

    public boolean isAlive() {
        return isAlive;
    }
}
