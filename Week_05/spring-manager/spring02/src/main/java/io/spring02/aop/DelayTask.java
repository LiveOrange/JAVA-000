package io.spring02.aop;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

@Data
public class DelayTask implements Delayed {
    private String key;
    private LocalDateTime taskTime;

    DelayTask(String key, LocalDateTime taskTime) {
        this.key = key;
        this.taskTime = taskTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(Duration.between(LocalDateTime.now(), taskTime).toMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed d) {
        DelayTask delayTask = (DelayTask) d;
        long diff = Duration.between(delayTask.getTaskTime(), taskTime).toMillis();
        return diff > 0 ? 1 : diff == 0 ? 0 : -1;
    }
}
