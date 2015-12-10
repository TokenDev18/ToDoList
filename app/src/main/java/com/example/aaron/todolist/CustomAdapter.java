package com.example.aaron.todolist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by aaron on 12/1/15.
 */
public class CustomAdapter extends BaseAdapter{

    private LayoutInflater inflater;
    public ArrayList<String> list = new ArrayList<>();

    public CustomAdapter(){

    }

    public CustomAdapter(Context context){
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String listItem = (String) getItem(position);
        View custom = inflater.inflate(R.layout.custom, parent, false);
        TextView textView = (TextView) custom.findViewById(R.id.textView);
        ImageView imageView = (ImageView) custom.findViewById(R.id.rubiks_cube);
        textView.setText(listItem);
        imageView.setImageResource(R.drawable.rubik_cube);
        return custom;
    }
    //method to add items to the list
    public void addToDoItem(String userTask){
        Log.d("flow", "add method was called");
        list.add(userTask);
    }

    //method to edit items in the list
    public void editToDoItem(String userTask, int listPosition){
        Log.d("flow", "edit method was called");
        list.remove(listPosition);
        list.add(listPosition,userTask);
    }

    //method to remove items from the list
    public void deleteToDoItem(int listPosition){
        Log.d("flow", "delete method was called");
        list.remove(listPosition);
    }
}
