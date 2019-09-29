package org.autumn.spring.argsbind.date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DateFieldForm {

    // 默认yyyyMMdd
    @DateField
    private String date1;

    // 允许多种格式
    @DateField(format = "yyyy-MM-dd", allowFormats = {"yyyyMMdd", "yyyy/MM/dd"})
    private String date2;

    // 传入参数的前一天
    @DateField(name = "date2", offsets = -1)
    private String prevDate;

    // 传入参数的上月同一天
    @DateField(name = "date2", offsets = {0, -1})
    private String sameDateOfPrevMonth;

    // 昨天
    @DateField(offsets = -1)
    private String date3;

    // 上月同一天
    @DateField(offsets = {0, -1})
    private String date4;

    // 单独使用新注解
    @NewDateField
    private String newDate;

    // 组合使用两个注解
    @DateField(name = "newDate")
    @NewDateField
    private String newDateFormat;
}
