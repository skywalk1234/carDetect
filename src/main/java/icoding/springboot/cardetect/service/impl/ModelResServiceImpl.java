package icoding.springboot.cardetect.service.impl;
import icoding.springboot.cardetect.pojo.ModelResponse;
import icoding.springboot.cardetect.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import icoding.springboot.cardetect.pojo.Defect;
import icoding.springboot.cardetect.service.DefectService;
import icoding.springboot.cardetect.service.ModelResService;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
@Slf4j
public class ModelResServiceImpl implements ModelResService {

    @Autowired
    private DefectService defectService;


    @Override
    public String sendQuest(String url)
    {
        try {
            // 创建 HttpClient 实例
            HttpClient client = HttpClient.newHttpClient();
            //到时候部署在容器内一定要改这个url
            String path = "http://localhost:8080/inspect_img?image_url="+url;
            // 创建 HttpRequest 实例
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(path))
                    .header("User-Agent", "backend server")
                    .GET()
                    .build();

            // 发送请求并获取响应
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 输出状态码和响应内容
            //System.out.println("Response Code: " + response.statusCode());
            log.info("状态码：{}",response.body());
            if(response.statusCode() == 200){
                return response.body();
            }else{
                return "响应出现错误";
            }
            //System.out.println("Response Body: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ModelResponse> parseQuestData(String json){
        List<ModelResponse> res = JsonUtil.parseJson(json, ModelResponse.class);
        /*
        //机器学习返回的json格式是这样的，需要先用ModelResponse这个类映射
        [
    {
        "ImageId_ClassId": "0002cc93b.jpg_1",
        "EncodedPixels":"29102 12 29346 24 29602 24 29858 24 30114 24 30370 24 30626 24"
    },
    {
        "ImageId_ClassId": "0002cc96b.jpg_2",
        "EncodedPixels":"29102 12 29346 24 29602 24 29858 24 30114 24 30370 24 30626 24"
    }
]
         */

        return res;
    }

    @Override
    public void processQuestData(Defect defect){
        defectService.addDefect(defect);
    }

}


