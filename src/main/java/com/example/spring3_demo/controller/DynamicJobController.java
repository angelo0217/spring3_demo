package com.example.spring3_demo.controller;

import com.example.spring3_demo.model.User;
import com.example.spring3_demo.scheduler.CronTaskRegister;
import com.example.spring3_demo.scheduler.SchedulingRunnable;
import com.example.spring3_demo.scheduler.task.TestTask;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@Tag(description = "Dynamic Job Testing",
        name = "Dynamic Job")
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

    @Operation(summary = "addCustomer",
            description = "add customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "${api.response-codes.ok.desc}"),
            @ApiResponse(responseCode = "400",
                    description = "${api.response-codes.badRequest.desc}",
                    content = { @Content(examples = { @ExampleObject(value = "") }) }),
            @ApiResponse(responseCode = "404",
                    description = "${api.response-codes.notFound.desc}",
                    content = { @Content(examples = { @ExampleObject(value = "") }) }) })

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

    @PostMapping("/post/user")
    public User postUser(User user){
        log.info("user: ", user.getName());

        return user;
    }
}
