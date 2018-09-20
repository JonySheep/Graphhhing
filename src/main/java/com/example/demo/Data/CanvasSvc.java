package com.example.demo.Data;

import com.example.demo.Entity.Canvas;
import com.example.demo.util.ResultMessageEnum;

public interface CanvasSvc {
    Canvas getCanvas(int canvId) throws Exception;
    ResultMessageEnum saveCanvas(int canvId, Canvas canvas) throws Exception;
}
