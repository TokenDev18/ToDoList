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
    int userHourOfDay, userMinute;
    long userTime;
    final static long MILLISEC_IN_HOUR = 3600000L;
    final static long MILLISEC_IN_MIN = 60000L;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if(hourOfDay == 0) hourOfDay += 12;
        ((MainActivity) getActivity()).setTime(hourOfDay, minute);
        Log.d("flow", "the hour and minute is: " + hourOfDay + " " + minute);
    }

  public long getUserTimeInMilliSec(){
      userTime = (userHourOfDay * MILLISEC_IN_HOUR) + (userMinute * MILLISEC_IN_MIN);
        return userTime ;
   }
}
