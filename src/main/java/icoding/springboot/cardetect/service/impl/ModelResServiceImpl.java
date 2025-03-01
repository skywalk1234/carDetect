package icoding.springboot.cardetect.service.impl;

import icoding.springboot.cardetect.mapper.ModelResMapper;
import icoding.springboot.cardetect.pojo.ModelRes;
import icoding.springboot.cardetect.service.ModelResService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class ModelResServiceImpl implements ModelResService {
    @Autowired
    private ModelResMapper modelResMapper;

    @Override
    public ModelRes getModelRes(Integer defectId) {
        return modelResMapper.getModelRes(defectId);
    }
    @Override
    public int addModelRes(ModelRes modelRes) {
        modelRes.setCreatedAt(LocalDateTime.now());
        modelRes.setUpdatedAt(LocalDateTime.now());
        return modelResMapper.addModelRes(modelRes);
    }
    @Override
    public int deleteModelRes(Integer defectId) {
        return modelResMapper.deleteModelRes(defectId);
    }

}