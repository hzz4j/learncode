package org.hzz.controller;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.NotImplementedException;
import org.hzz.domain.common.PageResult;
import org.hzz.domain.common.Result;
import org.hzz.domain.dto.UserDTO;
import org.hzz.domain.dto.UserQueryDTO;
import org.hzz.domain.vo.UserVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequestMapping("/user")
@Tag(name = "用户管理",description = "用户管理(增删改查)",
        externalDocs = @ExternalDocumentation(url = "https://q10viking.github.io",description = "我的博客"))
@Validated
public interface UserApi {

    @Operation(summary = "查询用户信息", description = "根据用户id查询用户信息")
    @ApiResponse(responseCode = "200", description = "查询成功")
    @GetMapping("/queryById")
    default Result<UserVO> queryById(
            @Parameter(description = "用户id",required = true,example = "1220708537638920191")
            @Valid @NotNull @RequestParam(name="userid",required = true)
            String userId){
        throw new NotImplementedException("接口未实现");
    }

    @Operation(summary = "分页查询", description = "分页查询用户信息")
    @ApiResponse(responseCode = "200", description = "分页查询成功")
    @GetMapping("/query")
    default Result<PageResult<List<UserVO>>> query(
            @Parameter(description = "当前页(默认为1)",example = "1")
            @RequestParam(name = "pageNum",defaultValue = "1",required = false)
            Integer pageNum,
            @Parameter(description = "每页显示条数(默认为20)",example = "20")
            @RequestParam(name = "pageSize",defaultValue = "20",required = false)
            Integer pageSize,
            @Parameter(description = "用户查询条件")
            @Valid UserQueryDTO userQueryDTO) {
        throw new NotImplementedException("接口未实现");
    }


    @Operation(summary = "保存用户信息", description = "保存用户信息到数据库")
    @ApiResponse(responseCode = "200", description = "保存成功"
            , content = {@Content(mediaType = "application/json")})
    @PostMapping("/save")
    default  Result<String> save(
            @Parameter(description = "用户信息",required = true)
            @Valid @RequestBody UserDTO userDTO) {
        throw new NotImplementedException("接口未实现");
    }

    @Operation(summary = "更新用户信息", description = "更新用户信息到数据库")
    @ApiResponse(responseCode = "200", description = "更新成功")
    @PostMapping("/update/{id}")
    default Result<String> update(
            @Parameter(description = "用户id",required = true,example = "1220708537638920191")
            @PathVariable("id") Long userId,
            @Parameter(description = "用户信息",required = true)
            @Valid @RequestBody UserDTO userDTO) {
        throw new NotImplementedException("接口未实现");
    }

    @Operation(summary = "删除用户信息", description = "删除用户信息到数据库")
    @ApiResponse(responseCode = "200", description = "删除成功")
    @DeleteMapping("/delete/{id}")
    default Result<String> delete(
            @Parameter(description = "用户id",required = true,example = "1220708537638920191")
            @NotNull @PathVariable("id") Long userId) {
        throw new NotImplementedException("接口未实现");
    }

}
