package org.autumn.spring.argsbind.provider;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PropertyValuesController {

    @GetMapping("/test")
    public PropertyValuesForm test(PropertyValuesForm form) {
        System.out.println(form);
        return form;
    }
}
