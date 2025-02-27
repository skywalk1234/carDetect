package icoding.springboot.cardetect.exception;/* I love coding */

import icoding.springboot.cardetect.pojo.Reminder;
import icoding.springboot.cardetect.pojo.Result;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.security.SignatureException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class CatchException {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public static Result exception() {
        return Result.error("该用户名已被注册");
    }
    @ExceptionHandler(WrongJwtException.class)
    public static Result wrongJwtException() {
        return Result.error("NOT_LOGIN");
    }
    @ExceptionHandler(IOException.class)
    public static Result ioe() {
        return Result.error("发生读写错误，可能是没有成功连接到数据库");
    }
}
