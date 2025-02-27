package icoding.springboot.cardetect.controller;/* I love coding */

import icoding.springboot.cardetect.pojo.Result;
import icoding.springboot.cardetect.pojo.User;
import icoding.springboot.cardetect.pojo.User2;
import icoding.springboot.cardetect.service.UserService;
import icoding.springboot.cardetect.utils.Jwt;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static icoding.springboot.cardetect.interceptor.CheckInterceptor.current_username;


@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        User u = userService.login(user.getUsername(),user.getPassword());
        if(u!=null){
            Map<String,Object> map = new HashMap<>();
            map.put("username",u.getUsername());
            String jwt = Jwt.generate_jwt(map);
            return Result.success(jwt);
        }else{
            return Result.error("用户名或密码错误");
        }
    }
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        int num = userService.register(user.getUsername(),user.getPassword());
        return Result.success(num);
    }

    @PostMapping("/update_info")
    public Result update(@RequestBody User2 user) {
        userService.update_info(user.getNew_username(),user.getOld_username(),user.getPassword());
        Map<String,Object> map = new HashMap<>();
        map.put("username",user.getNew_username());
        String jwt = Jwt.generate_jwt(map);
        log.info("生成新的令牌");
        return Result.success(jwt);
    }
    @GetMapping("/user-info")
    public Result check(){
        return Result.success(current_username);
    }
}
