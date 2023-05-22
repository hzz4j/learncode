package org.hzz.controller;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.NotImplementedException;
import org.hzz.domain.common.Result;
import org.hzz.domain.dto.UserDTO;
import org.hzz.domain.vo.UserVO;
import org.springframework.web.bind.annotation.*;

import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RequestMapping("/user")
@Tag(name = "用户管理",description = "用户管理(增删改查)",
        externalDocs = @ExternalDocumentation(url = "https://q10viking.github.io",description = "我的博客"))
@Validated
public interface UserApi {

    @Operation(summary = "查询用户信息",description = "根据用户id查询用户信息")
    @GetMapping("/queryById")
    default Result<UserVO> queryById(
            @Valid @NotNull @RequestParam(name="userid",required = false)
            Long userId){
        throw new NotImplementedException("接口未实现");
    }


    @Operation(summary = "保存用户信息",description = "保存用户信息到数据库")
    @PostMapping("/save")
    default public Result<String> save(@Valid @RequestBody UserDTO userDTO) {
        throw new NotImplementedException("接口未实现");
    }

}
