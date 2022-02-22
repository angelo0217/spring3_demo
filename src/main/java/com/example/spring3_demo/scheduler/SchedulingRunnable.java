package com.example.spring3_demo.scheduler;


import com.example.spring3_demo.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Objects;

@Slf4j
public class SchedulingRunnable implements Runnable {
    private String beanName;
    private String methodName;
    private Object[] params;

    public SchedulingRunnable(String beanName, String methodName) {
        this(beanName, methodName, null);
    }

    public SchedulingRunnable(String beanName, String methodName, Object... params) {
        this.beanName = beanName;
        this.methodName = methodName;
        this.params = params;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        try {
            Object target = SpringContextUtils.getBean(beanName);
            Method method = null;
            if (null != params && params.length > 0) {
                Class<?>[] paramCls = new Class[params.length];
                for (int i = 0; i < params.length; i++) {
                    paramCls[i] = params[i].getClass();
                }
                method = target.getClass().getDeclaredMethod(methodName, paramCls);
            } else {
                method = target.getClass().getDeclaredMethod(methodName);
            }
            ReflectionUtils.makeAccessible(method);
            if (null != params && params.length > 0) {
                method.invoke(target, params);
            } else {
                method.invoke(target);
            }
        } catch (Exception ex) {
            log.error("job error - bean：{}，method： {}，params： {} ", beanName, methodName, params, ex);
        }
        long times = System.currentTimeMillis() - startTime;
        log.info("job end - bean：{}, method：{}, params： {}, times： {} ms", beanName, methodName, params, times);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchedulingRunnable that = (SchedulingRunnable) o;
        if (params == null) {
            return beanName.equals(that.beanName) &&
                    methodName.equals(that.methodName) &&
                    that.params == null;
        }
        return beanName.equals(that.beanName) &&
                methodName.equals(that.methodName) &&
                params.equals(that.params);
    }

    @Override
    public int hashCode() {
        if (params == null) {
            return Objects.hash(beanName, methodName);
        }
        return Objects.hash(beanName, methodName, params);
    }
}