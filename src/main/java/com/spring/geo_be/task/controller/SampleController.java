package com.spring.geo_be.task.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.geo_be.common.config.ApiOperation;
import com.spring.geo_be.task.model.Sample;

@RestController
@RequestMapping("/samplevue")
public class SampleController {

    @ApiOperation(httpMethod = "POST", notes = "sample link")
    @RequestMapping(method = RequestMethod.POST, path = "/samplelink")
    public Map<String, String> sample(@RequestBody Sample sample) {
        System.out.println(sample);

        Map<String, String> result = new HashMap<String, String>();

        result.put("header", sample.getHeader());
        result.put("body", sample.getBody());

        return result;
    }
    
}
