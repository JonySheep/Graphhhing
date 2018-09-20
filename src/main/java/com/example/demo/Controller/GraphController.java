package com.example.demo.Controller;
import com.example.demo.Data.CanvasSvc;
import com.example.demo.Data.CanvasSvcImpl;
import com.example.demo.Entity.Canvas;
import com.example.demo.util.ResultMessageEnum;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/canvas")
public class GraphController {

    /**
     * 根据id得到这幅图片
     * @param canvasId 图片id
     * @return
     */
    @RequestMapping(value = "/{canvas_id}", method = RequestMethod.GET)
    public Object getCanvas(@PathVariable(value = "canvas_id") int canvasId){
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
    public Object saveCanvas(@PathVariable(value = "canvas_Id") int canvasId,
                             @RequestBody Canvas canvas){
        Map<String, Object> map = new HashMap<>();
        CanvasSvc svc = new CanvasSvcImpl();
        try {
            ResultMessageEnum result = svc.saveCanvas(canvasId, canvas);
            map.put("success", true);
            map.put("data", result);
        } catch (Exception e) {
            map.put("success", false);
            map.put("data", e.getStackTrace());
        }
        return map;
    }
}
