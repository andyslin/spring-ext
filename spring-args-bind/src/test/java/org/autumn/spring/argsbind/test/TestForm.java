package org.autumn.spring.argsbind.test;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TestForm {

    private String beforeBindProperty;

    private String afterBindProperty;
}
