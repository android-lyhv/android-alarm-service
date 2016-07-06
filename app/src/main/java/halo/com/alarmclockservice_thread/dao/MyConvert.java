package halo.com.alarmclockservice_thread.dao;
/**
 * @version 1.0
 * @author HoVanLy
 * @phone 0986305046
 * @nick halo
 */

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyConvert {
    // DATE TIME
    public static String getFullStringTime(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return dateFormat.format(cal.getTime());
    }

    public static String getShortStringTime(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return dateFormat.format(cal.getTime());
    }

    public static Date getDatefromString(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = simpleDateFormat.parse(dateString);
            return date;
        } catch (Exception e) {
            return null;
        }

    }

    public static String getCountTime(Date current, Date now) {
        long time = now.getTime() - current.getTime();
        long days = time / (1000 * 60 * 60 * 24);
        time = time % (1000 * 60 * 60 * 24);

        long hours = time / (1000 * 60 * 60);
        time = time % (1000 * 60 * 60);

        long minutes = time / (1000 * 60);
        time = time % (1000 * 60);

        long seconds = time / 1000;
        String timeLoad = "";
        if (days < 10) {
            timeLoad = timeLoad + "0" + days + ":";
        } else {
            timeLoad = timeLoad + String.valueOf(days) + ":";
        }
        if (hours < 10) {
            timeLoad = timeLoad + "0" + hours + ":";
        } else {
            timeLoad = timeLoad + String.valueOf(hours) + ":";
        }
        if (minutes < 10) {
            timeLoad = timeLoad + "0" + minutes + ":";
        } else {
            timeLoad = timeLoad + String.valueOf(minutes) + ":";
        }
        if (seconds < 10) {
            timeLoad = timeLoad + "0" + seconds;
        } else {
            timeLoad = timeLoad + String.valueOf(seconds);
        }
        return timeLoad;
    }

    public static long getLoadDays(Date oldDate, Date now) {
        long timeLoad = now.getTime() - oldDate.getTime();
        long days = timeLoad / (1000 * 60 * 60 * 24);
        return days;
    }

    public static Date getUtilTime(java.sql.Timestamp sqlTime) {
        return new Date(sqlTime.getTime());
    }

    public static java.sql.Timestamp getSqlTime(Date utilTime) {
        return new java.sql.Timestamp(utilTime.getTime());
    }

    // CRYPTION
    public static String enCryptMd5(String planText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDIgest = md.digest(planText.getBytes());
            BigInteger number = new BigInteger(1, messageDIgest);
            String hashtext = number.toString(16);
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


    // REGULAR
    public static boolean checkValidateMail(String email) {
        String email_pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(email_pattern);
        Matcher matcher;
        matcher = pattern.matcher(email);
        return matcher.matches();

    }

    public static boolean checkValidateTime12(String time) {
        String time_pattern = "(1[012]|[1-9]):[0-5][0-9](\\s)?(?i)(am|pm)";
        Pattern pattern = Pattern.compile(time_pattern);
        Matcher matcher;
        matcher = pattern.matcher(time);
        return matcher.matches();
    }

    public static boolean checkValidateTime24(String time) {
        String time_pattern = "^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";
        Pattern pattern = Pattern.compile(time_pattern);
        Matcher matcher;
        matcher = pattern.matcher(time);
        return matcher.matches();
    }

    public static boolean checkValidateUserName(String userName) {
        String userName_pattern = "^[a-z0-9_-]{3,15}$";
        Pattern pattern = Pattern.compile(userName_pattern);
        Matcher matcher;
        matcher = pattern.matcher(userName);
        return matcher.matches();
    }

    public static boolean checkValidatePassword(String password) {
        String password_pattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
        Pattern pattern = Pattern.compile(password_pattern);
        Matcher matcher;
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean checkValidateHexadecimalColor(String hexaColor) {
        String hexaColor_pattern = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";
        Pattern pattern = Pattern.compile(hexaColor_pattern);
        Matcher matcher;
        matcher = pattern.matcher(hexaColor);
        return matcher.matches();
    }

    public static boolean checkValidateImageFile(String imageFile) {
        String imageFile_pattern = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";
        Pattern pattern = Pattern.compile(imageFile_pattern);
        Matcher matcher;
        matcher = pattern.matcher(imageFile);
        return matcher.matches();
    }

    public static boolean checkValidateIpAdress(String ipAdress) {
        String ipAdress_pattern = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
                + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        Pattern pattern = Pattern.compile(ipAdress_pattern);
        Matcher matcher;
        matcher = pattern.matcher(ipAdress);
        return matcher.matches();
    }

    // STRING
    public static String trimSpace(String str) {
        str = str.trim();
        str = str.replaceAll("[\\s]+", " ");
        return str;
    }

    public static String deleteSpace(String str) {
        str = str.trim();
        str = str.replaceAll("[\\s]+", "");
        return str;
    }
}
