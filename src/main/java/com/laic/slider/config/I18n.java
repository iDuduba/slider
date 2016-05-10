/**
 * duduba
 * Created at 2016年5月1日 上午10:16:49
 */
package com.laic.slider.config;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

/**
 * @author duduba
 * 
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface I18n {

    public String codeType() default "";

    public String code() default "";
}
