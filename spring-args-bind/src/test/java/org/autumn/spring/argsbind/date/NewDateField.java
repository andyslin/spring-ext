package org.autumn.spring.argsbind.date;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@DateFormat(format = "yyyy-MM-dd", allowFormats = {"yyyyMMdd", "yyyy-MM-dd", "yyyy/MM/dd", "dd/MM/yyyy"})
public @interface NewDateField {

}
