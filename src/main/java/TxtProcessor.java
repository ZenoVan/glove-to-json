import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

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

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

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
        return output;
    }

    public void output(JSONArray json) {
        output(json, path, fileName);
    }

    public void output(JSONArray json, String path, String fileName) {
        JsonProcessor jsonProcessor = new JsonProcessor();
        String filePath = path + "/output";
        File file = new File(filePath);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
        String newName = fileName.substring(0, fileName.length() - 3) + "json";
        jsonProcessor.saveJson(json, filePath + "/" + newName);
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
