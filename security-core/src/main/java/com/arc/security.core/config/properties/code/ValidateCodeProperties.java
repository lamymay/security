package com.arc.security.core.config.properties.code;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 叶超
 * @since 2019/5/22 22:17
 */
@Setter
@Getter
@NoArgsConstructor
public class ValidateCodeProperties {

    private ImageCodeProperties image = new ImageCodeProperties();

    private SmsCodeProperties sms = new SmsCodeProperties();

}
