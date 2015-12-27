package com.example.aaron.todolist;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

/**
 * Created by aaron on 12/24/15.
 */
public class TaskFragment extends Fragment implements AdapterView.OnClickListener {
    private String userTask;
    private CustomAdapter customAdapter;
    private ListView listview;
    EditText editText;
    private int listPosition;
    DatePickerFragment datePicker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.task_fragment, container, false);
        listview = (ListView) view.findViewById(R.id.list_view);
        editText = (EditText) view.findViewById(R.id.edit_task);

        listview.setOnItemClickListener(onItemClickListener);
        listview.setOnItemLongClickListener(onItemLongClickListener);

        customAdapter = new CustomAdapter(getActivity());

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String value = (String) listview.getItemAtPosition(position);
            editText.setText(value);
            editText.setSelection(editText.length());
            listPosition = customAdapter.list.indexOf(value);
        }
    };

    private AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("flow", "item was long clicked");
            showDatePickerDialog();
            return false;
        }
    };

    public void addUserTask(String task){
        this.userTask = task;
        Log.d("flow", "String is passed:" + userTask);
        customAdapter.addToDoItem(userTask);
        listview.setAdapter(customAdapter);
    }

    public void showDatePickerDialog() {
        datePicker = new DatePickerFragment();
        datePicker.show(getFragmentManager(), "datePicker");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_updateTask:
                Log.d("flow", "action_updateItem clicked");
                userTask = editText.getText().toString();
                editText.getText().clear();
                Log.d("flow", "String is passed:" + userTask);
                customAdapter.editToDoItem(userTask, listPosition);
                customAdapter.notifyDataSetChanged();
                return true;
            case R.id.action_deleteTask:
                Log.d("flow", "action_deleteItem clicked");
                customAdapter.deleteToDoItem(listPosition);
                editText.getText().clear();
                customAdapter.notifyDataSetChanged();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
