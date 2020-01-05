package com.arc.security.core.service.security.impl;

import com.arc.security.core.config.properties.arc.ArcSecurityProperties;
import com.arc.security.core.model.system.ImageValidateCode;
import com.arc.security.core.model.system.ValidateCode;
import com.arc.security.core.service.security.ValidateCodeGenerator;
import com.arc.security.core.util.VerifyCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 叶超
 * @since 2019/6/2 19:20
 */
@Slf4j
//@Service(value = "imageValidateCodeGenerator")
//统一注入@Service
public class ImageValidateCodeGenerator implements ValidateCodeGenerator {


    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    //从配置文件中获取配置
    private ArcSecurityProperties arcSecurityProperties;

    public ImageValidateCodeGenerator(ArcSecurityProperties arcSecurityProperties) {
        this.arcSecurityProperties = arcSecurityProperties;
    }


    @Override
    public ValidateCode generateVerifyCode(ServletWebRequest request) {

        int width = ServletRequestUtils.getIntParameter(request.getRequest(), "width", arcSecurityProperties.getCode().getImage().getWidth());
        int height = ServletRequestUtils.getIntParameter(request.getRequest(), "height", arcSecurityProperties.getCode().getImage().getHeight());
        int length = ServletRequestUtils.getIntParameter(request.getRequest(), "length", arcSecurityProperties.getCode().getImage().getLength());

        // ValidateCode verifyCode = (ImageValidateCode )getVerifyCode(width, height, length);
        ImageValidateCode imageValidateCode = getVerifyCode(width, height, length);

        String key = UUID.randomUUID().toString().replaceAll("-", "");
        String valueInRedis = imageValidateCode.getCode();
        //save to redis
        redisTemplate.opsForValue().set(key, imageValidateCode.getCode(), imageValidateCode.getExpiredSecond(), TimeUnit.SECONDS);
        log.info("imageValidateCode={}\nredis的key={},value={}位验证码:{}", imageValidateCode, key, valueInRedis.length(), valueInRedis);
        System.err.println("#############################################################");
        // 单位是秒  43200S=12h
        Cookie cookie = new Cookie(arcSecurityProperties.getBrowser().getCookieKey(), key);
        cookie.setPath("/");
        cookie.setMaxAge(43200);
        HttpServletResponse response = request.getResponse();
        response.addCookie(cookie);
        response.setContentType("image/jpg");
        try {
            boolean b = ImageIO.write(VerifyCodeUtils.createVerifyImage(valueInRedis, width, height), "jpg", response.getOutputStream());
            log.debug("返回图片结果={}", b);
        } catch (IOException e) {
            log.error("异常IOException{}", e);
        }
        return imageValidateCode;
    }

    /**
     * 生成图形验证码
     *
     * @param width
     * @param height
     * @param length
     * @return
     */
    public ImageValidateCode getVerifyCode(int width, int height, int length) {

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();
        Random random = new Random(System.currentTimeMillis());

        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        String sRand = "";
        for (int i = 0; i < length; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 6, 16);
        }

        g.dispose();
        log.debug("#############################################################");
        log.debug("#############################################################");
        log.debug("#############################################################");
        log.debug("结果={}", sRand);
        log.debug("#############################################################");

        int expiredSecond = arcSecurityProperties.getCode().getImage().getExpiredSecond();
        ImageValidateCode validateCode = new ImageValidateCode(sRand, expiredSecond, image);
        return validateCode;
    }

    /**
     * 生成随机背景条纹
     *
     * @param fc
     * @param bc
     * @return
     */
    private Color getRandColor(int fc, int bc) {
        Random random = new Random(System.currentTimeMillis());
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    public void setSecurityProperties(ArcSecurityProperties arcSecurityProperties) {
        this.arcSecurityProperties = arcSecurityProperties;
    }


}
