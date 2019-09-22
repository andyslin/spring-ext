package org.autumn.spring.argsbind.rsa;

import org.autumn.spring.argsbind.PropertyValuesProvider;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import java.lang.reflect.Field;

import static org.autumn.spring.argsbind.rsa.RSAUtils.RSA_PAIR;

@Component
public class RsaDecryptPropertyValuesProvider implements PropertyValuesProvider {

    @Override
    public void addBindValues(MutablePropertyValues mpvs, ServletRequest request, Object target, String name) {
        for (Class<?> cls = target.getClass(); !cls.equals(Object.class); cls = cls.getSuperclass()) {
            for (Field field : cls.getDeclaredFields()) {
                if (field.isAnnotationPresent(RsaDecrypt.class)) {
                    mpvs.add(field.getName(), obtainConfigProperty(field.getName(), request));
                }
            }
        }
    }

    /**
     * 根据注解和Field获取属性
     */
    private Object obtainConfigProperty(String name, ServletRequest request) {
        String encrypt = request.getParameter(name);
        if (StringUtils.hasText(encrypt)) {
            return RSAUtils.decryptByPrivateKey(encrypt, RSA_PAIR[1]);
        }
        return encrypt;
    }
}
