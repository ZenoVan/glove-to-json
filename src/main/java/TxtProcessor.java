import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by FanZeQiu on 2021/1/19
 */
public class TxtProcessor {

    private String path;

    private String fileName;

    public JSONArray txtToJson() throws IOException {
        return txtToJson(path, fileName);
    }

    public JSONArray txtToJson(String path, String fileName) throws IOException {
        JSONArray output = new JSONArray();
        File file = new File(path + "/" + fileName);
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        BufferedReader reader = new BufferedReader(inputStreamReader, 5*1024*1024);

        String line;
        while ((line = reader.readLine()) != null) {
            JSONObject object = new JSONObject();
            List<String> words = new ArrayList< String >(Arrays.asList(line.split(" ")));
            object.put("word", words.remove(0));
            JSONArray vec = new JSONArray();
            vec.addAll(words);
            object.put("vec", vec);
            output.add(object);
        }
        System.out.println("本次读入完成！");
        return output;
    }

    public void output(JSONArray json) {
        output(json, path, fileName);
        System.out.println(fileName + "写入结束！");
    }

    public void output(JSONArray json, String path, String fileName) {
//        JsonProcessor jsonProcessor = new JsonProcessor();
        String filePath = path + "/output";
        File file = new File(filePath);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
        String newName = fileName.substring(0, fileName.length() - 3) + "json";
        saveJson(json, filePath + "/" + newName);
    }

    public void saveJson(JSON json, String filePath){
        String writeString = JSON.toJSONString(json, SerializerFeature.PrettyFormat);

//        System.out.println(writeString);
        BufferedWriter writer = null;
        File file = new File(filePath);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        //如果文件不存在则新建
        if (!file.exists()){
            try {
                file.createNewFile();
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
        //如果多次执行同一个流程，会导致json文件内容不断追加，在写入之前清空文件
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(bufferedOutputStream);
            writer = new BufferedWriter(outputStreamWriter, 5*1024*1024);

//            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,false),"UTF-8"), 5*1024*1024);
            writer.write("");
            writer.write(writeString);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }finally {
            try{
                if (writer != null){
                    writer.close();
                }
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
