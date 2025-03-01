package icoding.springboot.cardetect;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import icoding.springboot.cardetect.mapper.DefectMapper;
import icoding.springboot.cardetect.pojo.Defect;
import org.junit.jupiter.api.Test;
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
}
