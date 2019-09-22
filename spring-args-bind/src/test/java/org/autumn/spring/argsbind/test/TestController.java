package org.autumn.spring.argsbind.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public TestForm test(TestForm form) {
        return form;
    }
}
