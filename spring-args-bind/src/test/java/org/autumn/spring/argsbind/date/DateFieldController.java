package org.autumn.spring.argsbind.date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DateFieldController {

    @GetMapping("/dateField")
    public DateFieldForm test(DateFieldForm form) {
        return form;
    }
}
