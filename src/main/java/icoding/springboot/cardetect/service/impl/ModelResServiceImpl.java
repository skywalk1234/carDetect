package icoding.springboot.cardetect.service.impl;
import icoding.springboot.cardetect.pojo.ModelResponse;
import icoding.springboot.cardetect.utils.JsonUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import icoding.springboot.cardetect.pojo.Defect;
import icoding.springboot.cardetect.service.DefectService;
import icoding.springboot.cardetect.service.ModelResService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

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
    //

    @Override
    public String sendQuest(MultipartFile file)
    {
        log.info("文件大小{}",file.getSize());
        log.info("准备发送请求");
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost uploadFile = new HttpPost("http://ml:8000/inspect_img");

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody(
                    "files", // 确保这个字段名与服务器端接收的一致
                    file.getBytes(),
                    ContentType.create("image/form-data"),
                    file.getOriginalFilename()
            );

            HttpEntity multipart = builder.build();
            uploadFile.setEntity(multipart);

            log.info("发送请求");
            try (CloseableHttpResponse response = httpClient.execute(uploadFile)) {
                HttpEntity responseEntity = response.getEntity();
                if (response.getStatusLine().getStatusCode() == 200) {
                    log.info("响应成功");
                    return EntityUtils.toString(responseEntity, "UTF-8"); // 返回响应体
                } else {
                    log.error("响应失败，状态码：{}", response.getStatusLine().getStatusCode());
                    String errorMessage = EntityUtils.toString(responseEntity, "UTF-8");
                    log.error("响应消息：{}", errorMessage);
                    return "响应出现错误: " + errorMessage;
                }
            }
        } catch (Exception e) {
            log.error("Error occurred while uploading file.", e);
            return "发生异常: " + e.getMessage();
        }
    }

    @Override
    public List<ModelResponse> parseQuestData(String json){
        List<ModelResponse> res = JsonUtil.parseJson(json, ModelResponse.class);
        /*
        //机器学习返回的数据是这样的
        [
  {
    "image": "crazing_2.jpg",
    "type": 0,
    "position": "0.7400 0.6675 0.5200 0.3550"
  },
  {
    "image": "crazing_5.jpg",
    "type": 0,
    "position": "0.4850 0.7250 0.9700 0.4700"
  },
  {
    "image": "crazing_5.jpg",
    "type": 0,
    "position": "0.5225 0.4775 0.9550 0.3650"
  }


         */
        return res;
    }

    @Override
    public void processQuestData(Defect defect){
        defectService.addDefect(defect);
    }


}


