package icoding.springboot.cardetect.utils;

import com.alibaba.fastjson.JSON;

import java.util.List;

public class JsonUtil {
    public static <T> List<T> parseJson(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }
}