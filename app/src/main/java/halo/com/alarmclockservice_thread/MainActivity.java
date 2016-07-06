package halo.com.alarmclockservice_thread;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import halo.com.alarmclockservice_thread.bean.Alarm;
import halo.com.alarmclockservice_thread.config.DialogConfig;
import halo.com.alarmclockservice_thread.dao.AlarmDataBase;
import halo.com.alarmclockservice_thread.recyclerview.ClickItemRecyclerView;
import halo.com.alarmclockservice_thread.recyclerview.DividerItemDecoration;
import halo.com.alarmclockservice_thread.recyclerview.IClickItem;
import halo.com.alarmclockservice_thread.recyclerview.RecyclerViewAdapter;
import halo.com.alarmclockservice_thread.service.ForegroundService;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    public static RecyclerViewAdapter recyclerviewAdapter;
    AlarmDataBase alarmDataBase;
    public static int idRemind = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        alarmDataBase = new AlarmDataBase(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        loadRecyclerView();
        idRemind = alarmDataBase.getLastId();
        Log.e("Tag", "Last Id remind:  " + idRemind);
        //
        Intent startIntent = new Intent(MainActivity.this, ForegroundService.class);
        startService(startIntent);
        //
    }

    private void loadRecyclerView() {
        List<Alarm> alarmList = alarmDataBase.getListAlarm();
        recyclerviewAdapter = new RecyclerViewAdapter(alarmList, getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.hasFixedSize();
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(recyclerviewAdapter);
        recyclerView.addOnItemTouchListener(new ClickItemRecyclerView(getApplicationContext(), recyclerView, new IClickItem() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                List<Alarm> alarmList = alarmDataBase.getListAlarm();
                Alarm alarm = new Alarm();
                alarm.setId(alarmList.get(position).getId());
                DialogConfig dialogConfig = new DialogConfig();
                dialogConfig.setAlarm(alarm);
                dialogConfig.show(getFragmentManager(), "");
            }
        }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {

            startActivity(new Intent(this, AddAlarmActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        List<Alarm> alarmList = alarmDataBase.getListAlarm();
        recyclerviewAdapter.setAlarmList(alarmList);
        recyclerviewAdapter.notifyDataSetChanged();
    }
}
