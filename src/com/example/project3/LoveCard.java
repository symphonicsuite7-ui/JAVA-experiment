/**
 * 个人代码签名：黄昭展
 * AI Coding 工具：Manus AI (Version: 2026-05-15)
 */
package com.example.project3;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 字符画表白卡类：将图片转换为字符画输出
 */
public class LoveCard {

    // 灰度字符映射表，从浅到深
    private static final String ASCII_CHARS = " .:-=+*#%@"; 

    /**
     * 核心算法：读取图片像素并转换为对应的ASCII字符
     * @param imagePath 图片路径
     * @param newWidth 输出的字符画宽度
     */
    public static void convertImageToAscii(String imagePath, int newWidth) {
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            if (image == null) {
                System.err.println("无法读取图片文件: " + imagePath);
                return;
            }

            // 计算缩放后的高度，维持比例（字符通常比像素高，所以高度需额外除以2）
            int originalWidth = image.getWidth();
            int originalHeight = image.getHeight();
            int newHeight = (int) (originalHeight / (originalWidth / (double) newWidth) / 2);

            // 创建缩放后的图片
            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            resizedImage.getGraphics().drawImage(image, 0, 0, newWidth, newHeight, null);

            StringBuilder asciiArt = new StringBuilder();
            for (int y = 0; y < newHeight; y++) {
                for (int x = 0; x < newWidth; x++) {
                    int pixel = resizedImage.getRGB(x, y);
                    // 提取红绿蓝颜色分量
                    int red = (pixel >> 16) & 0xff;
                    int green = (pixel >> 8) & 0xff;
                    int blue = (pixel) & 0xff;

                    // 使用亮度公式计算灰度值
                    int gray = (int) (0.2126 * red + 0.7152 * green + 0.0722 * blue);

                    // 将灰度值映射到字符索引
                    int charIndex = (int) (gray / 255.0 * (ASCII_CHARS.length() - 1));
                    asciiArt.append(ASCII_CHARS.charAt(charIndex));
                }
                asciiArt.append("\n");
            }
            // 输出生成的字符画
            System.out.println(asciiArt.toString());

        } catch (IOException e) {
            System.err.println("处理图片时发生错误: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // 示例：转换 resources 目录下的图片
        String imagePath = "resources/sample.jpg";
        int width = 80; 
        System.out.println("正在为您生成字符画表白卡...");
        convertImageToAscii(imagePath, width);
    }
}
