package icoding.springboot.cardetect.utils;

import com.alibaba.fastjson.JSON;

public class JsonUtil {
    public static <T> T parseJson(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }
}