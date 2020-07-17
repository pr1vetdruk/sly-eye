package ru.privetdruk.slyeye.concurrent;

import java.time.LocalTime;

public class TaskTime {
    private final LocalTime time;
    private boolean isCurrentDay;

    public TaskTime(LocalTime time) {
        this.time = time;
        isCurrentDay = true;
    }

    public int getHour() {
        return time.getHour();
    }

    public int getMinute() {
        return time.getMinute();
    }

    public boolean isCurrentDay() {
        return isCurrentDay;
    }

    public void setCurrentDay(boolean currentDay) {
        isCurrentDay = currentDay;
    }
}
