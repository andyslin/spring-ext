package org.autumn.spring.argsbind.base;

import org.autumn.spring.argsbind.PropertyValuesProvider;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;

@Component
public class BaseFormPropertyValuesProvider implements PropertyValuesProvider {

    @Override
    public void addBindValues(MutablePropertyValues mpvs, ServletRequest request, Object target, String name) {
//        for (Class<?> cls = target.getClass(); !cls.equals(Object.class); cls = cls.getSuperclass()) {
////            for (Field field : cls.getDeclaredFields()) {
////                if (BaseForm.class.isAssignableFrom(field.getType())) {
////                    mpvs.add(field.getName(), obtainBaseForm());
////                }
////            }
////        }
        mpvs.add("base", obtainBaseForm());
    }

    /**
     * 获取BaseForm，实际应用可能会获取session、解密jwt或者其它逻辑
     *
     * @return
     */
    private BaseForm obtainBaseForm() {
        BaseForm form = new BaseForm();
        form.setUserId("admin");
        form.setOrgId("0000");
        return form;
    }
}
