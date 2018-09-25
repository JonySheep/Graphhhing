package com.example.demo.Data;

import com.example.demo.Entity.Canvas;
import com.example.demo.Entity.CanvasList;
import com.google.gson.Gson;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.util.ResultMessageEnum;

import java.io.*;

public class CanvasSvcImpl implements CanvasSvc {

    /**
     * 读取所有已存储的图片
     * @return
     * @throws Exception
     */
    public synchronized CanvasList getCanvasList() throws Exception {
        CanvasList canvasList = null;
        String path = "Web/static/assets/canvas.json";

        String file = readFile(path);
        Gson gson = new Gson();
        canvasList = gson.fromJson(file, CanvasList.class);

        if(canvasList.getCanvasList() != null) {
            return canvasList;
        } else {
            return null;
        }
    }

    /**
     * 根据id得到这幅图片
     * @param canvId 图片id
     * @return
     * @throws Exception
     */
    @Override
    public synchronized Canvas getCanvas(String canvId) throws Exception {
        CanvasList canvasList = null;
        String path = "Web/static/assets/canvas.json";

        String file = readFile(path);
        Gson gson = new Gson();
        canvasList = gson.fromJson(file, CanvasList.class);

        if(canvasList.getCanvasList() != null) {
            for(Canvas c : canvasList.getCanvasList()) {
                if(c.getCanvasId() .equals(canvId) ) {
                    return c;
                }
            }
        } else {
            return null;
        }
        return null;
    }

    /**
     * 存储一张图片和标签信息
     * @param canvId 图片id
     * @param canvas 图片及标签
     * @return
     * @throws Exception
     */
    @Override
    public synchronized ResultMessageEnum saveCanvas(String canvId, Canvas canvas, MultipartFile canvasPic) throws Exception {

        // 存储图片
        String picPath = "Web/static/assets/" + canvId + ".png";
        File newFile = new File(picPath);
        if (newFile.exists()) {
            newFile.delete();
        }
        newFile = new File(picPath);

        try {
            if(newFile.getParent() != null && !new File(newFile.getParent()).exists()) {
                new File(newFile.getParent()).mkdirs();
            }
            newFile.createNewFile();
            canvasPic.transferTo(new File(newFile.getAbsolutePath()));
        } catch (IOException e) {
            return ResultMessageEnum.FAIL;
        }

        // 存储文件
        CanvasList canvasList = new CanvasList();

        String path = "Web/static/assets/canvas.json";
        String file = readFile(path);
        Gson gson = new Gson();
        canvasList = gson.fromJson(file, CanvasList.class);

        ResultMessageEnum res = canvasList.addCanvas(canvas);
        if (res == ResultMessageEnum.SUCCESS) {
            // write file
            String jsonStr = gson.toJson(canvasList);
            return writeFile(path, jsonStr);
        } else {
            return ResultMessageEnum.FAIL;
        }

    }

    /**
     * 用来读取文件
     * @param path 路径
     * @return
     * @throws Exception
     */
    private String readFile(String path) throws Exception {
        String str = "";
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line = null;
        while ((line = reader.readLine()) != null) {
            str = str + line;
        }
        return str;
    }

    /**
     * 用来写文件
     * @param path 路径
     * @param str 写内容
     * @return
     * @throws Exception
     */
    private ResultMessageEnum writeFile(String path, String str) throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(path));
        bw.write(str);
        bw.close();
        return ResultMessageEnum.SUCCESS;
    }
}

