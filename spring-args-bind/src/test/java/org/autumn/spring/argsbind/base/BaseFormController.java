package org.autumn.spring.argsbind.base;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseFormController {

    @GetMapping("/baseform")
    public BusinessForm test(BusinessForm form) {
        return form;
    }
}
