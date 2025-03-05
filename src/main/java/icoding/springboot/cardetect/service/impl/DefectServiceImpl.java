package icoding.springboot.cardetect.service.impl;/* I love coding */

import com.alibaba.fastjson.JSON;
import icoding.springboot.cardetect.mapper.DefectMapper;
import icoding.springboot.cardetect.pojo.CountRes;
import icoding.springboot.cardetect.pojo.Defect;
import icoding.springboot.cardetect.service.DefectService;
import icoding.springboot.cardetect.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class DefectServiceImpl implements DefectService {
    @Autowired
    private DefectMapper defectMapper;

    @Override
    public List<Defect> getDefect(int id) {
        //从缓存里获取
        Jedis jedis = RedisUtil.getJedis();
        String key = "defect:imgid" + id;
        String cacheData = jedis.get(key);
        if (cacheData != null) {
            try {
                List<Defect> defectList = JSON.parseArray(cacheData, Defect.class);
                RedisUtil.close(jedis);
                return defectList;
            } catch (Exception e) {
                log.error("running failed when parsing cacheData!", e);
            } finally {
                RedisUtil.close(jedis);
            }
        }
        //从数据库读入
        return defectMapper.getByImgId(id);
    }

    @Override
    public int deleteDefect(int img_id, int def_id) {
        //删除缓存
        int deleteId = defectMapper.delete(img_id, def_id);
        if (deleteId > 0) {
            Jedis jedis = RedisUtil.getJedis();
            String key = "defect:imgid" + img_id;
            jedis.del(key);
            RedisUtil.close(jedis);
        }
        return deleteId;
    }

    @Override
    public int addDefect(Defect defect) {
        defect.setCreateTime(LocalDateTime.now());
        log.info("source:{}", defect.getSource());
        defectMapper.insert(defect);
        int addId = defectMapper.get_last_insert_def();
        //插入缓存
        Jedis jedis = RedisUtil.getJedis();
        String key = "defect:imgid" + addId;
        try {
            String value = JSON.toJSONString(defect);
            jedis.psetex(key, 60000, value);
        } catch (Exception e) {
            log.error("running failed when add cacheData!", e);
        } finally {
            {
                RedisUtil.close(jedis);
            }
        }
        return addId;
    }

    @Override
    public List<CountRes> getCount(LocalDateTime begin, LocalDateTime end) {
        List<CountRes> c = defectMapper.get_count_type(begin, end);
        return c;
    }
}
