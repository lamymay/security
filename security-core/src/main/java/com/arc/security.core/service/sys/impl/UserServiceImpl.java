package com.arc.security.core.service.sys.impl;

import com.arc.security.core.service.sys.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * @author 叶超
 * @since 2019/5/15 9:03
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserDetails loadUserByUsername(String username) {
        return null;
    }
}
