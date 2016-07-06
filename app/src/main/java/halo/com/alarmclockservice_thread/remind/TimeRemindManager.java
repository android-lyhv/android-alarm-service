package halo.com.alarmclockservice_thread.remind;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by HoVanLy on 7/5/2016.
 */
public class TimeRemindManager {

    public static long getTimeForNextDay(int currentDay, int[] listDay) {
        long timeForNextDay = 24 * 60 * 60 * 1000;
        for (int i = 0; i < listDay.length; i++) {
            if (currentDay == listDay[i]) {
                int nextDay;
                if (i == listDay.length - 1) {
                    nextDay = listDay[0];
                    timeForNextDay = (7 - Math.abs(currentDay - nextDay)) * 24 * 60 * 60 * 1000;
                } else {
                    nextDay = listDay[i + 1];
                    timeForNextDay = Math.abs(nextDay - currentDay) * 24 * 60 * 60 * 1000;
                }
                break;
            }
        }
        return timeForNextDay;
    }

    public static long getTimeForNextDayOut(int currentDay, int[] listDay) {
        boolean check = false;
        long timeForNextDay = 24 * 60 * 60 * 1000;
        for (int i = 0; i < listDay.length; i++) {
            if (currentDay < listDay[i]) {
                int nextDay = listDay[i];
                timeForNextDay = Math.abs(nextDay - currentDay) * 24 * 60 * 60 * 1000;
                check = true;
                break;
            }

        }
        if (!check) {
            timeForNextDay = (7 - Math.abs(currentDay - listDay[0])) * 24 * 60 * 60 * 1000;
        }
        return timeForNextDay;
    }

    public static String getDayOfWeek() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = dateFormat.format(d);
        return dayOfTheWeek;
    }

    public static int getIndexDay(String day) {
        day = day.toUpperCase();
        if ("SUNDAY".equals(day)) {
            return 8;
        }
        if ("SATURDAY".equals(day)) {
            return 7;
        }
        if ("FRIDAY".equals(day)) {
            return 6;
        }
        if ("THURSDAY".equals(day)) {
            return 5;
        }
        if ("WEDNESDAY".equals(day)) {
            return 4;
        }
        if ("TUESDAY".equals(day)) {
            return 3;
        }
        if ("MONDAY".equals(day)) {
            return 2;
        }
        return 2;
    }

    public static boolean isDayInList(int currentDay, int[] listDay) {
        if (listDay.length == 0) return true;
        for (int i = 0; i < listDay.length; i++) {
            if (currentDay == listDay[i]) {
                return true;
            }
        }
        return false;
    }

}
