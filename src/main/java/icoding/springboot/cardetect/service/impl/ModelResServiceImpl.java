package icoding.springboot.cardetect.service.impl;
import icoding.springboot.cardetect.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import icoding.springboot.cardetect.pojo.Defect;
import icoding.springboot.cardetect.service.DefectService;
import icoding.springboot.cardetect.service.ModelResService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ModelResServiceImpl implements ModelResService {

    @Autowired
    private DefectService defectService;


    @Override
    public String sendQuest(int id)
    {
        //TODO
    }

    @Override
    public Defect parseQuestData(String json){
        Defect defect = JsonUtil.parseJson(json, Defect.class);
        return defect;
    }

    @Override
    public void processQuestData(Defect defect){
        defectService.addDefect(defect);
    }

}


