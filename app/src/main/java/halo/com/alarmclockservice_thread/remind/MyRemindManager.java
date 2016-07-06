package halo.com.alarmclockservice_thread.remind;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

import halo.com.alarmclockservice_thread.bean.ParameterIntent;
import halo.com.alarmclockservice_thread.receiver.AlarmReceiver;
import halo.com.alarmclockservice_thread.service.StartRemindService;


/**
 * Created by HoVanLy on 7/4/2016.
 */
public class MyRemindManager {
    Context context;


    public MyRemindManager(Context context) {
        this.context = context;


    }

    public void setUpRemind(int idRemind, Calendar calendar, String title, int[] listIndexDay) {
        // Set up va gui den AlarmReceiver
        Intent intentRemind = new Intent(context, StartRemindService.class);
        intentRemind.putExtra(ParameterIntent.TITLE, title);
        intentRemind.putExtra(ParameterIntent.ID_REMIND, idRemind);
        intentRemind.putExtra(ParameterIntent.LIST_INDEX_DAY, listIndexDay);
        // Check In List
        int indexCurrentDay = TimeRemindManager.getIndexDay(TimeRemindManager.getDayOfWeek());
        boolean isCheckInList = TimeRemindManager.isDayInList(indexCurrentDay, listIndexDay);
        // Truong hop ngay hien tai khong co trong list ngay cai dat
        if (!isCheckInList) {
            Log.e("Current day Not inlLst", String.valueOf(TimeRemindManager.getTimeForNextDayOut(indexCurrentDay, listIndexDay)) + " Current Day:" + indexCurrentDay);
            intentRemind.putExtra(ParameterIntent.TIME_SET, calendar.getTimeInMillis() + TimeRemindManager.getTimeForNextDayOut(indexCurrentDay, listIndexDay));

        } else {
            // truong hop co, hoac khong cai dat list ngay, chi lap 1 mot lan
            if (calendar.getTimeInMillis() >= System.currentTimeMillis()) {
                Log.e("Tag", "Current day In List Remind");
                intentRemind.putExtra(ParameterIntent.TIME_SET, calendar.getTimeInMillis());
            } else {
                // Cong them 1 ngay
                Log.e("Just Only next day: ", String.valueOf(24 * 60 * 60 * 1000));
                intentRemind.putExtra(ParameterIntent.TIME_SET, calendar.getTimeInMillis() + 24 * 60 * 60 * 1000);
            }
        }

        context.startService(intentRemind);

    }

    public void deleteRemind(int idRemind) {

    }

    public void updateRemind(int idRemind) {

    }


}
