package icoding.springboot.cardetect.service;


import icoding.springboot.cardetect.pojo.Defect;
import icoding.springboot.cardetect.pojo.ModelResponse;

import java.util.List;

public interface ModelResService {
 String sendQuest(String url);
 List<ModelResponse> parseQuestData(String json);
 void processQuestData(Defect defect);
}
