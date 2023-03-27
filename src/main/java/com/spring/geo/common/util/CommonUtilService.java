package com.spring.geo.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.geo.common.exception.BusinessException;

@Component
public class CommonUtilService {

    @Autowired
    @Qualifier("transferRestTemplate")
    private RestTemplate restTemplate;
    
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
        if (uriVariables != null) {
            response = restTemplate.exchange(url, method, httpEntity, HashMap.class, uriVariables);
        }
        else {
            response = restTemplate.exchange(url, method, httpEntity, HashMap.class);
        }

        return response != null ? response.getBody(): null;
    }

    public <T> HashMap<String, Object> exchangeCus(String url, HttpMethod method, T param, Map<String, String > headerMap) throws BusinessException {

        return this.exchangeCus(url, method, param, headerMap, null);
    }

}
