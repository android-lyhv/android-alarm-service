package halo.com.alarmclockservice_thread;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import halo.com.alarmclockservice_thread.bean.Alarm;
import halo.com.alarmclockservice_thread.config.TimePicker;
import halo.com.alarmclockservice_thread.dao.AlarmDataBase;
import halo.com.alarmclockservice_thread.dao.MyConvert;
import halo.com.alarmclockservice_thread.remind.MyRemindManager;
import halo.com.alarmclockservice_thread.remind.TimeRemindManager;


/**
 * Created by HoVanLy on 6/30/2016.
 */
public class AddAlarmActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.edtTime)
    EditText edtTime;
    @Bind(R.id.cb2)
    CheckBox cb2;
    @Bind(R.id.cb3)
    CheckBox cb3;
    @Bind(R.id.cb4)
    CheckBox cb4;
    @Bind(R.id.cb5)
    CheckBox cb5;
    @Bind(R.id.cb6)
    CheckBox cb6;
    @Bind(R.id.cb7)
    CheckBox cb7;
    @Bind(R.id.cb8)
    CheckBox cb8;
    @Bind(R.id.edtTitle)
    EditText edtTitle;
    @Bind(R.id.btnAddAlarm)
    Button btnAddAlarm;
    AlarmDataBase alarmDataBase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_alarm);
        this.setTitle("New Alarm");
        ButterKnife.bind(this);
        btnAddAlarm.setOnClickListener(this);
        focusStartTime(edtTime);
        alarmDataBase = new AlarmDataBase(getApplicationContext());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAddAlarm) {
            List<Integer> list = new ArrayList<>();
            int listIndexDay[];
            String time = edtTime.getText().toString();
            String title = edtTitle.getText().toString();
            if (!"".equals(time) && !"".equals(title) && MyConvert.checkValidateTime24(time)) {
                Alarm alarm = new Alarm();
                alarm.setTime(time);
                alarm.setTitle(title);
                String listDay = "";
                if (cb2.isChecked()) {
                    listDay = listDay + "2;";
                    list.add(2);
                }
                if (cb3.isChecked()) {
                    list.add(3);
                    listDay = listDay + "3;";

                }
                if (cb4.isChecked()) {
                    listDay = listDay + "4;";
                    list.add(4);
                }
                if (cb5.isChecked()) {
                    listDay = listDay + "5;";
                    list.add(5);
                }
                if (cb6.isChecked()) {
                    listDay = listDay + "6;";
                    list.add(6);
                }
                if (cb7.isChecked()) {
                    listDay = listDay + "7;";
                    list.add(7);
                }
                if (cb8.isChecked()) {
                    listDay = listDay + "8;";
                    list.add(8);
                }
                alarm.setListDay(listDay);
                MainActivity.idRemind = MainActivity.idRemind + 1;
                alarm.setId(MainActivity.idRemind);
                alarm.setTime(time);
                alarmDataBase.addAlarm(alarm);
                //
                int size = list.size();
                if (size == 0) {
                    listIndexDay = new int[0];
                } else {
                    listIndexDay = new int[size];
                    for (int i = 0; i < list.size(); i++) {
                        listIndexDay[i] = list.get(i);
                    }
                }
                // set up remind
                sendRemind(this, MainActivity.idRemind, time, title, listIndexDay);
                this.finish();
            } else {
                Toast.makeText(getApplicationContext(), "Fail Input value", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void focusStartTime(EditText editTime) {
        editTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePicker timePicker = new TimePicker();
                timePicker.setListenEditText(v);
                timePicker.show(getSupportFragmentManager().beginTransaction(), "");
            }
        });
    }

    private void sendRemind(Context context, int idRemind, String time, String title, int[] listIndexDay) {
        Log.e("ADD", "Remind id:" + idRemind + " Time:"
                + time + " Day:" + toStringListDay(listIndexDay) + " Current:" + TimeRemindManager.getIndexDay(TimeRemindManager.getDayOfWeek()));
        String[] list = time.split(":");
        int hour = Integer.parseInt(list[0].trim());
        int minute = Integer.parseInt(list[1].trim());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 00);
        MyRemindManager remindManager = new MyRemindManager(context);
        remindManager.setUpRemind(idRemind, calendar, title, listIndexDay);
    }

    private String toStringListDay(int[] list) {
        String text = "";
        for (int i = 0; i < list.length; i++) {
            text = text + " " + list[i];
        }
        return text;
    }
}
