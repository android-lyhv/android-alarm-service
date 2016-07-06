package halo.com.alarmclockservice_thread.dao;


import java.util.List;

import halo.com.alarmclockservice_thread.bean.Alarm;

/**
 * Created by HoVanLy on 6/20/2016.
 */
 interface IAlarmManager {
     List<Alarm> getListAlarm();
     boolean addAlarm(Alarm alarm);
     boolean deleteAlarm(int id);
     boolean updateAlarm(Alarm alarm);
    int getLastId();

}
