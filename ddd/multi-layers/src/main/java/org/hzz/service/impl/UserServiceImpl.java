package org.hzz.service.impl;

import org.hzz.domain.common.PageQuery;
import org.hzz.domain.common.PageResult;
import org.hzz.domain.dto.UserDTO;
import org.hzz.domain.entity.UserDO;
import org.hzz.mapper.UserMapper;
import org.hzz.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public int save(UserDTO userDTO) {
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userDTO,userDO);
        int r = userMapper.insert(userDO);
        return r;
    }

    @Override
    public int update(Long id, UserDTO userDTO) {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public PageResult<List<UserDTO>> query(PageQuery<UserDTO> pageQuery) {
        return null;
    }

    @Override
    public UserDTO queryById(Long id) {
        UserDO userDO = userMapper.selectById(id);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userDO,userDTO);
        return userDTO;
    }


}
