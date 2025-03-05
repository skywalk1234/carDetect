package icoding.springboot.cardetect.service;/* I love coding */

import icoding.springboot.cardetect.pojo.Img;
import icoding.springboot.cardetect.pojo.PageBean;
import icoding.springboot.cardetect.pojo.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


public interface ImgService {
    public Img addImg(String url,String uploader);
    public PageBean findImg(Integer id,Integer type, String uploader, LocalDateTime time_begin, LocalDateTime time_end, Integer page, Integer pageSize);
    public int deleteImg(List<Integer> ids);
    //public Map<String,String> getSignature();
    public int restore(LocalDate time);
    public CompletableFuture<Result> processImageAsync(Integer imgId,String imageUrl);
    public int detect_img(Integer imgId,MultipartFile file);
}
