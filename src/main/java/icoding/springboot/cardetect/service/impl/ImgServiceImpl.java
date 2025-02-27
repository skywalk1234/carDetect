package icoding.springboot.cardetect.service.impl;/* I love coding */

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import icoding.springboot.cardetect.mapper.ImgMapper;
import icoding.springboot.cardetect.pojo.Img;
import icoding.springboot.cardetect.pojo.PageBean;
import icoding.springboot.cardetect.service.ImgService;
import icoding.springboot.cardetect.utils.MYSQL_;
import icoding.springboot.cardetect.utils.OssTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class ImgServiceImpl implements ImgService {
    @Autowired
    private ImgMapper imgMapper;

    @Override
    public Img addImg(String url,String uploader) {
        Img img = new Img();

        img.setImage(url);
        img.setInspectTime(LocalDateTime.now());
        img.setUploader(uploader);
        imgMapper.insert(img);
        return imgMapper.get_last_insert_img();
    }

    @Override
    public PageBean findImg(Integer id,Integer type, String uploader, LocalDateTime time_begin, LocalDateTime time_end, Integer page, Integer pageSize) {
//        PageHelper.startPage(page,pageSize);
//        Set<Img> img_set = imgMapper.findAll(type, uploader, time_begin, time_end);
//        List<Img> img = new ArrayList<Img>(img_set);
//        Page<Img> pages = (Page<Img>) img;
//        PageBean pageBean = new PageBean((int)pages.getTotal(),pages.getResult());
//        return pageBean;
        //返回的类型不再是List需要手动实现分页功能
        // 1. 查询所有符合条件的数据
        Set<Img> imgSet = imgMapper.findAll(id,type, uploader, time_begin, time_end);

        // 2. 将 Set 转换为 List
        List<Img> imgList = new ArrayList<>(imgSet);

        // 3. 计算分页逻辑
        int total = imgList.size(); // 总记录数
        int fromIndex = Math.min((page - 1) * pageSize, total); // 起始索引
        int toIndex = Math.min(page * pageSize, total); // 结束索引

        // 4. 提取当前页的数据
        List<Img> pageList = imgList.subList(fromIndex, toIndex);

        // 5. 构造 PageBean 并返回
        PageBean pageBean = new PageBean(total, pageList);
        return pageBean;
    }
    @Transactional
    @Override
    public int deleteImg(List<Integer> ids) {
        return imgMapper.deleteById(ids)+imgMapper.deleteByDefId(ids);
    }

    @Override
    public Map<String,String> getSignature() {
        OssTest ot = new OssTest();
        try {
            Map<String,String> res = ot.PostObject();
            return res;
        } catch (Exception e) {
            log.info("获取签名失败");
            return null;
        }
    }
    @Scheduled(cron = "0 0 3 * * ?")//每天凌晨三点执行
    public void backup(){
        MYSQL_.backup();
    }

    @Override
    public int restore(LocalDate time) {
        return MYSQL_.restore(time);
    }
}
