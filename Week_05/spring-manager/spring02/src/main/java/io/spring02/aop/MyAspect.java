package io.spring02.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;

@Aspect
@Component
public class MyAspect implements InitializingBean {
    private final static DelayQueue<DelayTask> queue = new DelayQueue<>();
    private final static ConcurrentHashMap<String, Object> cache = new ConcurrentHashMap<>();

    @Pointcut("@annotation(io.spring02.aop.MyCache)")
    private void pointCut() {
    }


    @Around("pointCut() && @annotation(myCache)")
    public Object begin(ProceedingJoinPoint joinPoint, MyCache myCache) throws Throwable {
        Object[] args = joinPoint.getArgs();
        StringBuilder key = new StringBuilder();
        for (Object object : args) {
            key.append(object);
        }
        Object value = cache.get(key.toString());
        if (value != null) {
            return value;
        }

        Object proceed = joinPoint.proceed();
        LocalDateTime dateTime = LocalDateTime.now().plusSeconds(myCache.value());
        cache.put(key.toString(), proceed);
        queue.add(new DelayTask(key.toString(), dateTime));
        return proceed;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Thread damon = new Thread(new ConsumeTask());
        damon.setDaemon(true);
        damon.start();
    }

    static class ConsumeTask implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    DelayTask take = queue.take();
                    System.out.println("remove"+take.getKey());
                    cache.remove(take.getKey());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
