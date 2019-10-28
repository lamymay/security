package com.arc.security.core.exception;

import javax.naming.AuthenticationException;

/**
 * @author 叶超
 * @since 2019/6/2 17:45
 */
public class PasswordNotMathException extends AuthenticationException {
    public PasswordNotMathException(String explanation) {
        super(explanation);
    }

    public PasswordNotMathException() {
        super();
    }
}
