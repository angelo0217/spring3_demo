package com.example.spring3_demo.scheduler.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("localTask")
public class LocalTask {
    public void taskWithParams(String param1) {
        log.info("job has params", param1);
    }
    public void taskNoParams() {
        log.info("job no params");
    }
}