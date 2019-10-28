package com.arc.security.core.model.system;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 验证码
 * 注意：保存到redis中
 *
 * @author 叶超
 * @since 2019/5/9 23:05
 */
@Setter
@Getter
@ToString
public class ValidateCode implements Serializable {

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;
    /**
     * code
     */
    private String code;


    /**
     * 有效秒数
     */
    private int expiredSecond=120;

//
//    /**
//     * 过期时间
//     */
//    private LocalDateTime expiredTime;


    public ValidateCode(String code, int expiredSecond) {
        this.code = code;
        this.expiredSecond = expiredSecond;
       // this.expiredTime = LocalDateTime.now().plusSeconds(expiredSecond);
    }

    public ValidateCode(String code) {
        this.code = code;
    }

    public ValidateCode(int expiredSecond) {
        this.expiredSecond = expiredSecond;
        //this.expiredTime = LocalDateTime.now().plusSeconds(expiredSecond);
    }

    public ValidateCode() {
    }


//    /**
//     * 是否过期
//     *
//     * @return
//     */
//    public boolean isExpired() {
//        return LocalDateTime.now().isAfter(getExpiredTime());
//    }
}
