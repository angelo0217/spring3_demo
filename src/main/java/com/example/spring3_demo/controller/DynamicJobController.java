package com.example.spring3_demo.controller;

import com.example.spring3_demo.scheduler.CronTaskRegister;
import com.example.spring3_demo.scheduler.SchedulingRunnable;
import com.example.spring3_demo.scheduler.task.TestTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DynamicJobController {
    private CronTaskRegister cronTaskRegister;

    @Autowired
    DynamicJobController(CronTaskRegister cronTaskRegister){
        this.cronTaskRegister = cronTaskRegister;
    }

    private Runnable cacheTask = null;

    @GetMapping("/add/job")
    public String addJob(){
        SchedulingRunnable task = new SchedulingRunnable("localTask",
                "taskWithParams",
                "test");
        cacheTask = task;

        cronTaskRegister.addCronTask(task, "0/5 * * * * ?");
        return "success";
    }


    @GetMapping("/add/customer")
    public String addCustomer(){
        cronTaskRegister.addCronTask(new TestTask(), "0/5 * * * * ?");
        return "success";
    }

    @GetMapping("/remove/job")
    public String removeJob(){
        if(cacheTask != null)
            cronTaskRegister.removeCronTask(cacheTask);
        return "success";
    }
}
