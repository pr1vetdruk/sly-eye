package ru.privetdruk.slyeye.concurrent;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TaskExecutor {
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    private final List<ScheduledFuture<?>> taskList = new ArrayList<>();

    public void startExecution(Runnable task, long delay, long period, TimeUnit timeUnit) {
        taskList.add(executorService.scheduleAtFixedRate(task, delay, period, timeUnit));
    }

    public void startExecution(Runnable task, TaskTime time) {
        Runnable taskWrapper = () -> {
            task.run();
            startExecution(task, time);
        };

        long delay = computeNextDelay(time);
        executorService.schedule(taskWrapper, delay, TimeUnit.SECONDS);
    }

    private long computeNextDelay(TaskTime time) {
        LocalDateTime localNow = LocalDateTime.now();
        if (time.isCurrentDay()) {
            time.setCurrentDay(false);
        } else {
            localNow = localNow.getSecond() != 0 ? localNow.plusMinutes(1).withSecond(0) : localNow;
        }

        ZoneId currentZone = ZoneId.systemDefault();
        ZonedDateTime zonedNow = ZonedDateTime.of(localNow, currentZone);
        ZonedDateTime zonedNextTarget = zonedNow.withHour(time.getHour()).withMinute(time.getMinute()).withSecond(0).withNano(0);

        if (zonedNow.compareTo(zonedNextTarget) > 0) {
            zonedNextTarget = zonedNextTarget.plusDays(1);
        }

        Duration duration = Duration.between(zonedNow, zonedNextTarget);
        return duration.getSeconds();
    }

    public void cancel() {
        taskList.forEach(task -> task.cancel(false));
        taskList.clear();
    }

    public void stop() {
        executorService.shutdown();
        cancel();
    }
}
