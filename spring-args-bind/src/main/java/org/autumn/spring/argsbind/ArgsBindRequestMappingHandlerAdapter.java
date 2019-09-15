package org.autumn.spring.argsbind;

import org.springframework.beans.ConfigurablePropertyAccessor;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.InitBinderDataBinderFactory;
import org.springframework.web.method.support.InvocableHandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletRequestDataBinderFactory;

import javax.servlet.ServletRequest;
import java.util.List;

/*package*/ class ArgsBindRequestMappingHandlerAdapter extends RequestMappingHandlerAdapter {

    private final List<PropertyValuesProvider> providers;

    public ArgsBindRequestMappingHandlerAdapter(List<PropertyValuesProvider> providers) {
        this.providers = providers;
    }

    @Override
    protected InitBinderDataBinderFactory createDataBinderFactory(List<InvocableHandlerMethod> binderMethods) throws Exception {
        return new ArgsBindServletRequestDataBinderFactory(binderMethods, getWebBindingInitializer());
    }

    private class ArgsBindServletRequestDataBinderFactory extends ServletRequestDataBinderFactory {

        public ArgsBindServletRequestDataBinderFactory(List<InvocableHandlerMethod> binderMethods, WebBindingInitializer initializer) {
            super(binderMethods, initializer);
        }

        @Override
        protected ServletRequestDataBinder createBinderInstance(Object target, String objectName, NativeWebRequest request) {
            return new ArgsBindServletRequestDataBinder(target, objectName);
        }
    }

    private class ArgsBindServletRequestDataBinder extends ExtendedServletRequestDataBinder {

        public ArgsBindServletRequestDataBinder(Object target, String objectName) {
            super(target, objectName);
        }

        /**
         * 添加属性值提供器的相关处理
         */
        @Override
        protected void addBindValues(MutablePropertyValues mpvs, ServletRequest request) {
            super.addBindValues(mpvs, request);
            if (null != providers) {
                Object target = getTarget();
                String name = getObjectName();
                providers.forEach(provider -> provider.addBindValues(mpvs, request, target, name));
            }
        }

        /**
         * 常规绑定之后的处理
         */
        @Override
        public void bind(ServletRequest request) {
            super.bind(request);
            if (null != providers) {
                ConfigurablePropertyAccessor mpvs = getPropertyAccessor();
                Object target = getTarget();
                String name = getObjectName();
                providers.forEach(provider -> provider.afterBindValues(mpvs, request, target, name));
            }
        }
    }
}
