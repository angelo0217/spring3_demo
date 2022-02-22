package com.example.spring3_demo.service;

import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public String hello(){
        return "hello world service";
    }
}
