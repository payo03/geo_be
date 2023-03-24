package com.spring.geo.common.util;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CommonUtilService {
    
    public <T> T convertMapToModel(Map<String, Object> map, Class<T> type) throws Exception {
        if(type == null) throw new NullPointerException("Class can't be null");
        
        ObjectMapper mapper = new ObjectMapper();
        
        T instance = mapper.convertValue(map.get("param"), type);
        if(map.get("param") == null) return instance;        

        return instance;
    }
}
