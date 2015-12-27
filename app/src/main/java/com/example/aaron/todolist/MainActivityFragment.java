package com.example.aaron.todolist;




import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment   {


    private EditText taskEditText;
    private Button button;
    ReceivingUserTask rTask;
    RelativeLayout rLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        taskEditText = (EditText) view.findViewById(R.id.add_task);
        button = (Button) view.findViewById(R.id.add_button);
        rLayout = (RelativeLayout) view.findViewById(R.id.taskfrag_container);

        taskEditText.setVisibility(View.INVISIBLE);
        button.setVisibility(View.INVISIBLE);

        button.setOnClickListener(onClickListener);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            rTask = (ReceivingUserTask) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Must implement ReceivingUserTask");
        }
    }
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("flow", "add task button was clicked");
            String userTask = taskEditText.getText().toString();
            Log.d("flow", "this string was passed " + userTask);
            rTask.receiveTask(userTask);
            taskEditText.getText().clear();

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            TaskFragment taskFragment = new TaskFragment();
            transaction.replace(R.id.taskfrag_container, taskFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_addTask:
                Log.d("flow", "action_addTask clicked");
                taskEditText.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public interface ReceivingUserTask {
        public void receiveTask(String task);
    }
}
