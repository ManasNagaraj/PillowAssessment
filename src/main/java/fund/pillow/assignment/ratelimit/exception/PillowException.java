package fund.pillow.assignment.ratelimit.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class PillowException extends Exception{

    private static final long serialVersionUID = 1342342442L;

    private String code;

    private HttpStatus httpStatus;

    public PillowException(String code,String message,HttpStatus httpStatus){
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public PillowException(String code,String message){
        super(message);
        this.code = code;
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }
}
