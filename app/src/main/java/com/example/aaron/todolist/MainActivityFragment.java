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

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements AdapterView.OnClickListener  {

    private CustomAdapter customAdapter;
    private ListView listview;
    private EditText taskEditText;
    private String userTask;
    private int listPosition;
    DatePickerFragment datePicker;
    Realm realm;
    private RealmResults<Task> tasks;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        listview = (ListView) view.findViewById(R.id.list_view);
        listview.setOnItemClickListener(onItemClickListener);
        listview.setOnItemLongClickListener(onItemLongClickListener);
        taskEditText = (EditText) view.findViewById(R.id.add_task);
        customAdapter = new CustomAdapter(getActivity());
        realm = Realm.getInstance(getActivity());
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
            taskEditText.setText(value);
            taskEditText.setSelection(taskEditText.length());
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

    @Override
    public void onClick(View v) {

    }

    public void showDatePickerDialog() {
        datePicker = new DatePickerFragment();
        datePicker.show(getFragmentManager(), "datePicker");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_addTask:
                Log.d("flow", "action_addTask clicked");
                userTask = taskEditText.getText().toString();
                taskEditText.getText().clear();
                Log.d("flow", "String is passed:" + userTask);
                customAdapter.addToDoItem(userTask);
                realm.beginTransaction();
                Task task = realm.createObject(Task.class);
                task.setNote(userTask);
                realm.commitTransaction();
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
            case R.id.action_deleteTask:
                Log.d("flow", "action_deleteItem clicked");
                customAdapter.deleteToDoItem(listPosition);
                taskEditText.getText().clear();
                customAdapter.notifyDataSetChanged();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
