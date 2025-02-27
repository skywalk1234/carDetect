package icoding.springboot.cardetect.service.impl;/* I love coding */

import icoding.springboot.cardetect.mapper.DefectMapper;
import icoding.springboot.cardetect.pojo.CountRes;
import icoding.springboot.cardetect.pojo.Defect;
import icoding.springboot.cardetect.service.DefectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class DefectServiceImpl implements DefectService {
    @Autowired
    private DefectMapper defectMapper;

    @Override
    public List<Defect> getDefect(int id) {
        return defectMapper.getByImgId(id);
    }

    @Override
    public int deleteDefect(int img_id, int def_id) {
       return defectMapper.delete(img_id, def_id);
    }

    @Override
    public int addDefect(Defect defect) {
        defect.setCreateTime(LocalDateTime.now());
        log.info("source:{}",defect.getSource());
        defectMapper.insert(defect);
        return defectMapper.get_last_insert_def();
    }

    @Override
    public List<CountRes> getCount(LocalDateTime begin, LocalDateTime end) {
        List<CountRes> c = defectMapper.get_count_type(begin, end);
        return c;
    }
}
