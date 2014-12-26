/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.web.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

public class ImgGenerator {

    public static List<String> readFileByLine(String fileName) throws IOException {
        List<String> nameList = new ArrayList<String>();
        File file = new File(fileName);
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
        BufferedReader reader = new BufferedReader(isr);
        String tmp;
        while ((tmp = reader.readLine()) != null) {
            nameList.add(tmp);
        }
        reader.close();
        return nameList;
    }

    public static boolean generateImg(String name, long type, String path) {
        Color backgroundColor = Color.green;
        Color wordColor = Color.white;
        Color frameColor = Color.white;

        // QA，边框为白色、背景为深绿色、字体为白色
        if (type == Constants.QA) {
            frameColor = Color.white;
            backgroundColor = new Color(34, 90, 31);
            wordColor = Color.white;
            // RD，边框为绿色、背景为白色、字体为绿色
        } else if (type == Constants.RD) {
            frameColor = new Color(34, 90, 31);
            backgroundColor = Color.white;
            wordColor = new Color(34, 90, 31);
            // pm，边框白色、背景为绿色、字体为白色
        } else if (type == Constants.PM) {
            frameColor = Color.white;
            backgroundColor = new Color(00, 53, 44);
            wordColor = Color.white;
        }
        try {
            generateImg(name, backgroundColor, wordColor, frameColor, path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static void generateImg(String name, Color backgroundColor, Color wordColor, Color frameColor,
            String fileName) throws IOException {

        Random r = new Random();
        int width = 100;
        int height = 100;
        String first = null, second = null, third = null, fourth = null;
        if (name.length() > 1) {
            first = name.substring(name.length() - 2, name.length() - 1);
        }
        second = name.substring(name.length() - 1, name.length());
        third = "验";
        fourth = "证";

        File file = new File(fileName);
        Font font = new Font("华文行楷", Font.PLAIN, 40);

        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) bi.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2.setBackground(backgroundColor);
        g2.clearRect(0, 0, width, height);
        g2.setFont(font);

        g2.setPaint(wordColor);
        g2.drawString(first, 50, 40);
        g2.drawString(second, 50, 80);
        g2.drawString(third, 10, 40);

        if (first == null) {
            drawStar(g2);
        } else {
            g2.drawString(fourth, 10, 80);
        }

        g2.setColor(frameColor);
        g2.drawRect(0, 0, width - 1, height - 1);
        g2.drawRect(1, 1, width - 1, height - 1);
        g2.drawRect(0, 0, width - 2, height - 2);

        g2.drawRect(5, 5, width - 11, height - 11);

        g2.setPaint(Color.white);

        for (int i = 0; i < width; i = i + 4) {
            if (r.nextInt(2) == 1) {
                // 北边框随机虚化

                g2.drawOval(i, -1, 7, 7);
                // 南边框随机虚化

                g2.drawOval(i, height - 6, 7, 7);
                // 西边框随机虚化

                g2.drawOval(-1, i, 7, 7);
                // 东边框随机虚化

                g2.drawOval(width - 6, i, 7, 7);
            }
        }

        if (file.exists() == false) {
            file.mkdirs();
        }
        ImageIO.write(bi, "jpg", file);
    }

    public static Color getRandomColor() {
        Random r = new Random();
        return new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
    }

    public static void drawStar(Graphics2D g) {
        // 定义外切圆和内接圆的半径
        int R = 16;
        int r = (int) (R * Math.sin(Math.PI / 10) / Math.sin(3 * Math.PI / 10));
        // 定义两个数组, 分别存放10个顶点的x, y 坐标
        int[] x = new int[10];
        int[] y = new int[10];
        // 通过循环给两个数组赋值
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                x[i] = 30 + (int) (R * Math.cos(Math.PI / 10 + (i - 1) * Math.PI / 5));
                y[i] = 70 + (int) (R * Math.sin(Math.PI / 10 + (i - 1) * Math.PI / 5));
            } else {
                x[i] = 30 + (int) (r * Math.cos(Math.PI / 10 + (i - 1) * Math.PI / 5));
                y[i] = 70 + (int) (r * Math.sin(Math.PI / 10 + (i - 1) * Math.PI / 5));
            }
        }

        g.setPaint(Color.white);
        // 调用fillPolygon方法绘制
        g.fillPolygon(x, y, 10);
    }
}
