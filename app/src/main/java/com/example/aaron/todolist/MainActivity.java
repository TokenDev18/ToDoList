package com.example.aaron.todolist;



import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.ReceivingUserTask {

    private int userMonthOfYear;
    private int userDayOfMonth;
    private int userHourOfDay;
    private int userMinute;
    private DialogFragment timePicker;
    TaskFragment taskFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        taskFragment = new TaskFragment();
//        transaction.add(R.id.main_id, taskFragment);
//        transaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void showTimePickerDialog() {
        timePicker = new TimePickerFragment();
        timePicker.show(getFragmentManager(), "timePicker");
    }

    public void setDate(int userDayOfMonth, int userMonthOfYear) {
        this.userDayOfMonth = userDayOfMonth;
        this.userMonthOfYear = userMonthOfYear;
        Log.d("flow", "User enter the following date " + userDayOfMonth + "/" + userMonthOfYear);
        showTimePickerDialog();
    }

    public void setTime(int userHourOfDay, int userMinute) {
        this.userHourOfDay = userHourOfDay;
        this.userMinute = userMinute;
        Log.d("flow", "User entered the following time " + userHourOfDay + ":" + userMinute);
        startAlarm();
    }

    public void startAlarm() {
        Log.d("flow", "Start Alarm was Called!");
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(ALARM_SERVICE);
        Calendar calendar = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR),
                userMonthOfYear, userDayOfMonth, userHourOfDay, userMinute);

        long when = calendar.getTimeInMillis();
        Intent intent = new Intent(this, AlarmIntentService.class);

        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC, when, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC, when, pendingIntent);
        }
    }

    @Override
    public void receiveTask(String task) {
        Log.d("flow", task);
        taskFragment = (TaskFragment) getFragmentManager().findFragmentById(R.id.task_frag);
        if(taskFragment != null) {
            taskFragment.addUserTask(task);
        }
    }
}