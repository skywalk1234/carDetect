package icoding.springboot.cardetect.exception;/* I love coding */

public class WrongJwtException extends Exception{
    public WrongJwtException(String message){
        super(message);
    }
}
