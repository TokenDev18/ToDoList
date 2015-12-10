package com.example.aaron.todolist;


import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Notification;
import android.app.NotificationManager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {

    public EditText taskEditText;
    public String userTask;
    public ListView listview;
    int listPosition;
    CustomAdapter customAdapter;
    public TimePicker timePicker;
    int hourOfDay, min;
    TimePickerFragment timePickerFragment = new TimePickerFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        listview = (ListView) findViewById(R.id.list_view);
        customAdapter = new CustomAdapter(this);

        //adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mainCustomAdapter.list);

        //On click listener for when the user selects the list item they want to update
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.dialog_message_title)
                        .setMessage(R.string.dialog_content_message);
                builder.setNegativeButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String value = (String) listview.getItemAtPosition(position);
                        taskEditText.setText(value);
                        listPosition = customAdapter.list.indexOf(value);
                    }
                });
                builder.setPositiveButton("Create reminder", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //showDatePickerDialog();
                        showTimePickerDialog();
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
                deleteTaskNotification();
                customAdapter.notifyDataSetChanged();
                return false;
            }
        });
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
                addTaskNotification();
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void showTimePickerDialog(){
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    //Add and Delete task notification
    public void addTaskNotification(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_add_white_24dp);
        mBuilder.setContentTitle("Add Notification");
        mBuilder.setContentText("You added " + userTask + " to the list");

        Notification notification = mBuilder.build();
        NotificationManager manager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1, notification);
    }

    public void deleteTaskNotification(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_delete_white_24dp);
        mBuilder.setContentTitle("Delete Notification");
        mBuilder.setContentText("You deleted " + userTask + " from the list");

        Notification notification = mBuilder.build();
        NotificationManager manager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        manager.notify(2, notification);
    }
}