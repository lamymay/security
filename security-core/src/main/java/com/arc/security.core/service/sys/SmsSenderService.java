package com.arc.security.core.service.sys;

/**
 * 短信发送服务
 *
 * @author 叶超
 * @since 2019/6/4 21:51
 */
public interface SmsSenderService {

    /**
     * 发送短信
     *
     * @param phone
     * @param msg
     * @return
     */
    boolean sendSms(long phone, String msg);
}
