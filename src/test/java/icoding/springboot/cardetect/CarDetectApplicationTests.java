package icoding.springboot.cardetect;


import icoding.springboot.cardetect.mapper.DefectMapper;

import icoding.springboot.cardetect.pojo.Defect;
import icoding.springboot.cardetect.pojo.ModelResponse;
import icoding.springboot.cardetect.service.ImgService;
import icoding.springboot.cardetect.service.ModelResService;

import icoding.springboot.cardetect.utils.String_to_json;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
@Slf4j
class CarDetectApplicationTests {
    @Autowired
    private DefectMapper defectMapper;
    @Autowired
    private ModelResService modelResService;
    @Autowired
    private ImgService imgService;
//    @Test
//    void addDefectTest(){
//
//        Defect defect = new Defect();
//        defect.setImgId(4);
//        defect.setType(2);
//        defect.setSource("machine");
//        defect.setCreateTime(LocalDateTime.now());
//        defect.setPosition("10045 3 1048 5 1489 6");
//        defectMapper.insert(defect);
//    }

    @Test
    void contextLoads() {
        String res_json = "[\n" +
                "  {\n" +
                "    \"image\": \"crazing_5.jpg\",\n" +
                "    \"type\": 0,\n" +
                "    \"position\": \"0.7400 0.6675 0.5200 0.3550\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"image\": \"crazing_5.jpg\",\n" +
                "    \"type\": 0,\n" +
                "    \"position\": \"0.4850 0.7250 0.9700 0.4700\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"image\": \"crazing_5.jpg\",\n" +
                "    \"type\": 1,\n" +
                "    \"position\": \"0.5225 0.4775 0.9550 0.3650\"\n" +
                "  }]";
        List<ModelResponse> res = modelResService.parseQuestData(res_json);
        //ModelResponse类就是负责跟json里面的字段作映射的
        //合并相同文件且相同缺陷的position然后给defect
        //调用modelResService.processQuestData(defect)写入
        Map<Integer, List<List<Double>>> map = new HashMap<>();

        for (ModelResponse m : res) {
            int type = m.getType();
            String positionStr = m.getPosition();
            List<Double> position = imgService.parsePosition(positionStr);
            map.compute(type, (key, existingPositions) -> {
                if (existingPositions == null) {
                    List<List<Double>> newPositions = new ArrayList<>();
                    newPositions.add(position);
                    return newPositions;
                } else {
                    existingPositions.add(position);
                    return existingPositions;
                }
            });

        }
        for (Map.Entry<Integer, List<List<Double>>> entry : map.entrySet()) {
            Defect defect = new Defect();
            defect.setType(entry.getKey());
            defect.setImgId(3);

            String positionJson = imgService.convertToJsonString(entry.getValue());
            defect.setPosition(positionJson);
            defect.setCreateTime(LocalDateTime.now());
            defect.setSource("machine");
            modelResService.processQuestData(defect);
        }
    }
}
