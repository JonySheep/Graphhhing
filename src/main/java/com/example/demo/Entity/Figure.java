package com.example.demo.Entity;

import java.util.List;

public class Figure {
    private String shape; // 形状：如正方形、圆形、三角形等
    private List<Integer> pointList; // 坐标点
    private String color; // 颜色

    // Constructor
    public Figure(String shape, List<Integer> pointList, String color) {
        this.shape = shape;
        this.pointList = pointList;
        this.color = color;
    }

    public String getShape() {
        return shape;
    }

    public List<Integer> getPointList() {
        return pointList;
    }

    public String getColor() {
        return color;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public void setPointList(List<Integer> pointList) {
        this.pointList = pointList;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
