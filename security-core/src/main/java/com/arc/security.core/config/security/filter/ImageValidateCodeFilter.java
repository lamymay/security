package com.arc.security.core.config.security.filter;

import com.arc.security.core.config.properties.ArcSecurityProperties;
import com.arc.security.core.exception.VerifyCodeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

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
public class ImageValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {


    private PathMatcher pathMatcher = new AntPathMatcher();

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

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
            urls.add(arcSecurityProperties.getBrowser().getLoginProcessingUrl());
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
        //if (action && RequestMethod.POST.toString().equalsIgnoreCase(request.getMethod())) {
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
        String verifyCodeInRequest = request.getParameter("verifyCode");

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
                        if (arcSecurityProperties.getBrowser().getCookieKey().equalsIgnoreCase(cookie.getName())) {
                            redisKey = cookie.getValue();
                            break;
                        }
                    }
                }
                redisValue = (String) redisTemplate.opsForValue().get(redisKey);
                log.debug("验证码 redisValue={}", redisValue);
                log.debug("The cookieValue is redisKey={},redisValue={}", redisKey, redisValue);
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

    private static Jedis getJedis() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);
        config.setMaxIdle(10);
        config.setMinIdle(2);
        config.setBlockWhenExhausted(true); //资源耗尽时是否阻塞
        config.setTestOnBorrow(false);
        config.setTestOnReturn(false);
        JedisPool pool = new JedisPool(config, "127.0.0.1", 6379);

        redis.clients.jedis.Jedis jedis = pool.getResource();
        log.debug("//////////////////////////////////////////////////");
        log.debug("结果={}", jedis);
        log.debug("//////////////////////////////////////////////////");
        return jedis;
    }

    public static void main(String[] args) {
        String method = "POST";
        boolean post = RequestMethod.POST.toString().equals(method);//true
        System.out.println(post);
        System.out.println("".trim().length());
        System.out.println("     ".trim().length());
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
