package com.arc.security.core.config.security.filter;

import com.arc.security.core.config.properties.arc.ArcSecurityProperties;
import com.arc.security.core.exception.VerifyCodeException;
import com.arc.security.core.model.system.ValidateCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 图形验证码过滤器
 *
 * @author 叶超
 * @since 2019/5/9 23:42
 */
@Slf4j
public class SmsValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {


    private PathMatcher pathMatcher = new AntPathMatcher();

    @Resource   private RedisTemplate<Object, Object> redisTemplate;

    @Resource
    private AuthenticationFailureHandler authenticationFailureHandler;


    private ArcSecurityProperties arcSecurityProperties;

    private Set<String> urls = new HashSet<>();

    @Override
    public void afterPropertiesSet() throws ServletException {
        //调用父类的实现
        super.afterPropertiesSet();
        String urlString = arcSecurityProperties.getCode().getImage().getUrl();
        if (urls != null && urlString != null) {
            urls.add("/authentication/cellphone");
            if (urlString.trim().length() > 0) {
                String[] split = urlString.split(",");
                if (split.length > 0) {
                    urls.addAll(Arrays.asList(split));
                }
            }
        }
        log.info("调用父类的实现后又去组装拦截的url集合，结果={}", urls.size());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        log.debug("{}方法请求的地址={}", request.getMethod(), requestURI);
        //只允许登录&POST请求，
        boolean action = false;
        for (String url : urls) {
            if (pathMatcher.match(url, requestURI)) {
                action = true;
            }
        }
        log.debug("请求的url匹配结果={}", action);
        if (action) {
            log.debug("#############################");
            boolean verifyCodeInRedis = false;
            try {
                verifyCodeInRedis = verifyImageCode(request);
                log.debug("verifyImageCode 传入的={}，比较结果={}", request, verifyCodeInRedis);
            } catch (VerifyCodeException e) {
                log.debug("authenticationFailureHandler 777777={}", authenticationFailureHandler);
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
            }
            if (!verifyCodeInRedis) {
                //不一致，结束，返回  则 throw new RuntimeException("验证码错误");
                authenticationFailureHandler.onAuthenticationFailure(request, response, new VerifyCodeException("验证码错误"));
                return;
            }
        }
        //否则继续让其他过滤器处理
        filterChain.doFilter(request, response);
    }

    private boolean verifyImageCode(HttpServletRequest request) {
        String verifyCodeInRequest = request.getParameter("smsCode");

        if (verifyCodeInRequest == null) {
            throw new VerifyCodeException("验证码为空");
        } else {
            log.debug("verifyCodeInRequest={}", verifyCodeInRequest);
            String redisValue;
            try {
                //尝试获取验证码
                String redisKey = null;
                if (request.getCookies() != null) {
                    for (Cookie cookie : request.getCookies()) {
                        if ("COOKIE_KEY_FOR_SMS".equalsIgnoreCase(cookie.getName())) {
                            redisKey = cookie.getValue();
                            break;
                        }
                    }
                }
                log.debug("redisKey={}",redisKey);
                Object obj = redisTemplate.opsForValue().get(redisKey);

                ValidateCode validateCode = (ValidateCode) obj;
                redisValue = validateCode.getCode();
                log.debug("validateCode which is in the request={}, redisKey={}, redisValue={}",validateCode,redisKey,redisValue);
            } catch (Exception e) {
                e.printStackTrace();
                throw new VerifyCodeException("验证码失效");
            }
            //获取redis中的值与前端传入的值比对
            if (verifyCodeInRequest.equals(redisValue)) {
                return true;
            } else {
                throw new VerifyCodeException("验证码错误");
            }
        }
    }

    public void setArcSecurityProperties(ArcSecurityProperties arcSecurityProperties) {
        this.arcSecurityProperties = arcSecurityProperties;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
