package com.spring.geo.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.spring.geo.common.exception.BusinessException;
import com.spring.geo.common.util.UtilService;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Value("${jwt.custom.access-token}")
    private String envAccessToken;

    @Value("${jwt.custom.refresh-token}")
    private String envRefreshToken;

    @Autowired
    UtilService utilService;

    private static final Logger LOGGER = LogManager.getLogger(JwtInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader("accesstoken");
        String refreshToken = request.getHeader("refreshtoken");

        try {
            // Access Token Check
            String accSubject = utilService.getSubject(accessToken, envAccessToken);

            response.setHeader("accesstoken", accessToken);
        } catch(ExpiredJwtException e) {
            try {
                // Refresh Token Check
                String accSubject = utilService.getSubject(refreshToken, envAccessToken + envRefreshToken);
        
                response.setHeader("accesstoken", utilService.createToken(accSubject, envAccessToken));
            } catch(ExpiredJwtException ex) {
                throw new BusinessException("refreshToken expired");
            }
        }

        return true;
    }
}
