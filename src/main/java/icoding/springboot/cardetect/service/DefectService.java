package icoding.springboot.cardetect.service;/* I love coding */

import icoding.springboot.cardetect.pojo.CountRes;
import icoding.springboot.cardetect.pojo.Defect;

import java.time.LocalDateTime;
import java.util.List;

public interface DefectService {
    public List<Defect> getDefect(int id);
    public int deleteDefect(int img_id,int def_id);
    public int addDefect(Defect defect);
    public List<CountRes> getCount(LocalDateTime begin, LocalDateTime end);
}
