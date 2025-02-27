package icoding.springboot.cardetect.service;/* I love coding */

import icoding.springboot.cardetect.pojo.Img;
import icoding.springboot.cardetect.pojo.PageBean;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


public interface ImgService {
    public Img addImg(String url,String uploader);
    public PageBean findImg(Integer id,Integer type, String uploader, LocalDateTime time_begin, LocalDateTime time_end, Integer page, Integer pageSize);
    public int deleteImg(List<Integer> ids);
    public Map<String,String> getSignature();
    public int restore(LocalDate time);
}
