package com.spring.geo.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.spring.geo.common.exception.BusinessException;

@Component
public class RestApiInterceptor implements HandlerInterceptor {

    @Value("${jwt.custom.rest-token}")
    private String restToken;

    @Value("${jwt.custom.activate}")
    private boolean activate;

    private static final Logger LOGGER = LogManager.getLogger(RestApiInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String auth = request.getHeader("Authorization");

        if (activate) {
            LOGGER.info(auth);
            if (auth != null && !auth.equals("")) {
                if (auth.equals(restToken))
                    return true;
                else
                    throw new BusinessException("Authorization token check");
            } else {
                throw new BusinessException("Authorization null");
            }
        }

        return true;
    }
}
