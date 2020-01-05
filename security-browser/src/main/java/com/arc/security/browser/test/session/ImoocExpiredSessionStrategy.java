package com.arc.security.browser.test.session;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: JZH
 * @Date: 2018/11/4 21:58
 * @Description:
 */
public class ImoocExpiredSessionStrategy implements SessionInformationExpiredStrategy {

    /**
     * @param eventØ session超时事件
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent eventØ) throws IOException, ServletException {
        HttpServletResponse response = eventØ.getResponse();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write("您的账号已在其他地点登陆");
    }
}
