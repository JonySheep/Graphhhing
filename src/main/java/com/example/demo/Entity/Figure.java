package com.example.demo.Entity;

import java.util.List;

public class Figure {
    private String shape; // 形状：如正方形、圆形、三角形等
    private Point textPoint; // 显示标签的坐标点
    private String color; // 颜色

    // Constructor
    public Figure(String shape, Point textPoint, String color) {
        this.shape = shape;
        this.textPoint = textPoint;
        this.color = color;
    }

    public String getShape() {
        return shape;
    }

    public Point getTextPoint() {
        return textPoint;
    }

    public String getColor() {
        return color;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public void setTextPoint(Point textPoint) {
        this.textPoint = textPoint;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
