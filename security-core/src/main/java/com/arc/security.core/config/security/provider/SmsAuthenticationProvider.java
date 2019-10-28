package com.arc.security.core.config.security.provider;

import com.arc.security.core.model.authentication.SmsAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author 叶超
 * @since 2019/6/9 23:37
 */
public class SmsAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    /**
     * 获取用户信息
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsAuthenticationToken authenticationToken = (SmsAuthenticationToken) authentication;
        //拿到手机号去查用户信息
        UserDetails user = userDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());
        if (user == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息，case SmsAuthenticationProvider");
        }
        //认证成功的人信息 是否认证的属性被设置为true了         super.setAuthenticated(true);
        SmsAuthenticationToken passedAuthenticationToken = new SmsAuthenticationToken(user,user.getAuthorities());
        passedAuthenticationToken.setDetails(authenticationToken.getDetails());
        return passedAuthenticationToken;
    }

    /**
     * 是不是这种类型 SmsAuthenticationToken
     *
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return SmsAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
