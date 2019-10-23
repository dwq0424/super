package com.action;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PropertySource(value = "classpath:param.properties",encoding = "UTF-8")
@RestController
public class IndexCentroller {

    @Value("${app.code}")
    private String code;
    @Autowired

    @RequestMapping(value = "/test",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void test(){
        System.out.println("---------------test code="+code);

    }
}
