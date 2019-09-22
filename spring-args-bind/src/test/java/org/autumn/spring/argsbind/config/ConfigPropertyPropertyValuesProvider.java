package org.autumn.spring.argsbind.config;

import org.autumn.spring.argsbind.PropertyValuesProvider;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import java.lang.reflect.Field;

@Component
public class ConfigPropertyPropertyValuesProvider implements PropertyValuesProvider {

    @Override
    public void addBindValues(MutablePropertyValues mpvs, ServletRequest request, Object target, String name) {
        for (Class<?> cls = target.getClass(); !cls.equals(Object.class); cls = cls.getSuperclass()) {
            for (Field field : cls.getDeclaredFields()) {
                // 实际应用中可以将是否包括@ConfigProperty注解等元信息缓存起来
                if (field.isAnnotationPresent(ConfigProperty.class)) {
                    mpvs.add(field.getName(), obtainConfigProperty(field.getAnnotation(ConfigProperty.class), field));
                }
            }
        }
    }

    /**
     * 根据注解和Field获取属性
     */
    private Object obtainConfigProperty(ConfigProperty configProperty, Field field) {
        String propertyName = configProperty.value();
        // 这里直接返回属性值，实际应用中可以根据注解从环境变量、DB或者缓存中获取
        return propertyName + "Value";
    }
}
