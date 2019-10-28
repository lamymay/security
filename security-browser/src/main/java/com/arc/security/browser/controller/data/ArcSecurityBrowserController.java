//package com.arc.security.browser.controller.data;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.DefaultRedirectStrategy;
//import org.springframework.security.web.RedirectStrategy;
//import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
//import org.springframework.security.web.savedrequest.RequestCache;
//import org.springframework.security.web.savedrequest.SavedRequest;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestController;
//import org.thymeleaf.util.StringUtils;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.time.LocalDateTime;
//
///**
// * @author 叶超
// * @since: 2019/5/8 22:05
// */
//@Slf4j
//@RestController
//public class ArcSecurityBrowserController {
//
//    @Autowired
//    private ArcSecurityProperties arcSecurityProperties;
//
//    private RequestCache cacheRequest = new HttpSessionRequestCache();
//
//    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
//
//    /**
//     * 需身份认证时候，跳转到这里
//     * 状态码返回 @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
//     */
//    @GetMapping(value = "/auth/require")
//    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
//    public Object get(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        SavedRequest savedRequest = cacheRequest.getRequest(request, response);
//        //引发跳转的请求
//        if (savedRequest != null) {
//            String redirectUrl = savedRequest.getRedirectUrl();
//            log.info("时间={} 实际请求的资源地址URL={}", LocalDateTime.now(), redirectUrl);
//            if (StringUtils.endsWith(redirectUrl, "html")) {
//                log.debug("### getLoginUrl={}", arcSecurityProperties.getBrowser().getLoginUrl());
//                redirectStrategy.sendRedirect(request, response, arcSecurityProperties.getBrowser().getLoginUrl());
//            }
//        }
//        return new ResponseSimple("需要登录");
//    }
//
//    @GetMapping("/me")
//    public Object me() {
//        return SecurityContextHolder.getContext().getAuthentication();
//    }
//
//    @GetMapping("/auth")
//    public Object auth(Authentication authentication) {
//        return authentication;
//    }
//}
