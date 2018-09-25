package com.example.demo.Entity;

import java.util.List;

public class FigureList {
    private List<Figure> figureList;

    public FigureList(List<Figure> figureList) {
        this.figureList = figureList;
    }

    public List<Figure> getFigureList() {
        return figureList;
    }
}
