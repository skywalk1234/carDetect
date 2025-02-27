package icoding.springboot.cardetect.utils;/* I love coding */

import icoding.springboot.cardetect.exception.WrongJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class Jwt {
    public static String generate_jwt(Map<String, Object> claims) {

        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, "goodmorningmyneighboorasdkfjaskldfjlkwejfkxsclkvshkejkht") // 使用生成的安全密钥签名
                .setClaims(claims) // 添加自定义声明
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 3600)) // 设置过期时间为1小时后
                .compact();
        return jwt;
    }
    public static Claims ParseJwt(String token) throws Exception {
        //String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwiZXhwIjoxNzM4ODMwNDIyfQ.krV-KLm2sidtro9b1yqsO_3z_s4ttdEs-KjdKXci_GE";
        Claims claim = Jwts.parser()
                .setSigningKey("goodmorningmyneighboorasdkfjaskldfjlkwejfkxsclkvshkejkht").build()
                .parseClaimsJws(token).getBody();
        return claim;
    }
}
