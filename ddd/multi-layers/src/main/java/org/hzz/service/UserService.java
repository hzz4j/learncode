package org.hzz.service;


import org.hzz.domain.common.PageQuery;
import org.hzz.domain.common.PageResult;
import org.hzz.domain.dto.UserDTO;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

public interface UserService {

    int save(@Validated UserDTO userDTO);

    int update(Long id,UserDTO userDTO);

    int delete(Long id);

    PageResult<List<UserDTO>> query(PageQuery<UserDTO> pageQuery);
}
