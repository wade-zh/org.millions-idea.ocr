package org.millions.idea.ocr.web.common.utility.json;

import com.alibaba.fastjson.JSON;

import java.util.List;

public class JsonUtil {

    public static String getJson(Object obj){
        return JSON.toJSONString(obj);
    }

    public static <T> List<T> getModelAsList(String jsonString, Class<T> clazz){
        return JSON.parseArray(jsonString, clazz);
    }

    public static <T> T getModel(String jsonString, Class<T> clazz){
        try{
            return JSON.parseObject(jsonString, clazz);
        }catch (Exception e){
            return null;
        }
    }
}
