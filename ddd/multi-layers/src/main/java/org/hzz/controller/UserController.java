package org.hzz.controller;

import org.hzz.domain.common.PageResult;
import org.hzz.domain.common.Result;
import org.hzz.domain.dto.UserDTO;
import org.hzz.domain.dto.UserQueryDTO;
import org.hzz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public Result<String> save( @Valid @RequestBody UserDTO userDTO) {
        userService.save(userDTO);
        return Result.success("新增用户成功");
    }


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
}
