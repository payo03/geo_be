package com.spring.geo.common.config;

public @interface ApiOperation {

    String httpMethod() default "";

    String notes() default "";

}
