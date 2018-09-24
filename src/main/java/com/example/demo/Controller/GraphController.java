package com.example.demo.Controller;
import com.example.demo.Data.CanvasSvc;
import com.example.demo.Data.CanvasSvcImpl;
import com.example.demo.Entity.Canvas;
import com.example.demo.Entity.Figure;
import com.example.demo.Entity.FigureList;
import com.example.demo.util.ResultMessageEnum;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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

    @RequestMapping(value = "/{canvas_id}", method = RequestMethod.POST)
    public Object saveCanvas(@PathVariable(value = "canvas_id") String canvasId,
                             @RequestParam(value = "canvas") MultipartFile canvasPic,
                             @RequestParam(value = "figures") String figureList){

        Map<String, Object> map = new HashMap<>();
        String path = "Web/static/assets/" + canvasId + ".png";

        Gson gson = new Gson();
        FigureList figureListClass = gson.fromJson(figureList, FigureList.class);

        String savedUrl = "../../static/assets/" + canvasId + ".png";
        Canvas postedCanvas = new Canvas(canvasId, savedUrl, figureListClass.getFigureList());

        File newFile = new File(path);
        if (newFile.exists()) {
            newFile.delete();
        }
        newFile = new File(path);

        try {
            if(newFile.getParent() != null && !new File(newFile.getParent()).exists()) {
                new File(newFile.getParent()).mkdirs();
            }
            newFile.createNewFile();
            canvasPic.transferTo(new File(newFile.getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("data", e.getStackTrace());
        }

        CanvasSvc svc = new CanvasSvcImpl();
        try {
            ResultMessageEnum result = svc.saveCanvas(canvasId, postedCanvas);
            map.put("success", true);
            map.put("data", result);
        } catch (Exception e) {
            map.put("success", false);
            map.put("data", e.getStackTrace());
        }
        return map;
    }
}
