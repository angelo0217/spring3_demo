package com.example.spring3_demo.scheduler.task;

public class TestTask implements Runnable{
    @Override
    public void run() {
        System.out.println("test 123");
    }
}
