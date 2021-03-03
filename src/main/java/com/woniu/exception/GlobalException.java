package com.woniu.exception;

import com.woniu.dto.Result;
import com.woniu.dto.StatusCode;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(Exception.class)
    public Result handlerAllException(){
        return new Result(false, StatusCode.Error,"未知异常");
    }

    @ExceptionHandler(UnknownAccountException.class)
    public Result handlerUnknownAccountException(){
        return new Result(false, StatusCode.UnknownAccount,"未知账户异常");
    }

    @ExceptionHandler(IncorrectCredentialsException.class)
    public Result handlerIncorrectCredentialsException(){
        return new Result(false, StatusCode.IncorrectCredentials,"凭证错误异常");
    }

}
