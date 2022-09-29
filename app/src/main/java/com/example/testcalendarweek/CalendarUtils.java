package com.example.testcalendarweek;

import android.os.Build;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarUtils {
    public static LocalDate selectDate;

    public static LocalDate selectDateNow() {
        LocalDate selectDateNow = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            selectDateNow = LocalDate.now();
        }
        return selectDateNow;
    }

    public static ArrayList<LocalDate> daysInWeekArray(LocalDate selectDate) {
        ArrayList<LocalDate> dates = new ArrayList<>();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate current = mondayForDate(selectDate);
            LocalDate endDate = current.plusWeeks(1);
            while (current.isBefore(endDate)) {
                dates.add(current);
                current = current.plusDays(1);
            }
        }
        return dates;
    }

    private static LocalDate mondayForDate(LocalDate selectDate) {
        LocalDate onWeekAgo = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            onWeekAgo = selectDate.minusWeeks(1);
            while (selectDate.isAfter(onWeekAgo)) {
                if(selectDate.getDayOfWeek() == DayOfWeek.MONDAY)
                    return selectDate;
                selectDate = selectDate.minusDays(1);
            }
        }
        return onWeekAgo;
    }
}
