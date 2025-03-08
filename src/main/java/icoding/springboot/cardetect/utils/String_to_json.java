package icoding.springboot.cardetect.utils;/* I love coding */

import java.util.ArrayList;
import java.util.List;

public class String_to_json {
    public static List<List<Double>> transfer(String input){

        // 分割输入字符串
        String[] tokens = input.split(" ");

        List<List<Double>> result = new ArrayList<>();
        List<Double> currentGroup = new ArrayList<>();

        for (String token : tokens) {
            if (currentGroup.size() == 4) { // 每三个数字创建一个新的列表
                result.add(currentGroup);
                currentGroup = new ArrayList<>();
            }
            currentGroup.add(Double.parseDouble(token));
        }
        if (!currentGroup.isEmpty()) { // 添加最后一个组，如果有的话
            result.add(currentGroup);
        }
        return result;
        // 输出结果
//        for (List<Double> group : result) {
//            System.out.println(group);
//        }
    }
}
