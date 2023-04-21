package com.spring.geo.common.util;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.geo.common.exception.BusinessException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class CommonUtilService {

    @Value("${jwt.custom.login-token}")
    private String loginToken;

    @Autowired
    @Qualifier("transferRestTemplate")
    private RestTemplate restTemplate;

    private long tokenDuration = Duration.ofMinutes(2).toMillis();
    
    @Deprecated
    public <T> T convertMapToModel(Map<String, Object> map, Class<T> type) throws Exception {
        if(type == null) throw new NullPointerException("Class can't be null");
        
        ObjectMapper mapper = new ObjectMapper();
        
        T instance = mapper.convertValue(map.get("param"), type);
        if(map.get("param") == null) return instance;        

        return instance;
    }

    /**
     * @param url : https://...
     * @param method : HttpMethod.GET, HttpMethod.POST
     * @param param : json, form-data, etc...
     * @param headerList : key-value List
     * @param responseType : HashMap
     * @param uriVariables : GET variables
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public <T> HashMap<String, Object> exchangeCus(String url, HttpMethod method, T param, Map<String, String > headerMap, Map<String, ? > uriVariables) throws BusinessException {

        HttpHeaders headers = new HttpHeaders();
        for (Entry<String, String > header : headerMap.entrySet()) {
            headers.add(header.getKey(), header.getValue());
        }
        HttpEntity<T> httpEntity = new HttpEntity<>(param, headers);

        ResponseEntity<HashMap> response = null;

        if (uriVariables != null) 
            response = restTemplate.exchange(url, method, httpEntity, HashMap.class, uriVariables);
        else 
            response = restTemplate.exchange(url, method, httpEntity, HashMap.class);

        return response != null ? response.getBody(): null;
    }

    public <T> HashMap<String, Object> exchangeCus(String url, HttpMethod method, T param, Map<String, String > headerMap) throws BusinessException {

        return this.exchangeCus(url, method, param, headerMap, null);
    }
    
    // 토큰 발급
    public String createToken(String subject) {
        // 토큰을 서명하기 위해 사용해야할 알고리즘 선택
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(loginToken);
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
        JwtBuilder builder = Jwts.builder()
                .setSubject(subject)
                .signWith(signatureAlgorithm, signingKey);
        long nowTime = System.currentTimeMillis();
        // 유효기간 30분
        builder.setExpiration(new Date(nowTime + tokenDuration));

        return builder.compact();
    }

    // 토큰 해독
    public String getSubject(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(loginToken))
                .parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

}
