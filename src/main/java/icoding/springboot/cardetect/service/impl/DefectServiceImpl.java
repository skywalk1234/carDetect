package icoding.springboot.cardetect.service.impl;/* I love coding */

import com.alibaba.fastjson.JSON;
import icoding.springboot.cardetect.mapper.DefectMapper;
import icoding.springboot.cardetect.pojo.CountRes;
import icoding.springboot.cardetect.pojo.Defect;
import icoding.springboot.cardetect.pojo.ResDefect;
import icoding.springboot.cardetect.service.DefectService;
import icoding.springboot.cardetect.utils.RedisUtil;
import icoding.springboot.cardetect.utils.String_to_json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DefectServiceImpl implements DefectService {
    @Autowired
    private DefectMapper defectMapper;

    @Override
    public List<ResDefect> getDefect(int id) {
        //从缓存里获取
        log.info("开始从缓存中获取");
        Jedis jedis = RedisUtil.getJedis();
        String key = "defect:imgid" + id;
        String cacheData = jedis.get(key);
        log.info("试图从缓存中获取");
        if (cacheData != null ) {
            try {
                List<Defect> defectList = JSON.parseArray(cacheData, Defect.class);
                log.info("映射从缓存中拿到的json数据");

                //从缓存中拿到的defect转成ResDefect
                List<ResDefect> resDefectList = new ArrayList<>();
                for(Defect defect : defectList) {
                    ResDefect resDefect = new ResDefect(defect.getDef_id(),
                            defect.getImg_id(),
                            defect.getType(),
                            defect.getSource(),
                            String_to_json.transfer(defect.getPosition()),
                            defect.getCreate_time()
                    );
                    resDefectList.add(resDefect);
                }
                return resDefectList;
            } catch (Exception e) {
                log.error("running failed when parsing cacheData!", e);
            } finally {
                RedisUtil.close(jedis);
            }
        }

        //如果缓存没有想要的数据就从数据库读入并处理position
        List<Defect> deflist = defectMapper.getByImgId(id);
        List<ResDefect> resList = new ArrayList<>();
        try{
            for(Defect defect : deflist) {
                ResDefect resDefect = new ResDefect(defect.getDef_id(),
                        defect.getImg_id(),
                        defect.getType(),
                        defect.getSource(),
                        String_to_json.transfer(defect.getPosition()),
                        defect.getCreate_time()
                );
                resList.add(resDefect);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return resList;
    }

    @Override
    public int deleteDefect(int img_id, int def_id) {
        //删除缓存
        int deleteId = defectMapper.delete(img_id, def_id);
        if (deleteId > 0) {
            Jedis jedis = RedisUtil.getJedis();
            String key = "defect:imgid" + img_id;
            try {
                jedis.del(key);
            } finally {
                RedisUtil.close(jedis); // 确保连接关闭
            }
        }
        return deleteId;
    }

    @Override
    public int addDefect(Defect defect) {
        defect.setCreate_time(LocalDateTime.now());
        log.info("source:{}", defect.getSource());
        //插入数据库
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
                RedisUtil.close(jedis);
        }
        return addId;
    }

    @Override
    public List<CountRes> getCount(LocalDateTime begin, LocalDateTime end) {
        List<CountRes> c = defectMapper.get_count_type(begin, end);
        return c;
    }
}
