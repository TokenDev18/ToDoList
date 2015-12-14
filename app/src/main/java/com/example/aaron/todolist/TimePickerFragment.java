package com.example.aaron.todolist;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TimePicker;


import java.util.Calendar;

/**
 * Created by aaron on 12/6/15.
 */
public class TimePickerFragment extends DialogFragment implements android.app.TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Log.d("flow", "the hour and minute is: " + hourOfDay + " " + minute);
        if(hourOfDay == 0) hourOfDay += 12;
        ((TaskActivity) getActivity()).setTime(hourOfDay, minute);
    }
}
