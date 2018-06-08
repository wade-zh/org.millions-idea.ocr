/***
 * @pName org.millions-idea.ocr
 * @name JsonUtil
 * @user HongWei
 * @date 2018/6/8
 * @desc
 */
package org.millions.idea.ocr.utility.json;


import com.alibaba.fastjson.JSON;
import java.util.List;

public class JsonUtil {
    public JsonUtil() {
    }

    public static String getJson(Object obj) {
        return JSON.toJSONString(obj);
    }

    public static <T> List<T> getModelAsList(String jsonString, Class<T> clazz) {
        return JSON.parseArray(jsonString, clazz);
    }

    public static <T> T getModel(String jsonString, Class<T> clazz) {
        return JSON.parseObject(jsonString, clazz);
    }
}
