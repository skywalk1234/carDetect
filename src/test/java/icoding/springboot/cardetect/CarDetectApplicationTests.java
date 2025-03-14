package icoding.springboot.cardetect;


import icoding.springboot.cardetect.mapper.DefectMapper;

import icoding.springboot.cardetect.pojo.Defect;
import icoding.springboot.cardetect.pojo.Img;
import icoding.springboot.cardetect.pojo.ModelResponse;
import icoding.springboot.cardetect.service.ImgService;
import icoding.springboot.cardetect.service.ModelResService;

import icoding.springboot.cardetect.utils.String_to_json;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

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
//    void contextLoads() {
//        String res_json = "[\n" +
//                "  {\n" +
//                "    \"image\": \"crazing_5.jpg\",\n" +
//                "    \"type\": 2,\n" +
//                "    \"position\": \"0.7400 0.6675 0.5200 0.3550\"\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"image\": \"crazing_5.jpg\",\n" +
//                "    \"type\": 0,\n" +
//                "    \"position\": \"0.4850 0.7250 0.9700 0.4700\"\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"image\": \"crazing_5.jpg\",\n" +
//                "    \"type\": 1,\n" +
//                "    \"position\": \"0.5225 0.4775 0.9550 0.3650\"\n" +
//                "  }]";
//        List<ModelResponse> res = modelResService.parseQuestData(res_json);
//        //ModelResponse类就是负责跟json里面的字段作映射的
//        //合并相同文件且相同缺陷的position然后给defect
//        //调用modelResService.processQuestData(defect)写入
//
//        Map<Integer, List<List<Double>>> map = new HashMap<>();
//
//        for (ModelResponse m : res) {
//            int type = m.getType();
//            String positionStr = m.getPosition();
//            List<Double> position =imgService.parsePosition(positionStr);
//            map.compute(type, (key, existingPositions) -> {
//                if (existingPositions == null) {
//                    List<List<Double>> newPositions = new ArrayList<>();
//                    newPositions.add(position);
//                    return newPositions;
//                } else {
//                    existingPositions.add(position);
//                    return existingPositions;
//                }
//            });
//        }
//
//        map.forEach((type, positions) -> {
//            Defect defect = new Defect();
//            defect.setType(type);
//            defect.setImg_id(3);
//            String positionJson = imgService.convertToJsonString(positions);
//            defect.setPosition(positionJson);
//            defect.setCreate_time(LocalDateTime.now());
//            defect.setSource("machine");
//            modelResService.processQuestData(defect);
//        });
//    }
//    @Test
//    void testSendRequest(){
//        MultipartFile file = null;
//        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
//            HttpPost uploadFile = new HttpPost("http://localhost:8080/inspect_img");
//            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//            builder.addBinaryBody(
//                    "image", // 服务器端接受参数的名字
//                    file.getBytes(),
//                    ContentType.MULTIPART_FORM_DATA,
//                    file.getOriginalFilename()
//            );
//            HttpEntity multipart = builder.build();
//            uploadFile.setEntity(multipart);
//
//            try (CloseableHttpResponse response = httpClient.execute(uploadFile)) {
//                HttpEntity responseEntity = response.getEntity();
//                if (response.getStatusLine().getStatusCode() == 200) {
//                    System.out.println(responseEntity);
//                    //return EntityUtils.toString(responseEntity, "UTF-8");//返回响应体
//                } else {
//                    System.out.println("error");
//                    //return "响应出现错误";
//                }
//            }
//
//        } catch (Exception e) {
//            log.error("Error occurred while uploading file.", e);
//           // return "发生异常";
//        }
 //   }
}
