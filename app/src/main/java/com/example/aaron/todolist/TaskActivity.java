package com.example.aaron.todolist;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TaskActivity extends AppCompatActivity {

    private EditText taskEditText;
    private String userTask;
    private ListView listview;


    private int listPosition;
    private CustomAdapter customAdapter;
    private DialogFragment datePicker;
    private DialogFragment timePicker;

    private int userMonthOfYear;
    private int userDayOfMonth;
    private int userHourOfDay;
    private int userMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listview = (ListView) findViewById(R.id.list_view);
        customAdapter = new CustomAdapter(this);

        //adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mainCustomAdapter.list);

        //On click listener for when the user selects the list item they want to update
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TaskActivity.this);
                builder.setTitle(R.string.dialog_message_title)
                        .setMessage(R.string.dialog_content_message);
                builder.setNegativeButton("Update Task", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String value = (String) listview.getItemAtPosition(position);

                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.toast_layout,
                                (ViewGroup) findViewById(R.id.toast_layout_root));
                        TextView textView = (TextView) layout.findViewById(R.id.text);
                        textView.setText("Click the update button to the right of the add sign in the menu above");

                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_VERTICAL, 0, 2);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();

                        taskEditText.setText(value);
                        taskEditText.setSelection(taskEditText.length());
                        listPosition = customAdapter.list.indexOf(value);
                    }
                });
                builder.setPositiveButton("Set Reminder", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showDatePickerDialog();
                    }
                });
                builder.create();
                builder.show();
            }
        });

        //Long click listener to delete a task from the list
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("flow", "item was long clicked");
                String onLongClickValue = (String) listview.getItemAtPosition(position);
                listPosition = customAdapter.list.indexOf(onLongClickValue);
                customAdapter.deleteToDoItem(listPosition);
                customAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Menu items to add items to the list or update items in the list.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_addTask:
                Log.d("flow", "action_addTask clicked");
                taskEditText = (EditText) findViewById(R.id.add_task);
                userTask = taskEditText.getText().toString();
                taskEditText.getText().clear();
                Log.d("flow", "String is passed:" + userTask);
                customAdapter.addToDoItem(userTask);
                listview.setAdapter(customAdapter);
                return true;
            case R.id.action_updateTask:
                Log.d("flow", "action_updateItem clicked");
                userTask = taskEditText.getText().toString();
                taskEditText.getText().clear();
                Log.d("flow", "String is passed:" + userTask);
                customAdapter.editToDoItem(userTask, listPosition);
                customAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showTimePickerDialog(){
        timePicker = new TimePickerFragment();
        timePicker.show(getFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(){
        datePicker = new DatePickerFragment();
        datePicker.show(getFragmentManager(), "datePicker");
    }

    public void setDate(int userDayOfMonth, int userMonthOfYear) {
        this.userDayOfMonth = userDayOfMonth;
        this.userMonthOfYear = userMonthOfYear;
        showTimePickerDialog();
    }

    public void setTime(int userHourOfDay, int userMinute ){
        this.userHourOfDay = userHourOfDay;
        this.userMinute = userMinute;
        startAlarm();
    }

    private void startAlarm() {
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(ALARM_SERVICE);
        Calendar calendar = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR),
                userMonthOfYear, userDayOfMonth, userHourOfDay, userMinute);

        long when = calendar.getTimeInMillis();
        Intent intent = new Intent(this, AlarmIntentService.class);

        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            alarmManager.setExact(AlarmManager.RTC, when, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC, when, pendingIntent);
        }
    }

}
