package io.spring02.aop;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) {
        LocalDateTime dateTime = LocalDateTime.now().plusSeconds(60);
        System.out.println(TimeUnit.NANOSECONDS.convert(Duration.between( LocalDateTime.now(),dateTime).toMillis(), TimeUnit.MILLISECONDS));

    }
}
