package halo.com.alarmclockservice_thread.config;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by HoVanLy on 4/23/2016.
 */
public class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private EditText editText;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), this, hourOfDay, minute, true);
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        String hourOfDayTxt;
        String minuteTxt;
        if (hourOfDay < 10) {
            hourOfDayTxt = "0" + hourOfDay;
        } else {
            hourOfDayTxt = String.valueOf(hourOfDay);
        }
        if (minute < 10) {
            minuteTxt = "0" + minute;
        } else {
            minuteTxt = String.valueOf(minute);
        }
        String time = hourOfDayTxt + ":" + minuteTxt;
        editText.setText(time);
    }

    public void setListenEditText(View view) {
        this.editText = (EditText) view;
    }

}
