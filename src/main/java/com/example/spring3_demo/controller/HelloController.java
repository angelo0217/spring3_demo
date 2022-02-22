package com.example.spring3_demo.controller;

import com.example.spring3_demo.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
public class HelloController {

    @Autowired
    HelloController(HelloService helloService){
        this.helloService = helloService;
    }

    private HelloService helloService;

    @GetMapping("/hello")
    public String hello(){
        return helloService.hello();
    }
}
