package com.example.aaron.todolist;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Typeface font = Typeface.createFromAsset(getAssets(), "unique.ttf");
        EditText titleEditText = (EditText) findViewById(R.id.title_editText);
        titleEditText.setTypeface(font);
        listview = (ListView) findViewById(R.id.list_view);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, fragment.list);

        //On click listener for when the user selects the list item they want to update
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = (String) listview.getItemAtPosition(position);
                taskEditText.setText(value);
                listPosition = fragment.list.indexOf(value);
                Log.d("flow", "list position is: " + listPosition);
            }
        });

        //Long click listener to delete a task from the list
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("flow", "item was long clicked");
                String onLongClickValue = (String) listview.getItemAtPosition(position);
                listPosition = fragment.list.indexOf(onLongClickValue);
                fragment.deleteToDoItem(listPosition);
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
                fragment.addToDoItem(userTask);
                listview.setAdapter(adapter);
                return true;
            case R.id.action_updateTask:
                Log.d("flow", "action_updateItem clicked");
                userTask = taskEditText.getText().toString();
                taskEditText.getText().clear();
                Log.d("flow", "String is passed:" + userTask);
                fragment.editToDoItem(userTask, listPosition);
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
}
