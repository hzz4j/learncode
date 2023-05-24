package org.hzz.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.hzz.domain.common.Result;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 由于在json的时候，取的是body，所以这里需要重写handleExceptionInternal方法
     * 默认的是直接设置为了null
     * json后的效果
     * {
     * 	"code": "405",
     * 	"msg": "Allow:\"GET\"",
     * 	"error": "405 method not allowed"
     * }
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.info("异常处理");
        if(body == null){
            body = Result.error(
                    status.toString().replaceAll("_"," ").toLowerCase(),
                    ""+status.value(),
                    headers.toString().substring(1, headers.toString().length()-1));
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }


    /**---------------------以下为定制的异常-------------------------------------------*/

    /**
     * 参数校验异常
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.info("校验失败");
        Map<String, Object> objectBody = new LinkedHashMap<>();
        objectBody.put("Current Timestamp", new Date());
        objectBody.put("Status", status.value());

        // Get all errors
        List<String> exceptionalErrors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getField()+":"+x.getDefaultMessage())
                .collect(Collectors.toList());

        objectBody.put("Errors", exceptionalErrors);

        return new ResponseEntity<>(objectBody, status);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
       logger.info("BindException");
        Map<String, Object> objectBody = new LinkedHashMap<>();
        objectBody.put("Current Timestamp", new Date());
        objectBody.put("Status", status.value());

        // Get all errors
        List<String> exceptionalErrors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getField()+":"+x.getDefaultMessage())
                .collect(Collectors.toList());

        objectBody.put("Errors", exceptionalErrors);

        return new ResponseEntity<>(objectBody, status);
    }

    /**---------------------以下是扩展的异常--------------------------------------*/
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public Result<String> handleMySelfException(Exception e, WebRequest request){
        String requestURI = ((ServletWebRequest) request).getRequest().getRequestURI();
        StringBuilder msgBuilder = new StringBuilder();
        msgBuilder.append("您请求的URI:"+requestURI+"发生了异常，异常信息在data中：");
        msgBuilder.append("如果这不是您的请求，请忽略。");
        msgBuilder.append("如果是您的请求，请联系管理员。Q10Viking，服务器发生异常，请联系管理员！hzz");

        return Result.error(e.toString(), ""+HttpStatus.INTERNAL_SERVER_ERROR.value(),msgBuilder.toString());
    }


}
