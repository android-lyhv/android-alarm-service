package halo.com.alarmclockservice_thread.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import halo.com.alarmclockservice_thread.MainActivity;
import halo.com.alarmclockservice_thread.R;
import halo.com.alarmclockservice_thread.bean.ParameterIntent;

/**
 * Created by HoVanLy on 7/6/2016.
 */
public class StartRemindService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            final String title = intent.getStringExtra(ParameterIntent.TITLE);
            long time_set = intent.getLongExtra(ParameterIntent.TIME_SET, 0);
            final int idNotification = intent.getIntExtra(ParameterIntent.ID_REMIND, 0);
            final int[] listIndexDay = intent.getIntArrayExtra(ParameterIntent.LIST_INDEX_DAY);

            Log.e("TAG", "Dang xu ly Remind: " + idNotification);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (listIndexDay.length != 0) {
                        sendToRepeatingService(idNotification, title, System.currentTimeMillis(), listIndexDay);
                    }
                    Log.e("TAG", "PushNotification Remind Id:" + idNotification);
                    Toast.makeText(getApplicationContext(), title, Toast.LENGTH_SHORT).show();
                    showNotification(title, idNotification);
                    //playMp3();
                }
            }, time_set - System.currentTimeMillis());
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "Service  Start Destroy");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("TAG", "Service  Create");
    }

    private void showNotification(String title, int idNotification) {
        // Builds a notification
        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText("Ho Van Ly")
                .setTicker(title)
                .setSmallIcon(R.drawable.user)
                .setAutoCancel(true);

        // Define that we have the intention of opening MoreInfoNotification
        Intent moreInfoIntent = new Intent(this, MainActivity.class);
        moreInfoIntent.putExtra("StopSong", "StopSong");
        // Used to stack tasks across activity so we go to the proper place when back is clicked
        // sap xep vao stack
        TaskStackBuilder tStackBuilder = TaskStackBuilder.create(this);

        // Add all parents of this activity to the stack
        tStackBuilder.addParentStack(MainActivity.class);

        // Add our new Intent to the stack
        tStackBuilder.addNextIntent(moreInfoIntent);

        // Define an Intent and an action to perform with it by another application
        // FLAG_UPDATE_CURRENT : If the intent exists keep it but update it if needed

        PendingIntent pendingIntent = tStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Defines the Intent to fire when the notification is clicked
        noBuilder.setContentIntent(pendingIntent);

        // Gets a NotificationManager which is used to notify the user of the background event
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        // Post the notification
        notificationManager.notify(idNotification, noBuilder.build());


    }

    private void playMp3() {
        Intent intent = new Intent(this, PlaySoundService.class);
        this.startService(intent);
    }

    private void stopNotificaton(int idNotification) {
       /* if (isShowNotification) {
            notificationManager.cancel(idNotification);
        }*/
    }

    private void sendToRepeatingService(int id, String title, long timeSet, int[] listIndexDay) {
        Intent intent = new Intent(this, RepeatRemindService.class);
        intent.putExtra(ParameterIntent.ID_REMIND, id);
        intent.putExtra(ParameterIntent.TITLE, title);
        intent.putExtra(ParameterIntent.TIME_SET, timeSet);
        intent.putExtra(ParameterIntent.LIST_INDEX_DAY, listIndexDay);
        this.startService(intent);
    }
}
