package com.spring.geo.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.spring.geo.common.exception.BusinessException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Value("${jwt.custom.login-token}")
    private String loginToken;

    @Value("${jwt.custom.activate}")
    private boolean activate;

    private static final Logger LOGGER = LogManager.getLogger(JwtInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String auth = request.getHeader("loginAuth");

        if(activate) {
            LOGGER.info(auth);
            if (auth != null && !auth.equals("") && isUsable(loginToken)) {
                return true;
            } else {
                throw new BusinessException("loginAuth check");
            }
        }

        return true;
    }

    private boolean isUsable(String jwt) throws Exception {
        try {
            Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(loginToken))
                .parseClaimsJws(jwt).getBody();

            return true;
        } catch (Exception e) {
            throw new Exception();
        }
    }
}
