package com.arc.security.core.model.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 封装登录信息
 * 登录前是用户信息
 * 登录后是认证信息
 *
 * @author 叶超
 * @since 2019/6/9 23:08
 */
public class SmsAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 1L;
    /**
     * 登录前principal是用户手机号码
     * 登录前principal是用户信息
     */
    private final Object principal;

    /**
     * 这个构造器
     * this constantor can be safety used by any code that wishes to create a  SmsAuthenticationToken as the {@link #isAuthenticated()}
     * will return <code>false</code>
     * 登录前principal是用户手机号码
     *
     * @param cellphone 手机号码
     */
    public SmsAuthenticationToken(Object cellphone) {
        super((Collection) null);
        this.principal = cellphone;
        this.setAuthenticated(false);
    }

    /**
     * 登录成功后放入登录成功的用户的信息
     *
     * @param principal
     * @param authorities
     */
    public SmsAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        //must use super ,as we override
        super.setAuthenticated(true);
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }

    /**
     * 获取密码
     *
     * @return null
     */
    @Override
    public Object getCredentials() {
        return null;
    }
}
