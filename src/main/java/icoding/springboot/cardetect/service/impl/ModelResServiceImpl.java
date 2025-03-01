package icoding.springboot.cardetect.service.impl;

import icoding.springboot.cardetect.service.DefectService;
import icoding.springboot.cardetect.service.ModelResService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class ModelResServiceImpl implements ModelResService {
    @Autowired
    private DefectService defectService;
//
//    @Override
//    public ModelRes getModelRes(Integer defectId) {
//        return modelResMapper.getModelRes(defectId);
//    }
//    @Override
//    public int addModelRes(ModelRes modelRes) {
//        modelRes.setCreateTime(LocalDateTime.now());
//        //modelRes.setUpdatedAt(LocalDateTime.now());
//        return modelResMapper.addModelRes(modelRes);
//    }
//    @Override
//    public int deleteModelRes(Integer defectId) {
//        return modelResMapper.deleteModelRes(defectId);
//    }
    //真的非常抱歉，是我之前没讲清楚，上面这些代码跟DefectServiceImpl中实现的是一样的功能所以就先注释掉了。
    //但是这个类可以保留用来执行给机器学习的模型发送和接收请求，解析数据的任务
    //解析完数据就调用DefectService中的addDefect方法把检测结果写入数据库

}