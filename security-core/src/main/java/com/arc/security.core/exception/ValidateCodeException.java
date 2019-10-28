package com.arc.security.core.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author may
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
