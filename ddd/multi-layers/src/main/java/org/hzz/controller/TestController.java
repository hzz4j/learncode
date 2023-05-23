package org.hzz.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.hzz.domain.common.Result;
import org.hzz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@RestController
public class TestController implements TestApi{

    @Autowired
    HttpServletRequest request;

    @Operation(summary = "测试request属性注入与方法参数注入")
    @ApiResponse(responseCode = "200", description = "测试request属性注入与方法参数注入")
    @GetMapping("/request")
    public Result<List<String>> request(HttpServletRequest methodParamRequest){
        HttpServletRequest contextRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        System.out.println(this.request == methodParamRequest);
        System.out.println(this.request == contextRequest);
        System.out.println(methodParamRequest == contextRequest);

        return Result.success(
                Arrays.asList(
                        String.format("request: %s, methodParamRequest: %s, contextRequest: %s",
                                this.request, methodParamRequest, contextRequest),
                        String.format("request == methodParamRequest: %s, " +
                                "request == contextRequest: %s, " +
                                "methodParamRequest == contextRequest: %s",
                                this.request == methodParamRequest, this.request == contextRequest,methodParamRequest == contextRequest)
                )
        );

    }
}
