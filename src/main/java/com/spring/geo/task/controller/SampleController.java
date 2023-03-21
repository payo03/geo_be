package com.spring.geo.task.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.geo.common.config.ApiOperation;
import com.spring.geo.common.exception.BusinessException;
import com.spring.geo.task.model.Memberonline;
import com.spring.geo.task.model.Sample;
import com.spring.geo.task.service.MemberService;

@RestController
@RequestMapping("/samplevue")
public class SampleController {

    @Resource(name = "memberService")
    private MemberService memberService;
    
    @ApiOperation(httpMethod = "POST", notes = "sample link")
    @RequestMapping(method = RequestMethod.POST, path = "/samplelink")
    public Map<String, String> sample(@RequestBody Sample sample) throws BusinessException {
        System.out.println(sample);

        Map<String, String> result = new HashMap<String, String>();

        result.put("header", sample.getHeader());
        result.put("body", sample.getBody());

        List<Memberonline> memberonline = memberService.selectMember();
        System.out.println(memberonline);
        
        return result;
    }
    
}
