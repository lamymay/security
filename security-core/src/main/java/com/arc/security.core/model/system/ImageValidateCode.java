package com.arc.security.core.model.system;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.awt.image.BufferedImage;

/**
 * 短信验证码
 *
 * @author 叶超
 * @since 2019/5/9 23:05
 */
@Setter
@Getter
@ToString
public class ImageValidateCode extends ValidateCode {

    /**
     * 序列化ID
     * 接口标记，需要被序列化的对象必须实现Serializable接口 否则会出现NotSerializableException异常
     * 自定义 serialVersionUID，用于判断类和对象是否为同一版本
     */
    private static final long serialVersionUID = 1L;

    /**
     * 非静态数据不想被序列化可以使用 transient 修饰
     */
    private transient BufferedImage image;

    public ImageValidateCode(String code, int expiredSecond, BufferedImage image) {
        super(code, expiredSecond);
        this.image = image;
    }

    public ImageValidateCode(String code, BufferedImage image) {
        super(code);
        this.image = image;
    }

    public ImageValidateCode(int expiredSecond, BufferedImage image) {
        super(expiredSecond);
        this.image = image;
    }

    public ImageValidateCode(BufferedImage image) {
        this.image = image;
    }
}
