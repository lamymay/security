package com.arc.security.core.service.security;

import com.arc.security.core.model.system.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 生成验证码
 *
 * @author 叶超
 * @since 2019/6/2 19:17
 */
public interface ValidateCodeGenerator {

    /**
     * 生成验证码
     *
     * @param request ServletWebRequest
     * @return ValidateCode
     */
    ValidateCode generateVerifyCode(ServletWebRequest request);

}




/*    *//**
 * 生成随机数作为验证码
 *
 * @param length
 * @return 生成随机数作为验证码
 * @param contentSize
 * @return 生成验证码
 * @param contentSize
 * @return 生成随机数作为验证码
 * @param contentSize
 * @return 生成验证码
 * @param contentSize
 * @return
 *//*
    @Deprecated
    Integer getRandomVerifyCodeAsNumber(int length);

    *//**
 * 生成随机数作为验证码
 *
 * @param contentSize
 * @return
 *//*
    @Deprecated
    String getRandomVerifyCode(int contentSize);

    *//**
 * 生成验证码
 *
 * @param contentSize
 * @return
 *//*
    @Deprecated
    ValidateCode generateVerifyCode(int contentSize);*/
