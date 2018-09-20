package com.example.demo.util;


import com.example.demo.util.ResultMessageEnum;

import java.io.*;

public class JsonIO {
    public static ResultMessageEnum write(String filePath, String input) throws IOException {
        File file = new File(filePath);
        if(!file.exists()){
            file.createNewFile();
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write(input);
        bw.close();
        return ResultMessageEnum.SUCCESS;
    }

    public static String read(String filePath) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line = null;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();
    }
}
