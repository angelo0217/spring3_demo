package com.example.spring3_demo;

import com.example.spring3_demo.service.HelloService;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

@State(Scope.Thread)
public class JmhTest {
    private ConfigurableApplicationContext context;

    private HelloService helloService;


    public static void main(String [] args) throws RunnerException {

        //include:指定当前这个类执行options的操作 如果你有多个options不指定其它也会执行
        Options options = new OptionsBuilder().include(JmhTest.class.getName()+".*")
                .warmupIterations(2).measurementIterations(2)
                .forks(1).build();

        new Runner(options).run();
    }

    /**
     * Setup初始化容器的时候只执行一次
     */
    @Setup(Level.Trial)//测试级别 执行一次
    public void init(){
        String args = "";
        context = SpringApplication.run(Spring3DemoApplication.class,args);

        helloService = context.getBean(HelloService.class);
    }

    /**
     * Benchmark执行多次，此注解代表触发我们所要进行基准测试的方法
     */
    @Benchmark
    public void test(){
        //调用接口 简单的查询列表方法
        System.out.println(helloService.hello());

    }
}
