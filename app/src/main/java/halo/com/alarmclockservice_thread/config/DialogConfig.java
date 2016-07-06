package halo.com.alarmclockservice_thread.config;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import halo.com.alarmclockservice_thread.MainActivity;
import halo.com.alarmclockservice_thread.R;
import halo.com.alarmclockservice_thread.bean.Alarm;
import halo.com.alarmclockservice_thread.dao.AlarmDataBase;


/**
 * Created by HoVanLy on 6/20/2016.
 */
public class DialogConfig extends DialogFragment {
    Alarm alarm;
    Button btnDelete;
    Button btnEdit;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View viewDialog = layoutInflater.inflate(R.layout.item_edit_config, null);
        btnDelete = (Button) viewDialog.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmDataBase alarmDataBase = new AlarmDataBase(getActivity());
                alarmDataBase.deleteAlarm(alarm.getId());
                MainActivity.recyclerviewAdapter.setAlarmList(alarmDataBase.getListAlarm());
                MainActivity.recyclerviewAdapter.notifyDataSetChanged();
                dismiss();
            }
        });
        btnEdit = (Button) viewDialog.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        builder.setView(viewDialog);
        return builder.create();
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }
}
