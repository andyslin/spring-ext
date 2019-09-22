package org.autumn.spring.argsbind.test;

import org.autumn.spring.argsbind.PropertyValuesProvider;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyAccessor;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;

@Component
public class TestPropertyValuesProvider implements PropertyValuesProvider {

    @Override
    public void addBindValues(MutablePropertyValues mpvs, ServletRequest request, Object target, String name) {
        mpvs.add("beforeBindProperty", "beforeBindPropertyValue");
    }

    @Override
    public void afterBindValues(PropertyAccessor accessor, ServletRequest request, Object target, String name) {
        if (target instanceof TestForm) {
            accessor.setPropertyValue("afterBindProperty", "afterBindPropertyValue");
        }
    }
}
