package icoding.springboot.cardetect.utils;/* I love coding */

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class String_to_json {
    public static List<List<Double>> transfer(String input) throws Exception {
        // 输入字符串
        //String input = "[[0.74,0.6675,0.52,0.355],[0.485,0.725,0.97,0.47]]";

        // 使用Jackson的ObjectMapper将JSON字符串转换为List<List<Double>>
        ObjectMapper mapper = new ObjectMapper();
        List<List<Double>> result = mapper.readValue(input, List.class);
        //System.out.println(result);
        return result;
    }
}
