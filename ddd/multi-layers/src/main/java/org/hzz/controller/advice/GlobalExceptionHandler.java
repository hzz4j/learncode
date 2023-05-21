package org.hzz.controller.advice;

import org.hzz.domain.common.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

//@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e, WebRequest request){
        String requestURI = ((ServletWebRequest) request).getRequest().getRequestURI();
        StringBuilder msgBuilder = new StringBuilder();
        msgBuilder.append("您请求的URI:"+requestURI+"发生了异常，异常信息在data中：");
        msgBuilder.append("如果这不是您的请求，请忽略。");
        msgBuilder.append("如果是您的请求，请联系管理员。Q10Viking，服务器发生异常，请联系管理员！hzz");

        return Result.error(e.toString(), ""+HttpStatus.INTERNAL_SERVER_ERROR.value(),msgBuilder.toString());
    }
}
