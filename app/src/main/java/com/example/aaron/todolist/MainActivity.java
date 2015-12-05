package com.example.aaron.todolist;


import android.app.Notification;
import android.app.NotificationManager;

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

public class MainActivity extends AppCompatActivity {

    public EditText taskEditText;
    public String userTask;
    public ListView listview;
    MainActivityFragment fragment = new MainActivityFragment();
    ArrayAdapter adapter;
    int listPosition;
    CustomAdapter mainCustomAdapter = new CustomAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listview = (ListView) findViewById(R.id.list_view);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mainCustomAdapter.list);

        //On click listener for when the user selects the list item they want to update
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = (String) listview.getItemAtPosition(position);
                taskEditText.setText(value);
                listPosition = mainCustomAdapter.list.indexOf(value);
                Log.d("flow", "list position is: " + listPosition);
            }
        });

        //Long click listener to delete a task from the list
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("flow", "item was long clicked");
                String onLongClickValue = (String) listview.getItemAtPosition(position);
                listPosition = mainCustomAdapter.list.indexOf(onLongClickValue);
                mainCustomAdapter.deleteToDoItem(listPosition);
                deleteTaskNotification();
                adapter.notifyDataSetChanged();
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
                mainCustomAdapter.addToDoItem(userTask);
                listview.setAdapter(adapter);
                addTaskNotification();
                return true;
            case R.id.action_updateTask:
                Log.d("flow", "action_updateItem clicked");
                userTask = taskEditText.getText().toString();
                taskEditText.getText().clear();
                Log.d("flow", "String is passed:" + userTask);
                mainCustomAdapter.editToDoItem(userTask, listPosition);
                adapter.notifyDataSetChanged();
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

    public void addTaskNotification(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_add_white_24dp);
        mBuilder.setContentTitle("Add Notification");
        mBuilder.setContentText("You added " + userTask + " to the list");

        Notification notification = mBuilder.build();
        NotificationManager manager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1, notification);
        mBuilder.setAutoCancel(true);
    }

    public void deleteTaskNotification(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_delete_white_24dp);
        mBuilder.setContentTitle("Delete Notification");
        mBuilder.setContentText("You deleted " + userTask + " from the list");

        Notification notification = mBuilder.build();
        NotificationManager manager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        manager.notify(2, notification);
        mBuilder.setAutoCancel(true);
    }
}