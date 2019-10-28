//package com.arc.security.browser.controller.data;
//
//import com.arc.security6.config.properties.StaticField;
//import com.arc.security6.model.Code;
//import com.arc.security6.util.VerifyCodeUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.annotation.Resource;
//import javax.imageio.ImageIO;
//import javax.naming.AuthenticationException;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Random;
//import java.util.UUID;
//import java.util.concurrent.TimeUnit;
//
//
///**
// * @author 叶超
// * @since 2019/5/9 12:54
// */
//@Slf4j
//@Controller
//@RequestMapping("/verify")
//public class VerifyController {
//
//    @Resource
//    private RedisTemplate<Object, Object> redisTemplate;
//
//    /**
//     * 获取验证码图片
//     * 1、生成随机数作为验证码
//     * 2、后台记录一下什么验证码发给了谁，记录在redis中
//     * 3、返回验证码给用户，并把redis的key写入cookie
//     */
//    @RequestMapping(value = "/code/img", method = {RequestMethod.GET, RequestMethod.POST})
//    @ResponseBody
//    public void getVerifyImageV0(HttpServletRequest request, HttpServletResponse response) {
//        // 生成  生成 key=随机字串UUID，value=算法生成4位随机数
//
//        Random random = new Random(System.currentTimeMillis());
//        String key = UUID.randomUUID().toString().replaceAll("-", "");
////        String verifyCode = random.nextInt(10000) + "";
////        Code code = new Code(verifyCode);
////        log.debug("redis的key={},value={}位验证码:{}", key, verifyCode.length(), code);
//        int code = random.nextInt(10000);
//        redisTemplate.opsForValue().set(key, code, 600L, TimeUnit.SECONDS);
//
//        Cookie cookie = new Cookie(StaticField.COOKIE_KEY, key);
//        cookie.setMaxAge(43200);// 单位是秒  12h
//        cookie.setPath("/");
//        response.addCookie(cookie);
//        try {
//            boolean b = ImageIO.write(VerifyCodeUtils.createVerifyImage(String.valueOf(code)), "jpg", response.getOutputStream());
//            log.debug("返回图片结果={}", b);
//        } catch (IOException e) {
//            e.printStackTrace();
//            log.error("IOException{}", e);
//            return;
//        }
//    }
//
//
//    /**
//     * 测试验证码校验逻辑，注意参数有get方式传
//     *
//     * @param verifyCode
//     * @param request
//     * @param response
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "/code/img/verify/{verifyCode}", method = {RequestMethod.GET, RequestMethod.POST})
//    @ResponseBody
//    public boolean verifyImageCode(@PathVariable String verifyCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        //String verifyCode = (String) request.getAttribute("verifyCode");
//        if (verifyCode == null) {
//            throw new AuthenticationException("验证码为空");
//        } else {
//            log.debug("verifyCode={}", verifyCode);
//            String redisValue = null;
//            try {
//                //尝试获取验证码
//                String redisKey = null;
//                if (request.getCookies() != null) {
//                    for (Cookie cookie : request.getCookies()) {
//                        if (StaticField.COOKIE_KEY.equalsIgnoreCase(cookie.getName())) {
//                            redisKey = cookie.getValue();
//                            break;
//                        }
//                    }
//                }
//                //Code code=(Code) redisTemplate.opsForValue().get(redisKey);
//                redisValue = (String) redisTemplate.opsForValue().get(redisKey);
//                log.debug("The cookieValue is redisKey={},redisValue={}", redisKey, redisValue);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw new AuthenticationException("验证码失效");
//            }
//            //获取redis中的值与前端传入的值比对
//            if (verifyCode.equals(redisValue)) {
//                return true;
//            } else {
//                throw new AuthenticationException("验证码错误");
//            }
//        }
//    }
//
//
//    @RequestMapping(value = "/code/test", method = {RequestMethod.GET, RequestMethod.POST})
//    @ResponseBody
//    public Object getVerifyImageV1(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//        // 生成验证码信息并放入session，【根据系统架构session共享】  注意设计key的前缀 VERIFY_IMAGE_PREFIX           //        this.expireTime = LocalDateTime.now().plusSeconds(expireSeconds);
//        //返回图片给client
//        //下次来访问后端时候去验证用户身份的时候获取图片
//
//        Code codeFormRedis = null;
//        try {
//            //操作字符串
//            String username = (String) request.getAttribute("username");
//            //String key = (String) request.getAttribute("verify_code");
//            String key = UUID.randomUUID().toString().replaceAll("-", "");
//            String verifyImageCode = getRandomVerifyCode(4);
//
//            Code valueObject = new Code(verifyImageCode);
//            log.debug("------------------------------------------");
//            log.debug("username={}\nredisTemplate={}\nkey={}\nvalue={}", username, redisTemplate, key, valueObject);
//            log.debug("------------------------------------------");
//
//
//            //数据保存有限时间
//            //redisTemplate.opsForValue().set(Object k, Object v, long l, TimeUnit timeUnit)
//            redisTemplate.opsForValue().set(key, valueObject, 600L, TimeUnit.SECONDS);
//
//            //测试取值
//            codeFormRedis = (Code) redisTemplate.opsForValue().get(key);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return codeFormRedis;
//    }
//
//
//    public static String getRandomVerifyCode(int contentSize) {
//        //如果初始值设定一场给默认值1
//        contentSize = contentSize < 1 ? 1 : contentSize;
//        Random random = new Random(System.currentTimeMillis());
//        StringBuilder verifyCode = new StringBuilder(contentSize);
//        for (int i = 0; i < contentSize; i++) {
//            verifyCode.append(random.nextInt(contentSize));
//        }
//        return verifyCode.toString();
//    }
//
//}
////        log.debug("测试取值 valueGet={}", valueGet);
////        response.setHeader("Pragma", "No-cache");
////        response.setHeader("Cache-Control", "no-cache");
////        response.setDateHeader("Expires", 60);
////        response.setContentType("image/jpeg");
////        boolean write = ImageIO.write(verifyImage, "jpg", response.getOutputStream());
////        log.debug("返回图片结果={}", write);
