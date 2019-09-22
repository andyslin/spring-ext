package org.autumn.spring.argsbind.rsa;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RsaDecryptController {

    @GetMapping("/rsaDecrypt")
    public RsaDecryptForm test(@Validated RsaDecryptForm form) {
        return form;
    }
}
