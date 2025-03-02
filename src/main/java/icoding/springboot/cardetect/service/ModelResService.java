package icoding.springboot.cardetect.service;


import icoding.springboot.cardetect.pojo.Defect;

public interface ModelResService {
 String sendQuest(int id);
 Defect parseQuestData(String json);
 void processQuestData(Defect defect);
}
