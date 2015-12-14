package com.example.aaron.todolist;



import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button addTaskButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addTaskButton = (Button) findViewById(R.id.addTaskToList_button);

        //On click listener for the add task button
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               callTaskActivity();
            }
        });
    }

        //Method that creates an intent to start the TaskActivity Class
        public void callTaskActivity(){
            Intent taskActivityIntent = new Intent(this, TaskActivity.class);
            startActivity(taskActivityIntent);
    }
}