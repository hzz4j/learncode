package org.hzz.controller;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.hzz.domain.common.PageResult;
import org.hzz.domain.common.Result;
import org.hzz.domain.dto.UserDTO;
import org.hzz.domain.dto.UserQueryDTO;
import org.hzz.domain.vo.UserVO;
import org.hzz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController implements UserApi {

    @Autowired
    private UserService userService;




    @PostMapping("/update/{id}")
    public Result<String> update(
            @PathVariable("id") Long userId,
            @RequestBody UserDTO userDTO) {

        return Result.success("更新用户成功");
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable("id") Long userId) {

        return Result.success("删除用户成功");
    }

    @GetMapping("/query")
    public Result<PageResult> query(Integer pageNum, Integer pageSize,
                                    UserQueryDTO userQueryDTO) {

        return Result.success(new PageResult());
    }

    @GetMapping("/hello")
    public Result<String> hello() {
        return Result.success("hello111");
    }


    @GetMapping("/exception")
    public Result<String> exception() {
        throw new RuntimeException("测试异常");
    }

    @Override
    public Result<UserVO> queryById(Long userId) {
        System.out.println(userId);
        return null;
    }
}
