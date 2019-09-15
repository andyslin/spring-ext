package org.autumn.spring.argsbind;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyAccessor;

import javax.servlet.ServletRequest;

/**
 * 属性值提供器接口
 */
public interface PropertyValuesProvider {

    /**
     * 绑定前添加属性
     *
     * @param mpvs
     * @param request
     * @param target
     * @param name
     */
    default void addBindValues(MutablePropertyValues mpvs, ServletRequest request, Object target, String name) {
    }

    /**
     * 绑定后修改目标对象
     *
     * @param accessor
     * @param request
     * @param target
     * @param name
     */
    default void afterBindValues(PropertyAccessor accessor, ServletRequest request, Object target, String name) {
    }
}
