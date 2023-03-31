package com.spring.geo.common.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.spring.geo.common.interceptor.JwtInterceptor;
import com.spring.geo.common.interceptor.RestApiInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	@Autowired
	private JwtInterceptor jwtInterceptor;

	@Autowired
	private RestApiInterceptor RestApiInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor)
				.addPathPatterns("/rest/auth/**")
				.excludePathPatterns("/rest/vst/**");
		
		registry.addInterceptor(RestApiInterceptor)
				.addPathPatterns("/rest/**");
	}

}