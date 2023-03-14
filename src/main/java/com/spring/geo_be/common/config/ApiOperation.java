package com.spring.geo_be.common.config;

public @interface ApiOperation {

    String httpMethod() default "";

    String notes() default "";

}
