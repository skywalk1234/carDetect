package icoding.springboot.cardetect.controller;/* I love coding */

import icoding.springboot.cardetect.pojo.Img;
import icoding.springboot.cardetect.pojo.PageBean;
import icoding.springboot.cardetect.pojo.Result;
import icoding.springboot.cardetect.service.ImgService;
//import icoding.springboot.cardetect.utils.OssUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static icoding.springboot.cardetect.interceptor.CheckInterceptor.current_username;
@CrossOrigin(origins = "http://192.168.177.107:8080")
@RestController
@Slf4j
public class ImgController {
//    @Autowired
//    private OssUtils ossUtils;
    @Autowired
    private ImgService imgService;
//    @GetMapping("/get_signature")
//    public Result getSignature() {
//        Map<String,String> res = imgService.getSignature();
//        return Result.success(res);
//    }

//    @PostMapping("/upload_img")
//    public Result uploadImg(@RequestParam("image") List<MultipartFile> images) throws IOException {
//        for (MultipartFile file : images) {
//            String url = ossUtils.upload(file);
//            if(url != null) {
//                log.info("文件上传成功，路径：{}",url);
//                return Result.success(url);
//            }else{
//                return Result.error("上传失败");
//            }
//        }
//        return Result.success();
//        //String url = ossUtils.upload(image);
//    }
    //前端发送检测请求
    @GetMapping("/inspect")
    public Result inspect(String image_url){
        Img img = imgService.addImg(image_url,current_username);
        //这里的img已经是有id的了
        CompletableFuture<Result> future = imgService.processImageAsync(img.getId(),image_url);//这一步是异步执行的，不会阻塞主线程

        return Result.success(img);
    }
    @GetMapping("/records")
    public Result search(Integer id,Integer type, String uploader, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime time_begin, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime time_end, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize){
        PageBean pb = imgService.findImg(id,type, uploader, time_begin, time_end, page, pageSize);
        log.info("total:{}",pb.getTotal());
        if(pb.getTotal()>0){
            return Result.success(pb);
        }else{
            return Result.error("success");
        }
    }
    @DeleteMapping("/records/{ids}")
    public Result deleteImg(@PathVariable List<Integer> ids){
        int num = imgService.deleteImg(ids);
        if(num>0){
            return Result.success("delete successfully");
        }else{
            return Result.error("删除发生错误");
        }
    }
    @GetMapping("/download/{id}")
    public void downloadImg(@PathVariable Integer id){}
    @GetMapping("/backup")
    public Result backupImg(LocalDate time){
        int status = imgService.restore(time);
        if(status==1){
            return Result.success();
        }else if (status==-1){
            return Result.error("找不到该日期的备份文件");
        }else{
            return Result.error("数据备份失败");
        }
    }
}
