package com.spring.geo.common.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.geo.common.exception.BusinessException;
import com.spring.geo.common.util.UtilService;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Value("${jwt.custom.login-token}")
    private String loginToken;

    @Value("${jwt.custom.activate}")
    private boolean activate;
    
    @Autowired
    UtilService utilService;

    private static final Logger LOGGER = LogManager.getLogger(JwtInterceptor.class);

    @Override
    @SuppressWarnings("unchecked")
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String headerAuth = request.getHeader("loginAuth");
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(headerAuth, Map.class);

        String authToken = (String) map.get("value");
        Long expiration = (Long) map.get("expiration");

        if(activate) {
            LOGGER.info(map);
            if (map.size() != 0 && utilService.isUsable(authToken, loginToken)) {
                response.setHeader("loginauth", authToken);
                
                return true;
            } else {
                throw new BusinessException("loginAuth check");
            }
        }

        return true;
    }
}
