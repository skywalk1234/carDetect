package icoding.springboot.cardetect.service;


import icoding.springboot.cardetect.pojo.Defect;
import icoding.springboot.cardetect.pojo.ModelResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ModelResService {
 String sendQuest(MultipartFile file);
 List<ModelResponse> parseQuestData(String json);
 void processQuestData(Defect defect);
}
