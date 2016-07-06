package halo.com.alarmclockservice_thread.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import halo.com.alarmclockservice_thread.bean.ParameterIntent;
import halo.com.alarmclockservice_thread.receiver.AlarmReceiver;
import halo.com.alarmclockservice_thread.remind.TimeRemindManager;


/**
 * Created by HoVanLy on 7/5/2016.
 */
public class RepeatRemindService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent!=null){
            String title = intent.getStringExtra(ParameterIntent.TITLE);
            int id = intent.getIntExtra(ParameterIntent.ID_REMIND, 0);
            int[] listIndexDay = intent.getIntArrayExtra(ParameterIntent.LIST_INDEX_DAY);
            long timeSet = intent.getLongExtra(ParameterIntent.TIME_SET, 0);

            int indexCurrentDay = TimeRemindManager.getIndexDay(TimeRemindManager.getDayOfWeek());
            long loadTimeNextDay = TimeRemindManager.getTimeForNextDay(indexCurrentDay, listIndexDay);
            Log.e("Net Load Repeat Remind", String.valueOf(loadTimeNextDay));
            timeSet = timeSet + loadTimeNextDay;
            Intent intentRemind = new Intent(this, AlarmReceiver.class);
            intentRemind.putExtra(ParameterIntent.TITLE, title);
            intentRemind.putExtra(ParameterIntent.ID_REMIND, id);
            intentRemind.putExtra(ParameterIntent.TIME_SET, timeSet);
            intentRemind.putExtra(ParameterIntent.LIST_INDEX_DAY, listIndexDay);
            sendBroadcast(intentRemind);
        }else {
            Log.e("TAG", "Null Intent");
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "Service  Repeat Destroy");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("TAG", "Service Repeat Create");
    }

    public RepeatRemindService() {
        super();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
