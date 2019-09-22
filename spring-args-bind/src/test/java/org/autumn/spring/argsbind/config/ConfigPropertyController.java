package org.autumn.spring.argsbind.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigPropertyController {

    @GetMapping("/configProperty")
    public ConfigPropertyForm test(ConfigPropertyForm form) {
        return form;
    }
}
