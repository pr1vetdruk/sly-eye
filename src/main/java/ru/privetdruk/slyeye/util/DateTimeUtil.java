package ru.privetdruk.slyeye.util;

import java.time.LocalTime;

public class DateTimeUtil {
    private DateTimeUtil() {

    }

    public static String convertTimeToString(LocalTime time) {
        return time != null ? time.toString() : "--:--";
    }
}
