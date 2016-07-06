package halo.com.alarmclockservice.receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import halo.com.alarmclockservice.MainActivity;
import halo.com.alarmclockservice.R;
import halo.com.alarmclockservice.bean.ParameterIntent;
import halo.com.alarmclockservice.service.AlarmServicePlayMp3;
import halo.com.alarmclockservice.service.RepeatRemindService;

/**
 * Created by HoVanLy on 7/2/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {
    Context context;
    boolean isShowNotification = false;
    NotificationManager notificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        String title = intent.getStringExtra(ParameterIntent.TITLE);
        int idNotification = intent.getIntExtra(ParameterIntent.ID_REMIND, 0);
        int[] listIndexDay = intent.getIntArrayExtra(ParameterIntent.LIST_INDEX_DAY);
        Toast.makeText(context, title, Toast.LENGTH_SHORT).show();
        showNotification(title, idNotification);
        // Send to service repeating if list day >0
        if (listIndexDay.length != 0) {
            sendToRepeatingService(idNotification, title, listIndexDay);
        }
        Log.e("TAG", "PushNotification Remind Id:" + idNotification);
    }


    private void showNotification(String title, int idNotification) {
        playMp3();
        // Builds a notification
        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setContentText("Ho Van Ly")
                .setTicker(title)
                .setSmallIcon(R.drawable.user)
                .setAutoCancel(true);

        // Define that we have the intention of opening MoreInfoNotification
        Intent moreInfoIntent = new Intent(context, MainActivity.class);
        moreInfoIntent.putExtra("StopSong", "StopSong");
        // Used to stack tasks across activity so we go to the proper place when back is clicked
        // sap xep vao stack
        TaskStackBuilder tStackBuilder = TaskStackBuilder.create(context);

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
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Post the notification
        notificationManager.notify(idNotification, noBuilder.build());
        isShowNotification = true;

    }

    private void playMp3() {
        Intent intent = new Intent(context, AlarmServicePlayMp3.class);
        context.startService(intent);
    }

    private void stopNotificaton(int idNotification) {
        if (isShowNotification) {
            notificationManager.cancel(idNotification);
        }
    }

    private void sendToRepeatingService(int id, String title, int[] listIndexDay) {
        Intent intent = new Intent(context, RepeatRemindService.class);
        intent.putExtra(ParameterIntent.ID_REMIND, id);
        intent.putExtra(ParameterIntent.TITLE, title);
        intent.putExtra(ParameterIntent.LIST_INDEX_DAY, listIndexDay);
        context.startService(intent);
    }
}
