package com.example.aaron.todolist;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public EditText titleEditText;
    public String[] list_values = new String[25];
    int listCount = 0;
    private int position;

    public ArrayList<String> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RelativeLayout relLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_main, container, false);
        titleEditText = (EditText) relLayout.findViewById(R.id.title_editText);

        return relLayout;
    }

    //method to add items to the list
    public void addToDoItem(String userTask){
        Log.d("flow", "this method was called");
        list_values[listCount] = userTask;
        list.add(list_values[listCount]);
        position = listCount;
        listCount++;
    }

    //method to edit items in the list
    public void editToDoItem(String userTask, int listPosition){
        Log.d("flow", "this method was called");
        list_values[position] = userTask;
        list.remove(listPosition);
        list.add(listPosition,list_values[position]);
    }

    //method to remove items from the list
    public void deleteToDoItem(int listPosition){
        Log.d("flow", "this method was called");
        list.remove(listPosition);
    }
}
