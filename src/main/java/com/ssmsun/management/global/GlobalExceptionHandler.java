package com.ssmsun.management.global;

import com.ssmsun.management.global.exception.*;
import com.ssmsun.management.util.json.Json;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理中心
 * 统一处理异常
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    public static final Integer SUCCESS = 2000;

    @ExceptionHandler({Exception.class})
    public Json<Void> handException(Throwable throwable){
        Json<Void> json = new Json<>(throwable.getMessage());

        if (throwable instanceof UserNotFoundException){
            json.setState(600);
        }
        if (throwable instanceof PasswordNotMatchException){
            json.setState(601);
        }
        if (throwable instanceof UsernameRepeatException){
            json.setState(602);
        }
        if (throwable instanceof VerifyGetFailException){
            json.setState(603);
        }
        if (throwable instanceof VerifyNotMatchException){
            json.setState(604);
        }
        if (throwable instanceof InsertUserException){
            json.setState(605);
        }
        if (throwable instanceof DeleteUserException){
            json.setState(606);
        }
        if (throwable instanceof InsertUserException){
            json.setState(607);
        }
        if (throwable instanceof SelectUserException){
            json.setState(608);
        }
        if (throwable instanceof FileEmptyException){
            json.setState(609);
        }
        if (throwable instanceof FileSizeException){
            json.setState(610);
        }
        if (throwable instanceof FileTypeException){
            json.setState(611);
        }
        if (throwable instanceof FileUploadIOException){
            json.setState(612);
        }


        return json;
    }

}
