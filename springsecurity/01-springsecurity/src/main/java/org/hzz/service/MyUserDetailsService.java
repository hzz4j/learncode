package org.hzz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        String pw = BCrypt.hashpw("root.123456",BCrypt.gensalt());
//        UserDetails userDetails = new User("hzz","{bcrypt}"+pw,
//                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,user"));

        String pw = "root.123456";
        UserDetails userDetails = new User("hzz",passwordEncoder.encode(pw),
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,user"));
        return userDetails;
    }
}
