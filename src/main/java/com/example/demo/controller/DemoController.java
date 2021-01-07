package com.example.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/* 类注解 */
@Api(value = "desc of class")
@RestController
public class DemoController
{
    /* 方法注解 */
    @ApiOperation(value = "desc of method", notes = "")
    @RequestMapping(value = "demo", method = RequestMethod.GET)
    public String demo()
    {
        return "{\"name\":\"我是名字\",\"pwd\":\"我是密碼\"}";
    }
}
