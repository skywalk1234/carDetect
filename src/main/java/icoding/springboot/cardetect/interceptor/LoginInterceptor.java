package icoding.springboot.cardetect.interceptor;/* I love coding */

import com.alibaba.fastjson.JSONObject;
import icoding.springboot.cardetect.exception.CatchException;
import icoding.springboot.cardetect.exception.WrongJwtException;
import icoding.springboot.cardetect.pojo.Result;
import icoding.springboot.cardetect.utils.Jwt;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String jwt = request.getHeader("token");
        if(!StringUtils.hasLength(jwt)){
            Result error = Result.error("NOT_LOGIN");
            String notLogin = JSONObject.toJSONString(error);
            response.getWriter().write(notLogin);
            return false;
        }
        try{
            Claims claim= Jwt.ParseJwt(jwt);
//            current_username = claim.get("username").toString();
//            log.info("username:{}",current_username);
        }catch(Exception e){
            throw new WrongJwtException("令牌错误");
            //CatchException.wrongJwtException();
            //return false;
        }

        log.info("令牌验证通过，放行");
        return true;
    }
}
