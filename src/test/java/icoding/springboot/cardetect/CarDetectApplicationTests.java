package icoding.springboot.cardetect;


import icoding.springboot.cardetect.mapper.DefectMapper;

import icoding.springboot.cardetect.service.ModelResService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
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
//    @Test
//    void parseData(){
//        String res_json = "[\n" +
//                "    {\n" +
//                "        \"ImageId_ClassId\": \"0002cc93b.jpg_1\",\n" +
//                "        \"EncodedPixels\":\"29102 12 29346 24 29602 24 29858 24 30114 24 30370 24 30626 24\"\n" +
//                "    },\n" +
//                "    {\n" +
//                "        \"ImageId_ClassId\": \"0002cc96b.jpg_2\",\n" +
//                "        \"EncodedPixels\":\"29102 12 29346 24 29602 24 29858 24 30114 24 30370 24 30626 24\"\n" +
//                "    }\n" +
//                "]";
//        List<ModelResponse> res = modelResService.parseQuestData(res_json);//解析json格式
//        for(ModelResponse m : res) {
//            //提取列表中
//            Defect defect = new Defect();
//            String img_name = m.getImageId_ClassId();//提取图片名字的最后一个字符（就是缺陷类型）
//            int type = img_name.charAt(img_name.length()-1)-'0';//将字符转成数字
//            defect.setType(type);
//            defect.setImgId(2);
//            defect.setPosition(m.getEncodedPixels());
//            defect.setCreateTime(LocalDateTime.now());
//            defect.setSource("machine");
//            modelResService.processQuestData(defect);
//
//        }
//    }
}
