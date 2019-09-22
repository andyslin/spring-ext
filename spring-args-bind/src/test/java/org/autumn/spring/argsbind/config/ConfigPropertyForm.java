package org.autumn.spring.argsbind.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConfigPropertyForm {

    @ConfigProperty("configName")
    private String configProperty;
}
