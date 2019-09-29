package org.autumn.spring.argsbind.date;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateFormat {

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
}
