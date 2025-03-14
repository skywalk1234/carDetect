package icoding.springboot.cardetect.service.impl;/* I love coding */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import icoding.springboot.cardetect.mapper.DefectMapper;
import icoding.springboot.cardetect.mapper.ImgMapper;
import icoding.springboot.cardetect.pojo.*;
import icoding.springboot.cardetect.service.ImgService;
import icoding.springboot.cardetect.service.ModelResService;
import icoding.springboot.cardetect.utils.MYSQL_;
//import icoding.springboot.cardetect.utils.OssTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class ImgServiceImpl implements ImgService {
    @Autowired
    private ImgMapper imgMapper;
    @Autowired
    private DefectMapper defectMapper;
    @Autowired
    private ModelResService modelResService;

    @Override
    public Img addImg(String url, String uploader) {
        Img img = new Img();


        img.setImage(url);
        img.setInspect_time(LocalDateTime.now());
        img.setUploader(uploader);
        imgMapper.insert(img);//这是将图片信息写入到数据库的imgs表中


        return imgMapper.get_last_insert_img();
    }

    @Async("taskExecutor") //新建一个线程采用异步执行
    @Override
    public CompletableFuture<Result> processImageAsync(Integer imgId, String imageUrl) {
//这个功能废弃了，改成下面那个函数
        //        try {
//            //1.调用ModelResServiceImpl中的相关方法与机器学习的模型交互，并拿到对应的解析完的数据
//            log.info("开始处理。。。。。");
//            //Thread.sleep(5000);//模拟一个耗时操作
//            // 2. 调用defectMapper中的insert方法将数据写入到defect这张表中
//            String res_json = modelResService.sendQuest(imageUrl);//发送请求并拿到响应的json
//            List<ModelResponse> res = modelResService.parseQuestData(res_json);//解析json格式
//
//            for(ModelResponse m : res) {
//                //提取列表中每一个的结果构建defect实例
//                Defect defect = new Defect();
//                String img_name = m.getImageId_ClassId();//提取图片名字的最后一个字符（就是缺陷类型）
//                int type = img_name.charAt(img_name.length()-1)-'0';//将字符转成数字
//                defect.setType(type);
//                defect.setImgId(imgId);
//                defect.setPosition(m.getEncodedPixels());
//                defect.setCreateTime(LocalDateTime.now());
//                defect.setSource("machine");
//                modelResService.processQuestData(defect);
//            }
//            // 3. 返回成功响应
//            return CompletableFuture.completedFuture(Result.success());
//        } catch (Exception e) {
//            return CompletableFuture.failedFuture(e);
//        }
        return null;
    }

    @Override
    public List<Double> parsePosition(String positionStr) {
        String[] parts = positionStr.split(" ");
        List<Double> position = new ArrayList<>();
        for (String part : parts) {
            position.add(Double.parseDouble(part));
        }
        return position;
    }
    @Override
    public String convertToJsonString(List<List<Double>> positions) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[");

        for (int i = 0; i < positions.size(); i++) {
            List<Double> position = positions.get(i);
            jsonBuilder.append("[");

            for (int j = 0; j < position.size(); j++) {
                jsonBuilder.append(position.get(j));
                if (j < position.size() - 1) {
                    jsonBuilder.append(",");
                }
            }

            jsonBuilder.append("]");
            if (i < positions.size() - 1) {
                jsonBuilder.append(",");
            }
        }

        jsonBuilder.append("]");
        return jsonBuilder.toString();
    }

    @Override
    public int detect_img(Integer imgId, MultipartFile file){
        String res_json = modelResService.sendQuest(file);//发送请求并拿到响应的json
        //测试用
//        String res_json = "[\n" +
//                "  {\n" +
//                "    \"image\": \"crazing_5.jpg\",\n" +
//                "    \"type\": 2,\n" +
//                "    \"position\": \"0.7400 0.6675 0.5200 0.3550\"\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"image\": \"crazing_5.jpg\",\n" +
//                "    \"type\": 0,\n" +
//                "    \"position\": \"0.4850 0.7250 0.9700 0.4700\"\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"image\": \"crazing_5.jpg\",\n" +
//                "    \"type\": 1,\n" +
//                "    \"position\": \"0.5225 0.4775 0.9550 0.3650\"\n" +
//                "  }]";


            //处理响应的字符串
            try{
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(res_json);

                // 获取results节点
                JsonNode resultsNode = rootNode.path("results");

                // 将JsonNode转换为字符串
                String resultsStr = resultsNode.toPrettyString();

                //System.out.println(resultsStr);

                System.out.println(resultsStr);
                List<ModelResponse> res = modelResService.parseQuestData(resultsStr);
                //ModelResponse类就是负责跟json里面的字段作映射的
                //合并相同文件且相同缺陷的position然后给defect
                //调用modelResService.processQuestData(defect)写入

                Map<Integer, List<List<Double>>> map = new HashMap<>();

                for (ModelResponse m : res) {
                    int type = m.getType();
                    String positionStr = m.getPosition();
                    List<Double> position = parsePosition(positionStr);
                    map.compute(type, (key, existingPositions) -> {
                        if (existingPositions == null) {
                            List<List<Double>> newPositions = new ArrayList<>();
                            newPositions.add(position);
                            return newPositions;
                        } else {
                            existingPositions.add(position);
                            return existingPositions;
                        }
                    });
                }

                map.forEach((type, positions) -> {
                    Defect defect = new Defect();
                    defect.setType(type);
                    defect.setImg_id(imgId);
                    String positionJson = convertToJsonString(positions);
                    defect.setPosition(positionJson);
                    defect.setCreate_time(LocalDateTime.now());
                    defect.setSource("machine");
                    modelResService.processQuestData(defect);
                });
            }catch(Exception e){
                e.printStackTrace();
            }


        return 0;
    }

    @Override
    public PageBean findImg(Integer id, Integer type, String uploader, LocalDateTime time_begin, LocalDateTime time_end, Integer page, Integer pageSize) {
//        PageHelper.startPage(page,pageSize);
//        Set<Img> img_set = imgMapper.findAll(type, uploader, time_begin, time_end);
//        List<Img> img = new ArrayList<Img>(img_set);
//        Page<Img> pages = (Page<Img>) img;
//        PageBean pageBean = new PageBean((int)pages.getTotal(),pages.getResult());
//        return pageBean;
        //返回的类型不再是List需要手动实现分页功能
        // 1. 查询所有符合条件的数据
        Set<Img> imgSet = imgMapper.findAll(id, type, uploader, time_begin, time_end);

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
        return imgMapper.deleteById(ids) + imgMapper.deleteByDefId(ids);
    }

    //    @Override
//    public Map<String,String> getSignature() {
//        OssTest ot = new OssTest();
//        try {
//            Map<String,String> res = ot.PostObject();
//            return res;
//        } catch (Exception e) {
//            log.info("获取签名失败");
//            return null;
//        }
//    }
    @Scheduled(cron = "0 0 3 * * ?")//每天凌晨三点执行
    public void backup() {
        MYSQL_.backup();
    }

    @Override
    public int restore(LocalDate time) {
        return MYSQL_.restore(time);
    }
}
