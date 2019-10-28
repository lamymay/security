package com.arc.security.core.service.sys.impl;

import com.arc.security.core.service.sys.SmsSenderService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 叶超
 * @since 2019/6/9 13:52
 */
@Slf4j
public class DefaultSmsSenderServiceImpl implements SmsSenderService {

    @Override
    public boolean sendSms(long phone, String msg) {
        log.debug("#############################################################");
        log.info("开发中假装向{}发送了短信：{}", phone, msg);
        log.debug("#############################################################");
        return true;
    }

}
