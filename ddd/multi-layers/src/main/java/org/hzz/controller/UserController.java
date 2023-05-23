package org.hzz.controller;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.apache.commons.lang3.StringUtils;
import org.hzz.domain.common.PageQuery;
import org.hzz.domain.common.PageResult;
import org.hzz.domain.common.Result;
import org.hzz.domain.dto.UserDTO;
import org.hzz.domain.dto.UserQueryDTO;
import org.hzz.domain.vo.UserVO;
import org.hzz.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
public class UserController implements UserApi {

    @Autowired
    private UserService userService;

    @Override
    public Result<String> save(UserDTO userDTO) {
        int save = userService.save(userDTO);
        if(save == 1){
            return Result.success("保存用户成功");
        }
        throw new RuntimeException("保存用户失败");
    }

    @Override
    public Result<String> update(
            @PathVariable("id") Long userId,
            @RequestBody UserDTO userDTO) {
        int updateCount = userService.update(userId, userDTO);
        if(updateCount == 0){
            throw new RuntimeException("更新用户失败");
        }
        return Result.success("更新用户成功");
    }

    @Override
    public Result<String> delete(@PathVariable("id") Long userId) {
        int deleteCount = userService.delete(userId);
        if(deleteCount == 0){
            throw new RuntimeException("删除用户失败");
        }
        return Result.success("删除用户成功");
    }

    @Override
    public Result<PageResult<List<UserVO>>> query(Integer pageNum, Integer pageSize,
                                                  UserQueryDTO userQueryDTO) {
        PageQuery<UserQueryDTO> pageQuery = new PageQuery<>(pageNum, pageSize, userQueryDTO);
        PageResult<List<UserDTO>> userDTOPageResult = userService.query(pageQuery);

        PageResult<List<UserVO>> userVOPageResult = new PageResult<>();
        BeanUtils.copyProperties(userDTOPageResult,userVOPageResult);

        List<UserVO> userVOList = Optional.ofNullable(userDTOPageResult.getData())
                .orElse(Collections.emptyList())
                .stream()
                .map(userDTO -> {
                    UserVO userVO = new UserVO();
                    BeanUtils.copyProperties(userDTO, userVO);
                    return userVO;
                })
                .collect(Collectors.toList());

        userVOPageResult.setData(userVOList);
        return Result.success(userVOPageResult);
    }

    @Override
    public Result<UserVO> queryById(String userId) {
        UserDTO userDTO = userService.queryById(Long.parseLong(userId));
        UserVO userVO = new UserVO();
        userVO.setId(Long.parseLong(userId));
        userVO.setUsername(userDTO.getUsername());
        userVO.setPassword(StringUtils.repeat("*",userDTO.getPassword().length()));
        return Result.success(userVO);
    }
}
