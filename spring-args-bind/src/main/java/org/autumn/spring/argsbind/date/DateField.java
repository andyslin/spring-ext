package org.autumn.spring.argsbind.date;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateField {

    /**
     * 参数名，默认为被注解属性的名称
     *
     * @return
     */
    String name() default "";

    /**
     * 最终接受的格式
     *
     * @return
     */
    String format() default "yyyyMMdd";

    /**
     * 允许的格式，默认和{@link #format()}相同
     *
     * @return
     */
    String[] allowFormats() default {};

    /**
     * 偏移量，使用整数数组表示针对当前时间的偏移，数组长度最长4位，依次表示日、月、周、年，不足4位的可省略
     * <p>
     * 如[0]表示今天，[-1]表示昨天，[0,-1]表示上月同一天，[0,0,-1]表示上周同一天，[0,0,0,-1]表示上年同一天
     *
     * @return
     */
    int[] offsets() default {};
}
