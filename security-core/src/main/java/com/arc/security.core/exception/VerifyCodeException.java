package com.arc.security.core.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author 叶超
 * @since 2019/6/2 17:45
 */
public class VerifyCodeException extends AuthenticationException {

    public VerifyCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public VerifyCodeException(String msg) {
        super(msg);
    }
}
