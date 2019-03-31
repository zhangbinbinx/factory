package com.spring.controller;

import com.spring.annotation.Controller;
import com.spring.annotation.RequestMapping;

@Controller
@RequestMapping("test")
public class TestController {
    @RequestMapping("/getName.do")
    public String returnName(String name){
        return name;
    }
}
