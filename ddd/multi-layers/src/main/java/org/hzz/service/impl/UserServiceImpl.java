package org.hzz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.hzz.domain.common.PageQuery;
import org.hzz.domain.common.PageResult;
import org.hzz.domain.dto.UserDTO;
import org.hzz.domain.dto.UserQueryDTO;
import org.hzz.domain.entity.UserDO;
import org.hzz.mapper.UserMapper;
import org.hzz.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public int save(UserDTO userDTO) {
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userDTO,userDO);
        return userMapper.insert(userDO);
    }

    @Override
    public int update(Long id, UserDTO userDTO) {
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userDTO,userDO);
        userDO.setId(id);
        return userMapper.updateById(userDO);
    }

    @Override
    public int delete(Long id) {
        return userMapper.deleteById(id);
    }

    @Override
    public PageResult<List<UserDTO>> query(PageQuery<UserQueryDTO> pageQuery) {
        Page<UserDO> page = new Page<>(pageQuery.getPageNo(),pageQuery.getPageSize());
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(pageQuery.getQuery(),userDO);
        QueryWrapper<UserDO> queryWrapper = new QueryWrapper<>(userDO);

        Page<UserDO> userDOPage = userMapper.selectPage(page, queryWrapper);

        // 结果解析
        final PageResult<List<UserDTO>> userDTOPageResult = new PageResult<>();
        userDTOPageResult.setPageNo((int)userDOPage.getCurrent());
        userDTOPageResult.setPageSize((int)userDOPage.getSize());
        userDTOPageResult.setTotal(userDOPage.getTotal());
        userDTOPageResult.setPageNum(userDOPage.getPages());

        List<UserDTO> userDTOList = Optional.ofNullable(userDOPage.getRecords())
                .orElse(Collections.emptyList())
                .stream()
                .map(userDO1 -> {
                    UserDTO userDTO = new UserDTO();
                    BeanUtils.copyProperties(userDO1, userDTO);
                    return userDTO;
                })
                .collect(Collectors.toList());
        userDTOPageResult.setData(userDTOList);
        return userDTOPageResult;
    }

    @Override
    public UserDTO queryById(Long id) {
        UserDO userDO = userMapper.selectById(id);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userDO,userDTO);
        return userDTO;
    }


}
