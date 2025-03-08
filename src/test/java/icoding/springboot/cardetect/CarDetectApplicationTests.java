package icoding.springboot.cardetect;


import icoding.springboot.cardetect.mapper.DefectMapper;

import icoding.springboot.cardetect.pojo.Defect;
import icoding.springboot.cardetect.pojo.ModelResponse;
import icoding.springboot.cardetect.service.ModelResService;

import icoding.springboot.cardetect.utils.String_to_json;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@SpringBootTest
@Slf4j
class CarDetectApplicationTests {
    @Autowired
    private DefectMapper defectMapper;
    @Autowired
    private ModelResService modelResService;
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
        String input = "76.1 34.3 95.4 12.33 58.323 83.2 27.5 49.6 5.6 91.7 63.8 18.987";
        String_to_json.transfer(input);
    }
}
