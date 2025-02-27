package icoding.springboot.cardetect.pojo;/* I love coding */

public class Reminder {
    public static Result repeat_username(){
        return Result.error("该用户名已被注册");
    }

}
