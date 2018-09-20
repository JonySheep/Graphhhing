package com.example.demo.Entity;

import java.util.List;

public class Canvas {
    private int canvasId;
    private String canvasUrl;
    private List<Figure> figureList;

    public Canvas(int canvasId, String canvasUrl, List<Figure> figureList) {
        this.canvasId = canvasId;
        this.canvasUrl = canvasUrl;
        this.figureList = figureList;
    }

    public void setCanvasId(int canvasId) {
        this.canvasId = canvasId;
    }

    public void setCanvasUrl(String canvasUrl) {
        this.canvasUrl = canvasUrl;
    }

    public void setFigureList(List<Figure> figureList) {
        this.figureList = figureList;
    }

    public int getCanvasId() {

        return canvasId;
    }

    public String getCanvasUrl() {
        return canvasUrl;
    }

    public List<Figure> getFigureList() {
        return figureList;
    }
}
