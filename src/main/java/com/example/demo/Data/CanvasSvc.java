package com.example.demo.Data;

import com.example.demo.Entity.Canvas;
import com.example.demo.Entity.CanvasList;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.util.ResultMessageEnum;

public interface CanvasSvc {
    CanvasList getCanvasList() throws Exception;
    Canvas getCanvas(String canvId) throws Exception;
    ResultMessageEnum saveCanvas(String canvId, Canvas canvas, MultipartFile canvasPic) throws Exception;
}
