package org.autumn.spring.argsbind.date;

import org.autumn.spring.argsbind.PropertyValuesProvider;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DateFieldPropertyValuesProvider implements PropertyValuesProvider {

    private final Map<Class<?>, List<Field>> dateFields = new ConcurrentHashMap<>();

    @Override
    public void addBindValues(MutablePropertyValues mpvs, ServletRequest request, Object target, String name) {
        Map<String, LocalDate> cached = new HashMap<>();
        for (Class<?> cls = target.getClass(); !cls.equals(Object.class); cls = cls.getSuperclass()) {
            for (Field field : getDateFields(cls)) {
                dealDateField(mpvs, request, field, cached);
            }
        }
    }

    private List<Field> getDateFields(Class<?> cls) {
        if (!dateFields.containsKey(cls)) {
            synchronized (dateFields) {
                if (!dateFields.containsKey(cls)) {
                    List<Field> fields = new ArrayList<>();
                    for (Field field : cls.getDeclaredFields()) {
                        if (null != AnnotationUtils.getAnnotation(field, DateField.class)
                                || null != AnnotationUtils.getAnnotation(field, DateFormat.class)) {
                            fields.add(field);
                        }
                    }
                    dateFields.put(cls, fields.isEmpty() ? Collections.emptyList() : fields);
                }
            }
        }
        return dateFields.get(cls);
    }

    private void dealDateField(MutablePropertyValues mpvs, ServletRequest request, Field field, Map<String, LocalDate> cached) {
        DateField dateField = AnnotationUtils.getAnnotation(field, DateField.class);
        DateFormat dateFormat = AnnotationUtils.getAnnotation(field, DateFormat.class);

        String paramName = null != dateField ? dateField.name() : null;
        String format = null != dateFormat ? dateFormat.format() : dateField.format();
        String[] allowFormats = null != dateFormat ? dateFormat.allowFormats() : dateField.allowFormats();
        int[] offsets = null != dateField ? dateField.offsets() : new int[0];

        if (!StringUtils.hasText(paramName)) {
            paramName = field.getName();
        }

        int length = offsets.length;
        String parameter = request.getParameter(paramName);

        // 未传入值，且无偏移量，则不做处理
        if (!StringUtils.hasText(parameter) && length == 0) {
            return;
        }

        // 获取日期对象
        LocalDate localDate;
        Object property = cached.get(paramName);//尝试从本地缓存中获取已解析过的日期对象
        if (property instanceof LocalDate) {
            localDate = (LocalDate) property;
        } else {
            localDate = getLocalDate(format, allowFormats, paramName, parameter);
            cached.put(paramName, localDate);
        }

        //计算偏移量 日 月 周 年
        localDate = localDate.plusDays(length >= 1 ? offsets[0] : 0)
                .plusMonths(length >= 2 ? offsets[1] : 0)
                .plusWeeks(length >= 3 ? offsets[2] : 0)
                .plusYears(length >= 4 ? offsets[3] : 0);
        mpvs.add(field.getName(), localDate.format(DateTimeFormatter.ofPattern(format)));
    }

    private LocalDate getLocalDate(String format, String[] allowFormats, String paramName, String parameter) {
        LocalDate localDate;
        if (StringUtils.hasText(parameter)) {// 解析参数值
            localDate = parseDate(parameter, format);
            if (null == localDate) {
                for (String allowFormat : allowFormats) {
                    localDate = parseDate(parameter, allowFormat);
                    if (null != localDate) {
                        break;
                    }
                }
            }
            if (null == localDate) {//格式不符合要求，抛出异常
                //throw new BindException(target, name);
                throw new RuntimeException("[param: " + paramName + "][value: " + parameter + "][format: " + format + "] does not matches... ");
            }
        } else {
            localDate = LocalDate.now();
        }
        return localDate;
    }

    private LocalDate parseDate(String date, String format) {
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
