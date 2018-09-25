package com.example.demo.Entity;

import com.example.demo.util.ResultMessageEnum;

import java.util.ArrayList;
import java.util.List;

public class CanvasList {
    private List<Canvas> canvasList;

    public CanvasList() {
        this.canvasList = new ArrayList<>();
    }

    public void setCanvasList(List<Canvas> canvasList) {
        this.canvasList = canvasList;
    }

    public List<Canvas> getCanvasList() {
        return canvasList;
    }

    /**
     * 增加一幅图片
     * @param canvas
     * @return
     */
    public ResultMessageEnum addCanvas (Canvas canvas) {
        this.canvasList.add(canvas);
        return ResultMessageEnum.SUCCESS;
    }
}
