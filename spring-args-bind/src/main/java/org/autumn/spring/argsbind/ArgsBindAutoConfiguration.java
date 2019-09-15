package org.autumn.spring.argsbind;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.List;

@Configuration
public class ArgsBindAutoConfiguration {

    @Bean
    @ConditionalOnBean(PropertyValuesProvider.class)
    @ConditionalOnMissingBean(ArgsBindWebMvcRegistrations.class)
    public ArgsBindWebMvcRegistrations argsBindWebMvcRegistrations(List<PropertyValuesProvider> providers) {
        return new ArgsBindWebMvcRegistrations(providers);
    }

    static class ArgsBindWebMvcRegistrations implements WebMvcRegistrations {

        private final List<PropertyValuesProvider> providers;

        public ArgsBindWebMvcRegistrations(List<PropertyValuesProvider> providers) {
            this.providers = providers;
        }

        @Override
        public RequestMappingHandlerAdapter getRequestMappingHandlerAdapter() {
            return new ArgsBindRequestMappingHandlerAdapter(providers);
        }
    }
}
