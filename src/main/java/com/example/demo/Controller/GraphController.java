package com.example.demo.Controller;
import com.example.demo.Data.CanvasSvc;
import com.example.demo.Data.CanvasSvcImpl;
import com.example.demo.Entity.Canvas;
import com.example.demo.Entity.FigureList;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.util.ResultMessageEnum;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/canvas")
@CrossOrigin(origins = "http://localhost:8080")
public class GraphController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object getCanvasList() {
        Map<String,Object> map =new HashMap<>();
        CanvasSvc svc = new CanvasSvcImpl();

        try{
            map.put("data",svc.getCanvasList());
            map.put("success",true);
        }
        catch (Exception e){
            map.put("data",e.getStackTrace());
            map.put("success",false);
        }
        return map;
    }

    /**
     * 根据id得到这幅图片
     * @param canvasId 图片id
     * @return
     */
    @RequestMapping(value = "/{canvas_id}", method = RequestMethod.GET)
    public Object getCanvas(@PathVariable(value = "canvas_id") String canvasId){
        Map<String,Object> map =new HashMap<>();
        CanvasSvc svc = new CanvasSvcImpl();

        try{
            map.put("data",svc.getCanvas(canvasId));
            map.put("success",true);
        }
        catch (Exception e){
            map.put("data",e.getStackTrace());
            map.put("success",false);
        }
        return map;
    }

    /**
     * 存储一幅绘图
     * @param canvasId 绘图id，由前端生成
     * @param canvasPic 图片文件
     * @param figureList 形状标签列表
     * @return
     */
    @RequestMapping(value = "/{canvas_id}", method = RequestMethod.POST)
    public Object saveCanvas(@PathVariable(value = "canvas_id") String canvasId,
                             @RequestParam(value = "canvas") MultipartFile canvasPic,
                             @RequestParam(value = "figures") String figureList){

        Map<String, Object> map = new HashMap<>();

        // 创建一个canvas类
        Gson gson = new Gson();
        FigureList figureListClass = gson.fromJson(figureList, FigureList.class);
        String savedUrl = "../../static/assets/" + canvasId + ".png";
        Canvas postedCanvas = new Canvas(canvasId, savedUrl, figureListClass.getFigureList());


        CanvasSvc svc = new CanvasSvcImpl();
        try {
            ResultMessageEnum result = svc.saveCanvas(canvasId, postedCanvas, canvasPic);
            map.put("success", true);
            map.put("data", result);
        } catch (Exception e) {
            map.put("success", false);
            map.put("data", e.getStackTrace());
        }
        return map;
    }
}
