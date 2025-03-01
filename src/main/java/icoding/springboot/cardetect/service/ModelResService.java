package icoding.springboot.cardetect.service;

import icoding.springboot.cardetect.pojo.ModelRes;

public interface ModelResService {
 ModelRes getModelRes(Integer defectId);
 int addModelRes(ModelRes modelRes);
 int deleteModelRes(Integer defectId);

}
