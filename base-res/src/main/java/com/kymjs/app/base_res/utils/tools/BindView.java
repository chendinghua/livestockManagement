package com.kymjs.app.base_res.utils.tools;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindView {
    int id();
    boolean click() default false;
    int textValue() default 0;
    String appendStr() default "";
    int hintValue() default 0;
    int tag() default 0;

}