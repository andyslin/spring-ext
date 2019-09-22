package org.autumn.spring.argsbind.rsa;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ToString
public class RsaDecryptForm {

    @Length(min = 6, max = 16, message = "长度只能在6-16位")
    @RsaDecrypt
    private String rsa;
}
