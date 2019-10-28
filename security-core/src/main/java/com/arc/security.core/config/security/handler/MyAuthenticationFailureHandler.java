package com.arc.security.core.config.security.handler;

import com.arc.core.model.vo.ResponseVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 失败处理器
 * 方法中的第三个参数Authentication，它里面包含了登录后的用户信息（UserDetails），Session的信息，登录信息等
 *
 * @author 叶超
 * @since 2019/6/2 17:38
 */
@Component("myAuthenticationFailureHandler")
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        log.info("登录失败");
        log.info("登录失败:" + exception.getMessage());
        log.info("username=>" + request.getParameter("username"));
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(ResponseVo.success(exception.getMessage())));
    }
}
