package halo.com.alarmclockservice_thread.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import halo.com.alarmclockservice_thread.bean.Alarm;


/**
 * Created by HoVanLy on 6/20/2016.
 */
public class AlarmDataBase extends SQLiteOpenHelper implements IAlarmManager {
    SQLiteDatabase mySqlDatabase;

    public AlarmDataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public AlarmDataBase(Context context) {
        super(context, "AlarmManagement", null, 1);
        mySqlDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "Create table Alarm (id INTEGER primary key not null, title text, day text, time datetime not null)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS Alarm";
        db.execSQL(query);
        onCreate(db);
    }


    @Override
    public List<Alarm> getListAlarm() {
        List<Alarm> alarmList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM Alarm";
        Cursor cursor = mySqlDatabase.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Alarm alarm = new Alarm();
                alarm.setId(cursor.getInt(cursor.getColumnIndex("id")));
                alarm.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                alarm.setTime(cursor.getString(cursor.getColumnIndex("time")));
                alarm.setListDay(cursor.getString(cursor.getColumnIndex("day")));
                alarmList.add(alarm);
            } while (cursor.moveToNext());
        }
        return alarmList;
    }

    @Override
    public boolean addAlarm(Alarm alarm) {
        ContentValues values = new ContentValues();
        values.put("id", alarm.getId());
        values.put("title", alarm.getTitle());
        values.put("time", alarm.getTime());
        values.put("day", alarm.getListDay());
        long insert = mySqlDatabase.insert("Alarm", null, values);
        if (insert == -1) return false;
        return true;
    }

    @Override
    public boolean deleteAlarm(int id) {
        String deleteQuery = "Delete from Alarm where id=" + id;
        try {
            mySqlDatabase.execSQL(deleteQuery);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateAlarm(Alarm alarm) {
        ContentValues values = new ContentValues();
        values.put("title", alarm.getTitle());
        values.put("time", alarm.getTime());
        values.put("day", alarm.getListDay());
        int update = mySqlDatabase.update("Alarm", values, "id" + " = ?", new String[]{String.valueOf(alarm.getId())});
        if (update == -1) return false;
        else return true;
    }

    @Override
    public int getLastId() {
        String selectQuery = "SELECT  id FROM Alarm";
        Cursor cursor = mySqlDatabase.rawQuery(selectQuery, null);
        if (cursor.moveToLast()) {
           return cursor.getInt(cursor.getColumnIndex("id"));
        }
        return 0;
    }
}
