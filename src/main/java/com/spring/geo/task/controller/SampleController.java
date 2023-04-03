package com.spring.geo.task.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.geo.common.config.ApiOperation;
import com.spring.geo.common.exception.BusinessException;
import com.spring.geo.task.model.Sample;

@RestController
public class SampleController {

    @ApiOperation(httpMethod = "POST", notes = "sample call")
    @RequestMapping(method = RequestMethod.POST, path = "/rest/vst/samplecall")
    public Map<String, String> sample(@RequestBody Sample sample)throws BusinessException {
        Map<String, String> result = new HashMap<String, String>();

        result.put("header", sample.getHeader());
        result.put("body", sample.getBody());

        return result;
    }

}
