package icoding.springboot.cardetect.controller;/* I love coding */

import icoding.springboot.cardetect.pojo.CountRes;
import icoding.springboot.cardetect.pojo.Defect;
import icoding.springboot.cardetect.pojo.Result;
import icoding.springboot.cardetect.service.DefectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
@CrossOrigin(origins = "http://192.168.177.107")
@RestController
public class DefectController {
    @Autowired
    private DefectService defectService;
    @GetMapping("/flaw/img/{id}")
    //前端根据对应的图片id请求该图片所有的缺陷信息
    public Result getDefect(@PathVariable int id) {
        List<Defect> defectList = defectService.getDefect(id);
        if(defectList.isEmpty()){
            return Result.error("未找到记录");
        }else{
            return Result.success(defectList);
        }

    }
    @DeleteMapping("/img/{id}/flaw/{def_id}")
    public Result deleteDefect(@PathVariable int id, @PathVariable int def_id) {
        int num = defectService.deleteDefect(id, def_id);
        if(num > 0){
            return Result.success();
        }else{
            return Result.error("delete failed");
        }
    }

    @PostMapping("/add_flaw")
    public Result addDefect(@RequestBody Defect defect) {
        int num = defectService.addDefect(defect);
        if(num > 0){
            return Result.success(num);
        }else{
            return Result.error("add failed");
        }
    }
    @GetMapping("/count")
    public Result count(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime time_begin,@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime time_end) {
        List<CountRes> c =  defectService.getCount(time_begin, time_end);
        return Result.success(c);
    }
}
