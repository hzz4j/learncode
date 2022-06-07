package org.hzz.service.impl;

import org.hzz.bean.User;
import org.hzz.mapper.UserMapper;
import org.hzz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByName(String name) {
        return userMapper.getByUsername(name);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User dbUser = getUserByName(username);

        // pass word 123456
        org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(
                dbUser.getUsername(), dbUser.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("admin")
        );

        return user;
    }
}
